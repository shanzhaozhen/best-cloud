package org.shanzhaozhen.uaa.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: shanzhaozhen
 * @Date: 2022-07-25
 * @Description:
 */
@ConfigurationProperties(prefix = "jwt")
@Configuration
@Getter
@Setter
public class JwtConfig {

    public static final String tokenHead = "Bearer ";

    private String issuer;

    private String secret;

    private long expiration;

    private long rememberExpiration;

}
