package org.shanzhaozhen.common.minio.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: shanzhaozhen
 * @Date: 2022-06-21
 * @Description: minio 配置
 */
@ConfigurationProperties(prefix = "minio")
@Configuration
@Getter
@Setter
public class MinioConfig {

    /**
     * MinIO的API地址
     */
    private String endpoint;

    /**
     * 用户名
     */
    private String accessKey;

    /**
     * 密钥
     */
    private String secretKey;

    /**
     * 自定义域名(非必须)
     */
    private String customDomain;

    /**
     * 存储桶名称，默认微服务单独一个存储桶
     */
    private String bucketName;

}
