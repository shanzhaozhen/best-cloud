package org.shanzhaozhen.commonredis.config.redisson;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * redisson 连接配置类
 */
@ConfigurationProperties(prefix = "redisson")
@Data
public class RedissonProperties {

    private String serverAddress;

    private String port;

    private String password;

    private Integer database;

    public Integer getDatabase() {
        if (null == database) {
            return 0;
        }
        return database;
    }
}
