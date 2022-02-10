package org.shanzhaozhen.gateway.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;

/**
 * 白名单配置
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "whitelist")
public class WhiteListConfig {

    private List<String> urls;

    public List<String> getUrls() {
        return CollectionUtils.isEmpty(urls) ? Collections.emptyList() : urls;
    }

    public String[] getUrlArray() {
        return CollectionUtils.isEmpty(urls) ? new String[0] : urls.toArray(new String[0]);
    }

}
