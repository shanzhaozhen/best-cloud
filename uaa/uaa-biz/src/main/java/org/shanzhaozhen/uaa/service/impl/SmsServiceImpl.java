package org.shanzhaozhen.uaa.service.impl;

import lombok.RequiredArgsConstructor;
import org.shanzhaozhen.uaa.service.CaptchaService;
import org.shanzhaozhen.uaa.service.SmsService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SmsServiceImpl implements SmsService {

    private final CaptchaService captchaService;

    @Override
    public void sendMsg(String content) {
        // todo: 发送信息逻辑，组建短信模板
    }

    @Override
    public void sendCode(String phone) {
        // todo: 生成验证码
        String captcha = captchaService.generateCaptchaNumber(phone);
        this.sendMsg("验证码为：" + captcha);
    }
}
