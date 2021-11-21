package org.shanzhaozhen.authorize.config.jose;

import com.nimbusds.jose.jwk.RSAKey;
import org.shanzhaozhen.authorize.config.oauth2.JksConfig;
import org.shanzhaozhen.common.utils.SpringContextUtils;
import org.springframework.core.io.ClassPathResource;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.math.BigInteger;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.KeyStore;
import java.security.spec.ECFieldFp;
import java.security.spec.ECParameterSpec;
import java.security.spec.ECPoint;
import java.security.spec.EllipticCurve;


final class KeyGeneratorUtils {

	static SecretKey generateSecretKey() {
		SecretKey hmacKey;
		try {
			hmacKey = KeyGenerator.getInstance("HmacSha256").generateKey();
		} catch (Exception ex) {
			throw new IllegalStateException(ex);
		}
		return hmacKey;
	}

	static KeyPair generateRsaKey() {
		KeyPair keyPair;
		try {
			// Java提供了KeyPairGenerator类。此类用于生成公钥和私钥对。
			// KeyPairGenerator类提供getInstance()方法，该方法接受表示所需密钥生成算法的String变量，并返回生成密钥的KeyPairGenerator对象。
			KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
			// KeyPairGenerator类提供了一个名为initialize()的方法，该方法用于初始化密钥对生成器。 此方法接受表示密钥大小的整数值。
			keyPairGenerator.initialize(2048);
			// 可以使用KeyPairGenerator类的generateKeyPair()方法生成KeyPair。
			keyPair = keyPairGenerator.generateKeyPair();
		} catch (Exception ex) {
			throw new IllegalStateException(ex);
		}
		return keyPair;
	}

	static KeyPair generateJksKey() {
		KeyPair keyPair;
		try {
			JksConfig jksConfig = (JksConfig) SpringContextUtils.getBean("jksConfig");

			ClassPathResource resource = new ClassPathResource(jksConfig.getPath());
			KeyStore jks = KeyStore.getInstance("jks");
			char[] pin = jksConfig.getSecret().toCharArray();
			jks.load(resource.getInputStream(), pin);
			keyPair = RSAKey.load(jks, jksConfig.getAlias(), pin).toKeyPair();
		} catch (Exception ex) {
			throw new IllegalStateException(ex);
		}
		return keyPair;
	}

	static KeyPair generateEcKey() {
		EllipticCurve ellipticCurve = new EllipticCurve(
				new ECFieldFp(
						new BigInteger("115792089210356248762697446949407573530086143415290314195533631308867097853951")),
				new BigInteger("115792089210356248762697446949407573530086143415290314195533631308867097853948"),
				new BigInteger("41058363725152142129326129780047268409114441015993725554835256314039467401291"));
		ECPoint ecPoint = new ECPoint(
				new BigInteger("48439561293906451759052585252797914202762949526041747995844080717082404635286"),
				new BigInteger("36134250956749795798585127919587881956611106672985015071877198253568414405109"));
		ECParameterSpec ecParameterSpec = new ECParameterSpec(
				ellipticCurve,
				ecPoint,
				new BigInteger("115792089210356248762697446949407573529996955224135760342422259061068512044369"),
				1);

		KeyPair keyPair;
		try {
			KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("EC");
			keyPairGenerator.initialize(ecParameterSpec);
			keyPair = keyPairGenerator.generateKeyPair();
		} catch (Exception ex) {
			throw new IllegalStateException(ex);
		}
		return keyPair;
	}
}
