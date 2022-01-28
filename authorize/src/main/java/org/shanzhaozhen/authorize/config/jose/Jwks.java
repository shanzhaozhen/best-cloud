package org.shanzhaozhen.authorize.config.jose;

import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.shanzhaozhen.uaa.dto.AuthUser;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.server.authorization.JwtEncodingContext;
import org.springframework.security.oauth2.server.authorization.OAuth2TokenCustomizer;

import java.security.KeyPair;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.UUID;


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
