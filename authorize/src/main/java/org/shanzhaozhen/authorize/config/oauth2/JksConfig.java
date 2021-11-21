package org.shanzhaozhen.authorize.config.oauth2;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@ConfigurationProperties(prefix = "jks")
@Configuration
@Getter
@Setter
public class JksConfig {

    private String path;

    private String alias;

    private String secret;

}
