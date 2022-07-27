package org.shanzhaozhen.uaa.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.shanzhaozhen.common.core.result.R;
import org.shanzhaozhen.uaa.service.SmsService;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "sms", description = "信息发送接口")
@RestController
@RequiredArgsConstructor
public class SmsController {

    private final SmsService smsService;

    public R<?> sendCode(@RequestParam("phone") String phone){
        smsService.sendCode(phone);
        return R.ok();
    }


}