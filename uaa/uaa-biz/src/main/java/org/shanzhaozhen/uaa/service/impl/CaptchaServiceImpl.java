package org.shanzhaozhen.uaa.service.impl;

import org.shanzhaozhen.uaa.authentication.phone.CaptchaErrorException;
import org.shanzhaozhen.uaa.service.CaptchaService;
import org.springframework.stereotype.Service;

@Service
public class CaptchaServiceImpl implements CaptchaService {
    @Override
    public boolean verifyCaptcha(String phone, String rawCode) throws CaptchaErrorException {
        return false;
    }
}
