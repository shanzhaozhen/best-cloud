package org.shanzhaozhen.common.minio.service;

import io.minio.*;
import io.minio.http.Method;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.shanzhaozhen.common.minio.config.MinioConfig;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.UUID;


@Component
@RequiredArgsConstructor
@Slf4j
public class MinioService implements InitializingBean {

    private final MinioConfig minioConfig;

    private MinioClient minioClient;

    @Override
    public void afterPropertiesSet() {
        log.info("初始化 MinIO 客户端...");
        Assert.hasText(minioConfig.getEndpoint(), "MinIO endpoint不能为空");
        Assert.hasText(minioConfig.getAccessKey(), "MinIO accessKey不能为空");
        Assert.hasText(minioConfig.getSecretKey(), "MinIO secretKey不能为空");
        this.minioClient = MinioClient.builder()
                //.endpoint(endpoint, 443, true)
                .endpoint(minioConfig.getEndpoint())
                .credentials(minioConfig.getAccessKey(), minioConfig.getSecretKey())
                .build();
    }

    /**
     * 创建存储桶(存储桶不存在)
     *
     * @param bucketName
     */
    @SneakyThrows
    public void createBucketIfAbsent(String bucketName) {
        BucketExistsArgs bucketExistsArgs = BucketExistsArgs.builder()
                .bucket(bucketName)
                .build();
        if (!minioClient.bucketExists(bucketExistsArgs)) {
            MakeBucketArgs makeBucketArgs = MakeBucketArgs.builder()
                    .bucket(bucketName)
                    .build();

            minioClient.makeBucket(makeBucketArgs);

            // 设置存储桶访问权限为PUBLIC， 如果不配置，则新建的存储桶默认是PRIVATE，则存储桶文件会拒绝访问 Access Denied
            SetBucketPolicyArgs setBucketPolicyArgs = SetBucketPolicyArgs.builder()
                    .bucket(bucketName)
                    .config(publicBucketPolicy(bucketName).toString())
                    .build();
            minioClient.setBucketPolicy(setBucketPolicyArgs);
        }
    }

    /**
     * 上传文件对象(默认存储桶)
     *
     * @param file MultipartFile文件对象
     * @return
     */
    public String putObject(MultipartFile file) {
        return putObject(file, minioConfig.getDefaultBucket());
    }

    /**
     * 上传文件对象
     *
     * @param file       MultipartFile文件对象
     * @param bucketName 存储桶名称
     * @return
     */
    @SneakyThrows
    public String putObject(MultipartFile file, String bucketName) {
        // 存储桶名称为空则使用默认的存储桶
        if (!StringUtils.hasText(bucketName)) {
            bucketName = minioConfig.getDefaultBucket();
        }

        createBucketIfAbsent(bucketName);

        // todo: 微服务集群需要改成雪花id
        String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".") + 1);
        String fileName = UUID.randomUUID() + "." + suffix;

        InputStream inputStream = file.getInputStream();
        PutObjectArgs putObjectArgs = PutObjectArgs.builder()
                .bucket(bucketName)
                .object(fileName)
                .contentType(file.getContentType())
                .stream(inputStream, inputStream.available(), -1)
                .build();

        minioClient.putObject(putObjectArgs);

        String fileUrl;
        if (!StringUtils.hasText(minioConfig.getCustomDomain())) { // 没有自定义文件路径域名
            GetPresignedObjectUrlArgs getPresignedObjectUrlArgs = GetPresignedObjectUrlArgs.builder()
                    .bucket(bucketName)
                    .object(fileName)
                    .method(Method.GET)
                    .build();

            fileUrl = minioClient.getPresignedObjectUrl(getPresignedObjectUrlArgs);
            fileUrl = fileUrl.substring(0, fileUrl.indexOf("?"));
        } else { // 自定义文件路径域名，Nginx配置方向代理转发MinIO
            fileUrl = minioConfig.getCustomDomain() +'/'+ bucketName + "/" + fileName;
        }
        return fileUrl;
    }

    public void removeObject(String bucket, String fileName) throws Exception {
        RemoveObjectArgs removeObjectArgs = RemoveObjectArgs.builder()
                .bucket(bucket)
                .object(fileName)
                .build();
        minioClient.removeObject(removeObjectArgs);
    }


    /**
     * PUBLIC桶策略
     * 如果不配置，则新建的存储桶默认是PRIVATE，则存储桶文件会拒绝访问 Access Denied
     *
     * @param bucketName
     * @return
     */
    private static StringBuilder publicBucketPolicy(String bucketName) {
        /**
         * AWS的S3存储桶策略
         * Principal: 生效用户对象
         * Resource:  指定存储桶
         * Action: 操作行为
         */
        StringBuilder builder = new StringBuilder();
        builder.append("{\"Version\":\"2012-10-17\"," +
                "\"Statement\":[{\"Effect\":\"Allow\"," +
                "\"Principal\":{\"AWS\":[\"*\"]}," +
                "\"Action\":[\"s3:ListBucketMultipartUploads\",\"s3:GetBucketLocation\",\"s3:ListBucket\"]," +
                "\"Resource\":[\"arn:aws:s3:::" + bucketName + "\"]}," +
                "{\"Effect\":\"Allow\"," +
                "\"Principal\":{\"AWS\":[\"*\"]}," +
                "\"Action\":[\"s3:ListMultipartUploadParts\",\"s3:PutObject\",\"s3:AbortMultipartUpload\",\"s3:DeleteObject\",\"s3:GetObject\"]," +
                "\"Resource\":[\"arn:aws:s3:::" + bucketName + "/*\"]}]}");

        return builder;
    }
}
