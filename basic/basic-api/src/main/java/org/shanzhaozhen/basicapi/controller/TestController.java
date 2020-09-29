package org.shanzhaozhen.basicapi.controller;

import io.swagger.annotations.Api;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Api("用户测试接口")
@Controller
public class TestController {

    @GetMapping("/test")
    @ResponseBody
    public Object testInstall() {
        return "INSTALL SUCCESS!";
    }

}
