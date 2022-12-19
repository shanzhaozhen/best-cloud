package org.shanzhaozhen.authorize.config.oauth2;


import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configurers.OAuth2AuthorizationServerConfigurer;
import org.springframework.security.oauth2.server.authorization.settings.AuthorizationServerSettings;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.util.matcher.RequestMatcher;


/**
 * 授权服务器配置
 */
@Configuration(proxyBeanMethods = false)
@RequiredArgsConstructor
public class AuthorizationServerConfig {

    private static final String CUSTOM_CONSENT_PAGE_URI = "/oauth2/consent";

    @Value("${server.port}")
    private Integer serverPort;

    /**
     *  uaa 挂载 Spring Authorization Server 认证服务器
     *  定义 spring uaa 拦击链规则
     * @param http
     * @return
     * @throws Exception
     */
    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE)
    public SecurityFilterChain authorizationServerSecurityFilterChain(HttpSecurity http) throws Exception {
        OAuth2AuthorizationServerConfigurer authorizationServerConfigurer =
                new OAuth2AuthorizationServerConfigurer();

        // 自定义确认 scope 页面
        authorizationServerConfigurer.authorizationEndpoint(authorizationEndpoint ->
                        authorizationEndpoint.consentPage(CUSTOM_CONSENT_PAGE_URI))
                .oidc(Customizer.withDefaults());	// Enable OpenID Connect 1.0

        // 提取 确认 scope 页面的端点
        RequestMatcher endpointsMatcher = authorizationServerConfigurer.getEndpointsMatcher();

        http
                .securityMatcher(endpointsMatcher)
                .authorizeHttpRequests(authorizeHttpRequests ->
                        authorizeHttpRequests.anyRequest().authenticated()
                )
                // 跨域配置
                .cors(Customizer.withDefaults())
                // 忽略掉相关端点的 csrf
                .csrf(csrf -> csrf.ignoringRequestMatchers(endpointsMatcher))
                .exceptionHandling(exceptions ->
                        exceptions.authenticationEntryPoint(new LoginUrlAuthenticationEntryPoint("/login"))
                )
                // 增加授权服务其解析 jwt 功能
                .oauth2ResourceServer(OAuth2ResourceServerConfigurer::jwt)
                // 应用 授权服务器的配置
                .apply(authorizationServerConfigurer)
        ;



        return http.build();
    }

    /**
     * 配置一些断点的路径，比如：获取token、授权端点 等
     * 配置 OAuth2.0 provider元信息
     * @return
     */
    @Bean
    public AuthorizationServerSettings authorizationServerSettings() {
        return AuthorizationServerSettings.builder()
                // 配置获取 token 的端点路径
//                .tokenEndpoint("/authentication/token")
                // 发布者的 url 地址,一般是本系统访问的根路径
                .issuer("http://localhost:" + serverPort).build();
    }

}
