package org.shanzhaozhen.authorize.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class FrontEndController {

    @GetMapping(value={
            "/login",
            "/login/**",
            "/account",
            "/social-bind",
            "/error",
            "/error/**",
            "/404",
            "/403",
            "/500"
    })
    public String front() {
        return "front/index";
    }

    @GetMapping("/")
    public String index() {
        return "redirect:/account";
    }

}
