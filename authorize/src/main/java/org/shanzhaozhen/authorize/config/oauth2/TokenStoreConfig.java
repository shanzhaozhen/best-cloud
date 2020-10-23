package org.shanzhaozhen.authorize.config.oauth2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;

import javax.sql.DataSource;

@Configuration
public class TokenStoreConfig {

//    @Autowired
//    private RedisConnectionFactory redisConnectionFactory;
//
//    @Autowired
//    private DataSource dataSource;
//
//    @Bean
//    public TokenStore redisTokenStore () {
//        return new RedisTokenStore(redisConnectionFactory);
//    }
//
//    @Bean
//    public TokenStore jdbcTokenStore () {
//        return new JdbcTokenStore(dataSource);
//    }


}
