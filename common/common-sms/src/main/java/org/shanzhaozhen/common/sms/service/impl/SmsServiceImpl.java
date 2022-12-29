package org.shanzhaozhen.common.sms.service.impl;

import com.aliyun.sdk.service.dysmsapi20170525.AsyncClient;
import com.aliyun.sdk.service.dysmsapi20170525.models.SendSmsRequest;
import com.aliyun.sdk.service.dysmsapi20170525.models.SendSmsResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.shanzhaozhen.common.core.utils.JacksonUtils;
import org.shanzhaozhen.common.sms.service.SmsService;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.concurrent.CompletableFuture;

/**
 * @Author: shanzhaozhen
 * @Date: 2022-12-22
 * @Description:
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class SmsServiceImpl implements SmsService {

    private final AsyncClient smsClient;

    @Override
    public void sendMsg(String phoneNumbers, String signName, String templateCode, String templateParam) {
        // Parameter settings for API request
        SendSmsRequest sendSmsRequest = SendSmsRequest.builder()
                .signName(signName)
                .templateCode(templateCode)
                .phoneNumbers(phoneNumbers)
                .templateParam(templateParam)
                // Request-level configuration rewrite, can set Http request parameters, etc.
                // .requestConfiguration(RequestConfiguration.create().setHttpHeaders(new HttpHeaders()))
                .build();

        // Asynchronously get the return value of the API request
        CompletableFuture<SendSmsResponse> response = smsClient.sendSms(sendSmsRequest);
        // Synchronously get the return value of the API request

        try {
            SendSmsResponse resp = response.get();

            if (log.isDebugEnabled()) {
                log.debug(JacksonUtils.toJSONString(resp));
            } else {
                if (!"ok".equals(resp.getBody().getCode())) {
                    log.error(JacksonUtils.toJSONString(resp));
                    throw new IllegalArgumentException("消息发送失败！");
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw new IllegalArgumentException("消息发送失败！");
        }

        // Asynchronous processing of return values
        /*response.thenAccept(resp -> {
            System.out.println(new Gson().toJson(resp));
        }).exceptionally(throwable -> { // Handling exceptions
            System.out.println(throwable.getMessage());
            return null;
        });*/

        // Finally, close the client
//        smsClient.close();

    }

    @Override
    public void sendMsgCode(String phoneNumbers, String code) throws Exception {
        sendMsg(phoneNumbers, "阿里云短信测试", "SMS_154950909", "{\"code\":\"" + code + "\"}");
    }
}
