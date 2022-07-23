package org.shanzhaozhen.uaa.service;


import org.shanzhaozhen.uaa.exception.CaptchaErrorException;
import org.shanzhaozhen.uaa.pojo.dto.CaptchaInfo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface CaptchaService {

    /**
     * 生成图片验证码
     * @param request
     * @param response
     */
    void generateCaptchaImage(HttpServletRequest request, HttpServletResponse response);

    /**
     * 生成手机验证码
     * @param phone
     * @return
     */
    CaptchaInfo generateCaptchaToPhone(String phone);

    /**
     * 校验验证码
     * @param key
     * @param rawCode
     * @return
     */
    boolean verifyCaptcha(String key, String rawCode) throws CaptchaErrorException;



}
