package org.shanzhaozhen.gateway.config;

import lombok.RequiredArgsConstructor;
import org.shanzhaozhen.common.enums.AuthConstants;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.oauth2.server.resource.authentication.ReactiveJwtAuthenticationConverterAdapter;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.util.CollectionUtils;
import org.springframework.web.reactive.config.EnableWebFlux;
import reactor.core.publisher.Mono;

import java.util.Collections;
import java.util.List;

@RequiredArgsConstructor
// 注解需要使用@EnableWebFluxSecurity而非@EnableWebSecurity，因为SpringCloud Gateway基于WebFlux
@EnableWebFluxSecurity
public class ResourceServerConfig {

    private final CustomAuthorizationManager customAuthorizationManager;

    private final CustomServerAccessDeniedHandler customServerAccessDeniedHandler;

    private final CustomServerAuthenticationEntryPoint customServerAuthenticationEntryPoint;

    private final WhiteListConfig whiteListConfig;

    @Bean
    public SecurityWebFilterChain securityWebFilterChain( ServerHttpSecurity http) {
        // 处理白名单
        List<String> urls = whiteListConfig.getUrls();
        if (!CollectionUtils.isEmpty(urls)) {
            http.authorizeExchange().pathMatchers(urls.toArray(new String[0])).permitAll();
        }

        http
                .oauth2ResourceServer().jwt()
//                .jwtAuthenticationConverter(jwtAuthenticationConverter())
                .and()
                .authenticationEntryPoint(customServerAuthenticationEntryPoint)         // 自定义处理JWT请求头过期或签名错误的结果
                .and()
                .authorizeExchange()
//                .pathMatchers(whiteListConfig.getWhiteList()).permitAll()
                .anyExchange().access(customAuthorizationManager)
                .and()
                .exceptionHandling()
                .accessDeniedHandler(customServerAccessDeniedHandler) // 处理未授权
                .authenticationEntryPoint(customServerAuthenticationEntryPoint) //处理未认证
                .and().csrf().disable();

        return http.build();
    }

    /**
     * @link https://blog.csdn.net/qq_24230139/article/details/105091273
     * ServerHttpSecurity 没有将jwt中authorities的负载部分当做Authentication
     * 需要把jwt的Claim中的authorities加入
     * 方案：重新定义 ReactiveAuthenticationManager 权限管理器，默认转换器 JwtGrantedAuthoritiesConverter
     * @return
     */
    @Bean
    public Converter<Jwt, ? extends Mono<? extends AbstractAuthenticationToken>> jwtAuthenticationConverter() {
        JwtGrantedAuthoritiesConverter jwtGrantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
        jwtGrantedAuthoritiesConverter.setAuthorityPrefix(AuthConstants.AUTHORITY_PREFIX);
        jwtGrantedAuthoritiesConverter.setAuthoritiesClaimName(AuthConstants.AUTHORITY_CLAIM_NAME);

        JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(jwtGrantedAuthoritiesConverter);
        return new ReactiveJwtAuthenticationConverterAdapter(jwtAuthenticationConverter);
    }

}
