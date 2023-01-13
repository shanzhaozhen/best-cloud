package org.shanzhaozhen.authorize.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Collections;

/**
 * @Author: shanzhaozhen
 * @Date: 2022-08-02
 * @Description: 配置允许跨域
 */
@Configuration
public class CorsConfig {

    @Bean
    protected CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        // 允许全部请求头
        corsConfiguration.addAllowedHeader("*");
        // 允许所有请求方法
        corsConfiguration.addAllowedMethod("*");
        // 允许携带 Authorization 头
        corsConfiguration.setAllowCredentials(true);
        // 允许所有域，当请求头
        corsConfiguration.addAllowedOriginPattern("*");

        // 允许全部请求路径
        source.registerCorsConfiguration("/**", corsConfiguration);
        return source;
    }

}