package org.shanzhaozhen.authorize.config.jose;

import com.nimbusds.jose.jwk.RSAKey;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.shanzhaozhen.common.utils.SpringContextUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.security.KeyPair;
import java.security.KeyStore;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.UUID;


@Configuration
@RequiredArgsConstructor
public class Jwks {

	private final KeyPair keyPair;

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
