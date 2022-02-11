package org.shanzhaozhen.authorize.config.oauth2;


import lombok.RequiredArgsConstructor;
import org.shanzhaozhen.authorize.config.oauth2.authentication.OAuth2ConfigurerUtils;
import org.shanzhaozhen.authorize.config.oauth2.authentication.OAuth2ResourceOwnerPasswordAuthenticationConverter;
import org.shanzhaozhen.authorize.config.oauth2.authentication.OAuth2ResourceOwnerPasswordAuthenticationProvider;
import org.shanzhaozhen.authorize.jackson.SecurityJacksonConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.authorization.OAuth2AuthorizationServerConfigurer;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.server.authorization.*;
import org.springframework.security.oauth2.server.authorization.client.JdbcRegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.config.ProviderSettings;
import org.springframework.security.oauth2.server.authorization.web.authentication.DelegatingAuthenticationConverter;
import org.springframework.security.oauth2.server.authorization.web.authentication.OAuth2AuthorizationCodeAuthenticationConverter;
import org.springframework.security.oauth2.server.authorization.web.authentication.OAuth2ClientCredentialsAuthenticationConverter;
import org.springframework.security.oauth2.server.authorization.web.authentication.OAuth2RefreshTokenAuthenticationConverter;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.RequestMatcher;

import java.util.Arrays;


/**
 * 授权服务器配置
 */
@Configuration(proxyBeanMethods = false)
@RequiredArgsConstructor
public class AuthorizationServerConfig {

    private static final String CUSTOM_CONSENT_PAGE_URI = "/authentication/consent";

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
        OAuth2AuthorizationServerConfigurer<HttpSecurity> authorizationServerConfigurer = new OAuth2AuthorizationServerConfigurer<>();

        // 追加 password 认证方式
        // 这种追加方式太不优雅了，持续关注该项目的里程碑 https://github.com/spring-projects/spring-authorization-server/milestone/10
        http.apply(authorizationServerConfigurer.tokenEndpoint((tokenEndpoint) -> tokenEndpoint.accessTokenRequestConverter(
            new DelegatingAuthenticationConverter(
                    Arrays.asList(
                            new OAuth2AuthorizationCodeAuthenticationConverter(),
                            new OAuth2RefreshTokenAuthenticationConverter(),
                            new OAuth2ClientCredentialsAuthenticationConverter(),
                            new OAuth2ResourceOwnerPasswordAuthenticationConverter()))
        )));

        // 自定义确认 scope 页面
        authorizationServerConfigurer.authorizationEndpoint(authorizationEndpoint -> authorizationEndpoint.consentPage(CUSTOM_CONSENT_PAGE_URI));
        // 提取 确认 scope 页面的端点
        RequestMatcher endpointsMatcher = authorizationServerConfigurer.getEndpointsMatcher();

        DefaultSecurityFilterChain securityFilterChain = http
                .requestMatcher(endpointsMatcher)
                .authorizeRequests(authorizeRequests -> authorizeRequests.anyRequest().authenticated())
                // 开启form登录
//                .formLogin()
//                .and()
                // 忽略掉相关端点的csrf
                .csrf(csrf -> csrf.ignoringRequestMatchers(endpointsMatcher))
                // 应用 授权服务器的配置
                .apply(authorizationServerConfigurer)
                .and()
                .formLogin(Customizer.withDefaults()).build();

        // 因为 build() 后会 在 OAuth2TokenEndpointConfigurer createDefaultAuthenticationProviders 中初始化 Oauth2 认证服务器的默认配置
        // 所以需要在 build 之后再追加 password 认证方式的鉴权
        addCustomOAuth2ResourceOwnerPasswordAuthenticationProvider(http);

        return securityFilterChain;
    }

    /**
     * 配置客户端
     * @return
     */
    @Bean
    public RegisteredClientRepository registeredClientRepository(JdbcTemplate jdbcTemplate) {
        // 使用内存作为客户端的信息库
//        RegisteredClient registeredClient = RegisteredClient.withId(UUID.randomUUID().toString())
//                // 客户端id 需要唯一
//                .clientId("auth")
//                // 客户端密码
//                .clientSecret("123456")
//                // 可以基于 basic 的方式和授权服务器进行认证
//                .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
//                // 授权码
//                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
//                // 刷新token
//                .authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN)
//                // 客户端模式
//                .authorizationGrantType(AuthorizationGrantType.CLIENT_CREDENTIALS)
//                // 密码模式
//                .authorizationGrantType(AuthorizationGrantType.PASSWORD)
//                // 重定向url
//                // 回调地址名单，不在此列将被拒绝 而且只能使用IP或者域名  不能使用 localhost
//                .redirectUri("http://127.0.0.1:8080/login/oauth2/code/messaging-client-oidc")
//                .redirectUri("http://127.0.0.1:8080/authorized")
//                .redirectUri("http://www.baidu.com")
//                // 客户端申请的作用域，也可以理解这个客户端申请访问用户的哪些信息，比如：获取用户信息，获取用户照片等
//                // OIDC支持
//                .scope(OidcScopes.OPENID)
//                // 其它Scope
//                .scope("all")
//                .scope("message.read")
//                .scope("message.write")
//                .clientSettings(ClientSettings
//                        .builder()
//                        // 是否需要用户确认一下客户端需要获取用户的哪些权限
//                        // 比如：客户端需要获取用户的 用户信息、用户照片 但是此处用户可以控制只给客户端授权获取 用户信息。
//                        // 配置客户端相关的配置项，包括验证密钥或者 是否需要授权页面
//                        .requireAuthorizationConsent(true).build())
//                .tokenSettings(TokenSettings.builder()
//                        // accessToken 的有效期
//                        .accessTokenTimeToLive(Duration.ofHours(1))
//                        // refreshToken 的有效期
//                        .refreshTokenTimeToLive(Duration.ofDays(3))
//                        // 是否可重用刷新令牌
//                        .reuseRefreshTokens(true)
//                        .build()
//                )
//                .build();
//        return new InMemoryRegisteredClientRepository(registeredClient);

//        return new JdbcRegisteredClientRepository(jdbcTemplate);

        // 使用数据库作为客户端的信息库
//        JdbcRegisteredClientRepository jdbcRegisteredClientRepository = new JdbcRegisteredClientRepository(jdbcTemplate);
//        jdbcRegisteredClientRepository.save(registeredClient);
//        return jdbcRegisteredClientRepository;

        JdbcRegisteredClientRepository jdbcRegisteredClientRepository = new JdbcRegisteredClientRepository(jdbcTemplate);

        // 解决json 反序列化 白名单问题
        JdbcRegisteredClientRepository.RegisteredClientRowMapper registeredClientRowMapper = new JdbcRegisteredClientRepository.RegisteredClientRowMapper();
        registeredClientRowMapper.setObjectMapper(SecurityJacksonConfig.objectMapper);
        jdbcRegisteredClientRepository.setRegisteredClientRowMapper(registeredClientRowMapper);

        return jdbcRegisteredClientRepository;
    }

    /**
     * 保存授权信息，授权服务器给我们颁发来 token，那我们肯定需要保存吧，由这个服务来保存
     * @param jdbcTemplate
     * @param registeredClientRepository
     * @return
     */
    @Bean
    public OAuth2AuthorizationService authorizationService(JdbcTemplate jdbcTemplate, RegisteredClientRepository registeredClientRepository) {
        JdbcOAuth2AuthorizationService jdbcOAuth2AuthorizationService = new JdbcOAuth2AuthorizationService(jdbcTemplate, registeredClientRepository);

        // 解决json 反序列化 白名单问题
        JdbcOAuth2AuthorizationService.OAuth2AuthorizationRowMapper authorizationRowMapper = new JdbcOAuth2AuthorizationService.OAuth2AuthorizationRowMapper(registeredClientRepository);
//        authorizationRowMapper.setLobHandler(new DefaultLobHandler());
        authorizationRowMapper.setObjectMapper(SecurityJacksonConfig.objectMapper);
        jdbcOAuth2AuthorizationService.setAuthorizationRowMapper(authorizationRowMapper);

        return jdbcOAuth2AuthorizationService;
    }

    /**
     * 如果是授权码的流程，可能客户端申请了多个权限，比如：获取用户信息，修改用户信息，此Service处理的是用户给这个客户端哪些权限，比如只给获取用户信息的权限
     * @param jdbcTemplate
     * @param registeredClientRepository
     * @return
     */
    @Bean
    public OAuth2AuthorizationConsentService authorizationConsentService(JdbcTemplate jdbcTemplate, RegisteredClientRepository registeredClientRepository) {
        return new JdbcOAuth2AuthorizationConsentService(jdbcTemplate, registeredClientRepository);
    }

    /**
     * 配置一些断点的路径，比如：获取token、授权端点 等
     * 配置 OAuth2.0 provider元信息
     * @return
     */
    @Bean
    public ProviderSettings providerSettings() {
        return ProviderSettings.builder()
                // 配置获取token的端点路径
//                .tokenEndpoint("/authentication/token")
                // 发布者的url地址,一般是本系统访问的根路径
                .issuer("http://localhost:" + serverPort).build();
    }


    /**
     * 基于默认授权服务器设置中追加 password 模式
     * @param http
     */
    private void addCustomOAuth2ResourceOwnerPasswordAuthenticationProvider(HttpSecurity http) {
        AuthenticationManager authenticationManager = http.getSharedObject(AuthenticationManager.class);

        // 弃用JwtEncoder和关联的类以准备在0.3.0发布中删除（请参阅gh-594）。
        // 该0.3.0版本将使用Spring Security 5.6JwtEncoder中引入的。
        JwtEncoder jwtEncoder = OAuth2ConfigurerUtils.getJwtEncoder(http);
        OAuth2TokenCustomizer<JwtEncodingContext> jwtCustomizer = OAuth2ConfigurerUtils.getJwtCustomizer(http);

        OAuth2ResourceOwnerPasswordAuthenticationProvider resourceOwnerPasswordAuthenticationProvider =
                new OAuth2ResourceOwnerPasswordAuthenticationProvider(
                        authenticationManager,
                        OAuth2ConfigurerUtils.getAuthorizationService(http),
                        jwtEncoder
                );
        if (jwtCustomizer != null) {
            resourceOwnerPasswordAuthenticationProvider.setJwtCustomizer(jwtCustomizer);
        }

        http.authenticationProvider(resourceOwnerPasswordAuthenticationProvider);
    }

}
