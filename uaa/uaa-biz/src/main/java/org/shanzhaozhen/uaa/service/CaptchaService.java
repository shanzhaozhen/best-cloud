package org.shanzhaozhen.uaa.service;


import org.shanzhaozhen.uaa.authentication.phone.CaptchaErrorException;

public interface CaptchaService {

    /**
     * 生成验证码
     * @param phoneNum 手机号
     * @return
     */
    String generateCaptchaCode(String phoneNum);

    /**
     * 校验验证码
     * @param phone
     * @param rawCode
     * @return
     */
    boolean verifyCaptcha(String phone, String rawCode) throws CaptchaErrorException;

}
