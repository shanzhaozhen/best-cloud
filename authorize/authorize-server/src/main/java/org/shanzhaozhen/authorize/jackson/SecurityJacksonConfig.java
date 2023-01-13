package org.shanzhaozhen.authorize.jackson;

import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.jackson2.SecurityJackson2Modules;
import org.springframework.security.oauth2.server.authorization.jackson2.OAuth2AuthorizationServerJackson2Module;

import java.util.Collections;
import java.util.List;

public class SecurityJacksonConfig {

    public static final ObjectMapper objectMapper = new ObjectMapper();

    static  {
        ClassLoader classLoader = SecurityJacksonConfig.class.getClassLoader();
        List<Module> securityModules = SecurityJackson2Modules.getModules(classLoader);
        securityModules.add(new OAuth2AuthorizationServerJackson2Module());
        securityModules.add(new SecurityJackson2Module());
        objectMapper.registerModules(securityModules);
    }

}
