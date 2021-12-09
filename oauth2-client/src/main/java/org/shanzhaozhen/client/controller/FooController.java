package org.shanzhaozhen.client.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * The type Foo controller.
 */
@RestController
@RequestMapping("/god")
public class FooController {

    /**
     * redirect_uri
     *
     * @param client ID为{@code god-auth}的客户端信息，注意ClientRegistration中必须包含该ID
     * @return the map
     */
    @GetMapping("/bar")
    public Map<String,Object> bar(@RegisteredOAuth2AuthorizedClient("god-auth") OAuth2AuthorizedClient client){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Map<String, Object> map = new HashMap<>();
        map.put("authentication",authentication);
        map.put("oAuth2AuthorizedClient",client);
        return map;
    }

}
