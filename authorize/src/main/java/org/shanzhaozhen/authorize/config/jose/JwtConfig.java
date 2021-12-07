package org.shanzhaozhen.authorize.config.jose;


import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@ConfigurationProperties(prefix = "jwt")
@Configuration
@Getter
@Setter
public class JwtConfig {

    private String path;

    private String alias;

    private String password;

}
