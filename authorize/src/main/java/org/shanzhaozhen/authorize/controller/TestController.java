package org.shanzhaozhen.authorize.controller;

import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class TestController {

    @ApiOperation(value = "测试")
    @GetMapping("/test")
    public String getPublicKey() {
        return "test yes";
    }

}
