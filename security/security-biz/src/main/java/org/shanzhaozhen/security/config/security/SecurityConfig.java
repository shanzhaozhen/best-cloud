package org.shanzhaozhen.security.config.security;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@ConfigurationProperties(prefix = "security")
@Configuration
@Getter
@Setter
public class SecurityConfig {

    private String[] whiteList;

}
