package org.shanzhaozhen.authorize.service;


import org.shanzhaozhen.authorize.exception.CaptchaErrorException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface CaptchaService {

    /**
     * 生成验证码
     * @param phone 手机号
     * @return
     */
    String generateCaptchaCode(String phone);

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

    /**
     * 发送短信验证码
     * @param phone
     */
    void sendCaptchaCode(String phone) throws Exception;

}
