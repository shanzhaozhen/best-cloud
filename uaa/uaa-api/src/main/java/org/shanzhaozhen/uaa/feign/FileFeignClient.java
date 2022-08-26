package org.shanzhaozhen.uaa.feign;

import io.swagger.v3.oas.annotations.Parameter;
import org.shanzhaozhen.common.core.result.R;
import org.shanzhaozhen.uaa.pojo.dto.UserDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@FeignClient(name = "uaa", contextId = "file")
public interface FileFeignClient {

    @GetMapping("/files")
    R<String> uploadFile(
            @Parameter(description = "文件") @RequestParam(value = "file") MultipartFile file,
            @Parameter(description = "存储桶名称(非必须，微服务有单独默认存储桶)") @RequestParam(value = "bucketName", required = false) String bucketName);

}
