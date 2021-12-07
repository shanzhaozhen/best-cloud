package org.shanzhaozhen.authorize.config.jose;

import com.nimbusds.jose.jwk.RSAKey;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.shanzhaozhen.common.utils.SpringContextUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import java.security.KeyPair;
import java.security.KeyStore;

@Configuration
@RequiredArgsConstructor
public class KeyGenerator {

	private final JwtConfig jwtConfig;

	@SneakyThrows
	@Bean
	public KeyPair generateRsaKey() {
		ClassPathResource resource = new ClassPathResource(jwtConfig.getPath());
		KeyStore jks = KeyStore.getInstance("jks");
		char[] pin = jwtConfig.getPassword().toCharArray();
		jks.load(resource.getInputStream(), pin);
		return RSAKey.load(jks, jwtConfig.getAlias(), pin).toKeyPair();
	}

}
