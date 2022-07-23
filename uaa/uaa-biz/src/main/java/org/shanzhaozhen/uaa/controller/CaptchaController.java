package org.shanzhaozhen.uaa.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.shanzhaozhen.common.core.result.R;
import org.shanzhaozhen.uaa.pojo.dto.CaptchaInfo;
import org.shanzhaozhen.uaa.service.CaptchaService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Tag(name = "login", description = "登陆接口")
@RestController
@RequiredArgsConstructor
public class CaptchaController {

    private static final String GENERATE_CAPTCHA_IMAGE = "/captcha/phone";

    private CaptchaService captchaService;

    @Operation(summary = "生成图片验证码")
    @PostMapping(GENERATE_CAPTCHA_IMAGE)
    public void generateCaptchaImage(HttpServletRequest request, HttpServletResponse response) {
        captchaService.generateCaptchaImage(request, response);
    }

}
