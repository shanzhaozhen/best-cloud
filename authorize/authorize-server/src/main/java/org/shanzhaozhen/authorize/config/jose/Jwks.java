package org.shanzhaozhen.authorize.config.jose;

import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.shanzhaozhen.uaa.pojo.dto.AuthUser;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.server.authorization.token.JwtEncodingContext;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenCustomizer;

import java.security.KeyPair;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.UUID;

/**
 * JWT：指的是 JSON Web Token，由 header.payload.signture 组成。不存在签名的JWT是不安全的，存在签名的JWT是不可窜改的。
 * JWS：指的是签过名的JWT，即拥有签名的JWT。
 * JWK：既然涉及到签名，就涉及到签名算法，对称加密还是非对称加密，那么就需要加密的 密钥或者公私钥对。此处我们将 JWT的密钥或者公私钥对统一称为 JSON WEB KEY，即 JWK。
 */
@Configuration
@RequiredArgsConstructor
public class Jwks {

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
	 * JWT内容增强
	 */
	@Bean
	OAuth2TokenCustomizer<JwtEncodingContext> jwtCustomizer() {
		return context -> {
			JwtClaimsSet.Builder claims = context.getClaims();
			Object principal = context.getPrincipal().getPrincipal();
			if (principal instanceof AuthUser) {
				AuthUser authUser = (AuthUser) principal;
				claims.claim("userId", authUser.getUserId());
				claims.claim("username", authUser.getUsername());
//				claims.claim("authorities", authUser.getAuthorities());
			}
			JwtEncodingContext.with(context.getHeaders(), claims);
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
