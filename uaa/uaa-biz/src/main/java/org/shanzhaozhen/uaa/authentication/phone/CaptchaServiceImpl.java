package org.shanzhaozhen.uaa.authentication.phone;

public class CaptchaServiceImpl implements CaptchaService {
    @Override
    public boolean verifyCaptcha(String phone, String rawCode) throws CaptchaErrorException {
        return false;
    }
}
