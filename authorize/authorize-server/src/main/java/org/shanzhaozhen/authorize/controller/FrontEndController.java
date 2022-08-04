package org.shanzhaozhen.authorize.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class FrontEndController {

//    @RequestMapping("/api/**")
//    public ApiResult api(HttpServletRequest request, HttpServletResponse response){
//        return apiProxy.proxy(request, response);
//    }

    @GetMapping(value={
//            "/",
            "/login"
    })
    public String index(){
        return "front/index";
    }

}
