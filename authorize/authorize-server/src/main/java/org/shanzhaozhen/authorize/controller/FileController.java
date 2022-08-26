package org.shanzhaozhen.authorize.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.shanzhaozhen.common.core.result.R;
import org.shanzhaozhen.uaa.feign.FileFeignClient;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

/**
 * @Author: shanzhaozhen
 * @Date: 2022-08-26
 * @Description:
 */
@Controller
@RequestMapping("/files")
@RequiredArgsConstructor
public class FileController {

    private final FileFeignClient fileFeignClient;

    @Operation(summary = "文件上传")
    public R<String> uploadFile(
            @Parameter(description = "文件") @RequestParam(value = "file") MultipartFile file) {
        return fileFeignClient.uploadFile(file, null);
    }

}
