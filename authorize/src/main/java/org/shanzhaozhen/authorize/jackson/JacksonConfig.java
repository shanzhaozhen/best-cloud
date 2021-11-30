package org.shanzhaozhen.authorize.jackson;

import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.jackson2.SecurityJackson2Modules;
import org.springframework.security.oauth2.server.authorization.jackson2.OAuth2AuthorizationServerJackson2Module;

import java.util.List;

@Configuration
public class JacksonConfig {

    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModules(SecurityJackson2Modules.getModules(  ));
        mapper.registerModule(new AuthUserJackson2Module());
        return mapper;
    }

    public Jackson2ObjectMapperBuilderCustomizer jackson2ObjectMapperBuilderCustomizer() {
        return builder -> {
            //jackson反序列化 安全白名单
            builder.mixIn(OpenAppUser.class, OpenAppUserMixin.class);
            List<Module> securityModules = SecurityJackson2Modules.getModules(LocalDateTimeSerializerConfig.class.getClassLoader());
            securityModules.add(new OAuth2AuthorizationServerJackson2Module());
            builder.modulesToInstall(securityModules.toArray(new Module[securityModules.size()]));
        };
    }

}
