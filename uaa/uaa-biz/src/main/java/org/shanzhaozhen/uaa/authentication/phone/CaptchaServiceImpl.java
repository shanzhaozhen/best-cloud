package org.shanzhaozhen.uaa.authentication.phone;

import org.springframework.stereotype.Service;

@Service
public class CaptchaServiceImpl implements CaptchaService {
    @Override
    public boolean verifyCaptcha(String phone, String rawCode) throws CaptchaErrorException {
        return false;
    }
}
