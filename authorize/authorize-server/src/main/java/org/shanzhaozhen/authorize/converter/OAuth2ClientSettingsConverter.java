package org.shanzhaozhen.authorize.converter;

import org.shanzhaozhen.authorize.pojo.dto.OAuth2ClientSettingsDTO;
import org.springframework.security.oauth2.jose.jws.SignatureAlgorithm;
import org.springframework.security.oauth2.server.authorization.config.ClientSettings;

public class OAuth2ClientSettingsConverter {

    public static ClientSettings toClientSettings(OAuth2ClientSettingsDTO oAuth2ClientSettingsDTO) {
        ClientSettings.Builder builder = ClientSettings.builder()
                .requireProofKey(oAuth2ClientSettingsDTO.getRequireProofKey())
                .requireAuthorizationConsent(oAuth2ClientSettingsDTO.getRequireAuthorizationConsent())
                .jwkSetUrl(oAuth2ClientSettingsDTO.getJwkSetUrl())
                .tokenEndpointAuthenticationSigningAlgorithm(SignatureAlgorithm.from(oAuth2ClientSettingsDTO.getTokenEndpointAuthenticationSigningAlgorithm()));
        return builder.build();
    }

}
