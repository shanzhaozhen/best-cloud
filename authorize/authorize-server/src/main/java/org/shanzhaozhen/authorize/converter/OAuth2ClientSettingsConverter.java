package org.shanzhaozhen.authorize.converter;

import org.shanzhaozhen.authorize.pojo.dto.OAuth2ClientSettingsDTO;
import org.springframework.security.oauth2.jose.jws.JwsAlgorithm;
import org.springframework.security.oauth2.jose.jws.SignatureAlgorithm;
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings;
import org.springframework.util.StringUtils;

public class OAuth2ClientSettingsConverter {

    public static ClientSettings toClientSettings(OAuth2ClientSettingsDTO oauth2ClientSettingsDTO) {
        ClientSettings.Builder builder = ClientSettings.builder()
                .requireProofKey(oauth2ClientSettingsDTO.isRequireProofKey())
                .requireAuthorizationConsent(oauth2ClientSettingsDTO.isRequireAuthorizationConsent());

        if (StringUtils.hasText(oauth2ClientSettingsDTO.getJwkSetUrl())) {
            builder.jwkSetUrl(oauth2ClientSettingsDTO.getJwkSetUrl());
        }

        if (StringUtils.hasText(oauth2ClientSettingsDTO.getTokenEndpointAuthenticationSigningAlgorithm())) {
            builder.tokenEndpointAuthenticationSigningAlgorithm(SignatureAlgorithm.from(oauth2ClientSettingsDTO.getTokenEndpointAuthenticationSigningAlgorithm()));
        }

        return builder.build();
    }

    public static OAuth2ClientSettingsDTO toDTO(ClientSettings clientSettings) {
        OAuth2ClientSettingsDTO.OAuth2ClientSettingsDTOBuilder builder = OAuth2ClientSettingsDTO.builder()
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
