package org.shanzhaozhen.uaa.service;

import org.shanzhaozhen.uaa.exception.CaptchaErrorException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface CaptchaService {

    /**
     * 生成验证码
     * @param phoneNum 手机号
     * @return
     */
    String generateCaptchaNumber(String phone);

    /**
     * 生成验证码图片
     * @param request
     * @param response
     */
    void generateCaptchaImage(HttpServletRequest request, HttpServletResponse response) throws IOException;

    /**
     * 校验验证码
     * @param phone
     * @param rawCode
     * @return
     */
    boolean verifyCaptcha(String phone, String rawCode) throws CaptchaErrorException;


}
