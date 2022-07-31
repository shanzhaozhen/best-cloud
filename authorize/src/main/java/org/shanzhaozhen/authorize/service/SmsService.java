package org.shanzhaozhen.authorize.service;

public interface SmsService {

    /**
     * 发送信息
     * @param content
     */
    void sendMsg(String content);

    /**
     * 发送验证码
     * @param phone
     */
    void sendCode(String phone);

}
