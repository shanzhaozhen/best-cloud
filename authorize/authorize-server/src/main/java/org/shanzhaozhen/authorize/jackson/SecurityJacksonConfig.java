package org.shanzhaozhen.authorize.jackson;

import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.shanzhaozhen.uaa.pojo.dto.AuthUser;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.jackson2.SecurityJackson2Modules;
import org.springframework.security.jackson2.SimpleGrantedAuthorityMixin;
import org.springframework.security.oauth2.server.authorization.jackson2.OAuth2AuthorizationServerJackson2Module;

import java.util.List;

public class SecurityJacksonConfig {

    public static final ObjectMapper objectMapper = new ObjectMapper();

    static  {
        ClassLoader classLoader = SecurityJacksonConfig.class.getClassLoader();
        List<Module> securityModules = SecurityJackson2Modules.getModules(classLoader);
        objectMapper.registerModules(securityModules);
        objectMapper.registerModule(new OAuth2AuthorizationServerJackson2Module());
        //        objectMapper.registerModule(new SecurityJackson2Module());
        objectMapper.addMixIn(AuthUser.class, AuthUserMixin.class);
    }

}
