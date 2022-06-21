package org.shanzhaozhen.common.minio.endpoint;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.shanzhaozhen.common.core.result.R;
import org.shanzhaozhen.common.minio.service.MinioService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Tag(name = "files", description = "文件接口")
@RestController
@RequestMapping("/files")
@RequiredArgsConstructor
public class FileEndpoint {

    private final MinioService minioService;

    @PostMapping
    @Operation(summary = "文件上传")
    @SneakyThrows
    public R<String> uploadFile(
            @Parameter(description = "文件") @RequestParam(value = "file") MultipartFile file,
            @Parameter(description = "存储桶名称(非必须，微服务有单独默认存储桶)") @RequestParam(value = "bucketName", required = false) String bucketName
    ) {
        String path = minioService.putObject(file, bucketName);
        return R.ok(path);
    }

    @DeleteMapping
    @Operation(summary = "文件删除")
    @SneakyThrows
    public R<?> deleteFile(@Parameter(description = "文件路径") @RequestParam String path) {
        int lastIndex = path.lastIndexOf("/");
        String bucket = path.substring(path.lastIndexOf("/", lastIndex - 1) + 1, lastIndex);
        String fileName = path.substring(lastIndex + 1);
        minioService.removeObject(bucket, fileName);
        return R.ok();
    }

}
