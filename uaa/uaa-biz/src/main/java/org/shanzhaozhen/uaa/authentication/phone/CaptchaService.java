package org.shanzhaozhen.uaa.authentication.phone;


public interface CaptchaService {

    /**
     * 校验验证码
     * @param phone
     * @param rawCode
     * @return
     */
    boolean verifyCaptcha(String phone, String rawCode) throws CaptchaErrorException;
}
