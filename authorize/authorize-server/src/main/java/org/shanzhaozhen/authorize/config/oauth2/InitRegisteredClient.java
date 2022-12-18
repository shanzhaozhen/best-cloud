package org.shanzhaozhen.authorize.config.oauth2;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.core.oidc.OidcScopes;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.config.ClientSettings;
import org.springframework.security.oauth2.server.authorization.config.TokenSettings;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.UUID;

/**
 * @Author: shanzhaozhen
 * @Date: 2022-01-29
 * @Description: 初始化oauth2客户端信息
 */
//@Component
@RequiredArgsConstructor
public class InitRegisteredClient implements CommandLineRunner {

    private final RegisteredClientRepository registeredClientRepository;

    /**
     * clientSettings.tokenEndpointAuthenticationSigningAlgorithm 为 SignatureAlgorithm.RS256
     * clientSettings.jwkSetUrl 必填，授权服务器在这种模式下会校验
     * tokenSettings.idTokenSignatureAlgorithm 为 SignatureAlgorithm.RS256 ，指生成的 token 也加密
     * tokenSettings.accessTokenFormat 必须为 OAuth2TokenFormat.SELF_CONTAINED
     * clientAuthenticationMethod 为 ClientAuthenticationMethod.PRIVATE_KEY_JWT
     */
    @Override
    public void run(String... args) {
        // 使用内存作为客户端的信息库
        RegisteredClient registeredClient = RegisteredClient.withId(UUID.randomUUID().toString())
                // 客户端 id 需要唯一
                .clientId("efd7527b-39d0-468c-9bd6-ff945a696982")
                // 客户端名称
                .clientName("auth")
                // 客户端密码
                .clientSecret("123456")
                .clientAuthenticationMethod(ClientAuthenticationMethod.NONE)
                // 可以基于 basic 的方式和授权服务器进行认证
                .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
                // 授权码
                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                // 刷新token
                .authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN)
                // 客户端模式
                .authorizationGrantType(AuthorizationGrantType.CLIENT_CREDENTIALS)
                // 密码模式
//                .authorizationGrantType(AuthorizationGrantType.PASSWORD)
                // 重定向url
                // 回调地址名单，不在此列将被拒绝 而且只能使用IP或者域名  不能使用 localhost
                .redirectUri("http://127.0.0.1:8080/login/oauth2/code/auth-oidc")
                .redirectUri("http://localhost:8080/login/oauth2/code/auth-oidc")
                .redirectUri("http://127.0.0.1:4200/silent-renew.html")
                .redirectUri("http://localhost:4200/silent-renew.html")
                .redirectUri("http://127.0.0.1:8000/oidc")
                .redirectUri("http://localhost:8000/oidc")
                // 客户端申请的作用域，也可以理解这个客户端申请访问用户的哪些信息，比如：获取用户信息，获取用户照片等
                // OIDC支持
                .scope(OidcScopes.OPENID)
                // 其它Scope
                .scope("message.read")
                .scope("message.write")
                .clientSettings(ClientSettings
                        .builder()
                        // 是否需要用户确认一下客户端需要获取用户的哪些权限
                        // 比如：客户端需要获取用户的 用户信息、用户照片 但是此处用户可以控制只给客户端授权获取 用户信息。
                        // 配置客户端相关的配置项，包括验证密钥或者 是否需要授权页面
                        // .requireProofKey(true) 及 clientAuthenticationMethod(ClientAuthenticationMethod.NONE) 才可以使用 PKCE
                        .requireAuthorizationConsent(true)
                        .requireProofKey(true)
                        .build()
                )
                .tokenSettings(TokenSettings.builder()
                        // accessToken 的有效期
                        .accessTokenTimeToLive(Duration.ofHours(1))
                        // refreshToken 的有效期
                        .refreshTokenTimeToLive(Duration.ofDays(3))
                        // 是否可重用刷新令牌
                        .reuseRefreshTokens(true)
                        .build()
                )
                .build();

        registeredClientRepository.save(registeredClient);
    }
}
