package org.shanzhaozhen.common.sms.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@ConfigurationProperties(prefix = "sms")
@Configuration
@Getter
@Setter
public class SmsConfig {

    private String accessKeyId;

    private String accessKeySecret;

    private String regionId;

}
