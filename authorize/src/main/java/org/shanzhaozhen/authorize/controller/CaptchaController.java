package org.shanzhaozhen.authorize.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.shanzhaozhen.authorize.service.CaptchaService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Tag(name = "login", description = "登陆接口")
@RestController
@RequiredArgsConstructor
public class CaptchaController {

    private static final String GENERATE_CAPTCHA_IMAGE = "/captcha/image";

    private final CaptchaService captchaService;

    @Operation(summary = "生成图片验证码")
    @PostMapping(GENERATE_CAPTCHA_IMAGE)
    public void generateCaptchaImage(HttpServletRequest request, HttpServletResponse response) throws IOException {
        captchaService.generateCaptchaImage(request, response);
    }

}
