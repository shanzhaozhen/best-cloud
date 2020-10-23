package org.shanzhaozhen.authorize.config.oauth2;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.InputStream;
import java.security.*;
import java.security.cert.CertificateException;

@Slf4j
public class JksUtil {

    public static void main(String[] args) throws Exception {
        PrivateKey privateKey = getPrivateKey("jwt.jks", "123456", "jwt");
        log.info("{}", privateKey);
        PublicKey publicKey = getPublicKey("jwt.jks", "123456", "jwt");
        log.info("{}", publicKey);
    }

    private static PrivateKey getPrivateKey(String fileName, String password, String alias) throws KeyStoreException,
            IOException, NoSuchAlgorithmException, CertificateException, UnrecoverableKeyException {
        InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(fileName);

        KeyStore keyStore = KeyStore.getInstance("JKS");
        keyStore.load(inputStream, password.toCharArray());

        return (PrivateKey) keyStore.getKey(alias, password.toCharArray());
    }

    private static PublicKey getPublicKey(String fileName, String password, String alias) throws KeyStoreException,
            IOException, NoSuchAlgorithmException, CertificateException, UnrecoverableKeyException {
        InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(fileName);

        KeyStore keyStore = KeyStore.getInstance("JKS");
        keyStore.load(inputStream, password.toCharArray());

        return keyStore.getCertificate(alias).getPublicKey();
    }


}
