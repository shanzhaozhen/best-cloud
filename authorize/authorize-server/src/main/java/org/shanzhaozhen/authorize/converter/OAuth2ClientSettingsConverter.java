package org.shanzhaozhen.authorize.converter;

import org.shanzhaozhen.authorize.pojo.entity.OAuth2ClientSettingsDO;
import org.springframework.security.oauth2.jose.jws.JwsAlgorithm;
import org.springframework.security.oauth2.jose.jws.SignatureAlgorithm;
import org.springframework.security.oauth2.server.authorization.config.ClientSettings;
import org.springframework.util.StringUtils;

public class OAuth2ClientSettingsConverter {

    public static ClientSettings toClientSettings(OAuth2ClientSettingsDO oAuth2ClientSettingsDO) {
        ClientSettings.Builder builder = ClientSettings.builder()
                .requireProofKey(oAuth2ClientSettingsDO.isRequireProofKey())
                .requireAuthorizationConsent(oAuth2ClientSettingsDO.isRequireAuthorizationConsent());

        if (StringUtils.hasText(oAuth2ClientSettingsDO.getJwkSetUrl())) {
            builder.jwkSetUrl(oAuth2ClientSettingsDO.getJwkSetUrl());
        }

        if (StringUtils.hasText(oAuth2ClientSettingsDO.getTokenEndpointAuthenticationSigningAlgorithm())) {
            builder.tokenEndpointAuthenticationSigningAlgorithm(SignatureAlgorithm.from(oAuth2ClientSettingsDO.getTokenEndpointAuthenticationSigningAlgorithm()));
        }

        return builder.build();
    }

    public static OAuth2ClientSettingsDO toDO(ClientSettings clientSettings) {
        OAuth2ClientSettingsDO.OAuth2ClientSettingsDOBuilder builder = OAuth2ClientSettingsDO.builder()
                .requireProofKey(clientSettings.isRequireProofKey())
                .requireAuthorizationConsent(clientSettings.isRequireAuthorizationConsent())
                .jwkSetUrl(clientSettings.getJwkSetUrl());

        JwsAlgorithm tokenEndpointAuthenticationSigningAlgorithm = clientSettings.getTokenEndpointAuthenticationSigningAlgorithm();
        if (tokenEndpointAuthenticationSigningAlgorithm != null) {
            builder.tokenEndpointAuthenticationSigningAlgorithm(tokenEndpointAuthenticationSigningAlgorithm.getName());
        }

        return builder.build();
    }

}
