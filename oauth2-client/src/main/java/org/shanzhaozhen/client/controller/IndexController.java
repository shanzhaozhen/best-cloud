package org.shanzhaozhen.client.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * The type Index controller.
 */
@RestController
public class IndexController {


    /**
     * 登录成功后默认跳转页
     *
     * @param authentication 当前认证信息
     * @return the map
     */
    @GetMapping("/")
    public Map<String, Object> index(@RegisteredOAuth2AuthorizedClient("god-oidc") OAuth2AuthorizedClient client , @CurrentSecurityContext(expression = "authentication") Authentication authentication) {
        HashMap<String, Object> map = new HashMap<>();
           map.put("authentication", authentication);
           map.put("client", client);
        return map;
    }
}
