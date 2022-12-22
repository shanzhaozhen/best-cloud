package org.shanzhaozhen.common.sms.service;


/**
 * @Author: shanzhaozhen
 * @Date: 2022-12-22
 * @Description:
 */
public interface SmsService {

    /**
     * 短信发送
     * @param phoneNumbers
     * @param signName
     * @param templateCode
     * @param templateParam
     * @throws Exception
     */
    void sendMsg(String phoneNumbers, String signName, String templateCode, String templateParam) throws Exception;

    /**
     * 短信发送(验证码)
     * @param phoneNumbers
     * @param code
     */
    void sendMsgCode(String phoneNumbers, String code) throws Exception;

}
