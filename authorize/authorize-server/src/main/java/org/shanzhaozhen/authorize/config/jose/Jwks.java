package org.shanzhaozhen.authorize.config.jose;

import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.shanzhaozhen.authorize.pojo.dto.AuthUser;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.oidc.endpoint.OidcParameterNames;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.server.authorization.OAuth2TokenType;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configuration.OAuth2AuthorizationServerConfiguration;
import org.springframework.security.oauth2.server.authorization.token.JwtEncodingContext;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenCustomizer;

import java.security.KeyPair;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.*;
import java.util.stream.Collectors;


/**
 * JWT：指的是 JSON Web Token，由 header.payload.signture 组成。不存在签名的JWT是不安全的，存在签名的JWT是不可窜改的。
 * JWS：指的是签过名的JWT，即拥有签名的JWT。
 * JWK：既然涉及到签名，就涉及到签名算法，对称加密还是非对称加密，那么就需要加密的 密钥或者公私钥对。此处我们将 JWT的密钥或者公私钥对统一称为 JSON WEB KEY，即 JWK。
 */
@Configuration
@RequiredArgsConstructor
public class Jwks {

	private static final OAuth2TokenType ID_TOKEN_TOKEN_TYPE = new OAuth2TokenType(OidcParameterNames.ID_TOKEN);

	private final KeyPair keyPair;

	/**
	 * 对 JWT 进行签名的 加解密密钥
	 * @return
	 */
	@SneakyThrows
	@Bean
	public JWKSource<SecurityContext> jwkSource() {
		RSAKey rsaKey = generateRsa();
		JWKSet jwkSet = new JWKSet(rsaKey);
		return (jwkSelector, securityContext) -> jwkSelector.select(jwkSet);
	}

	/**
	 * 对 JWT 解密
	 * @return
	 */
	@Bean
	public JwtDecoder jwtDecoder(JWKSource<SecurityContext> jwkSource) {
		return OAuth2AuthorizationServerConfiguration.jwtDecoder(jwkSource);
	}

	/**
	 * JWT内容增强
	 */
	@Bean
	OAuth2TokenCustomizer<JwtEncodingContext> jwtCustomizer() {
		return context -> {
			JwtClaimsSet.Builder claims = context.getClaims();
			Object principal = context.getPrincipal().getPrincipal();
			OAuth2TokenType tokenType = context.getTokenType();

			if (principal instanceof AuthUser) {
				AuthUser authUser = (AuthUser) principal;
				claims.claim("userId", authUser.getUserId());
				claims.claim("username", authUser.getUsername());

				// access token
				if (OAuth2TokenType.ACCESS_TOKEN.equals(tokenType)) {
					claims.claim("authorities", Optional.ofNullable(authUser.getAuthorities())
							.map(o -> o.stream().map(GrantedAuthority::getAuthority).collect(Collectors.toSet()))
							.orElse(Collections.emptySet()));

				} else if (ID_TOKEN_TOKEN_TYPE.equals(context.getTokenType())) {			// idToken

				}

			}

			JwtEncodingContext.with(context.getJwsHeader(), claims);
		};
	}

	// 生成公钥
	public RSAPublicKey getPublicKey() {
		return (RSAPublicKey) keyPair.getPublic();
	}

	// 生成私钥
	public RSAPrivateKey getPrivateKey() {
		return (RSAPrivateKey) keyPair.getPrivate();
	}

	public RSAKey generateRsa() {
		return new RSAKey.Builder(getPublicKey())
				.privateKey(getPrivateKey())
				.keyID(UUID.randomUUID().toString())
				.build();
	}

}
