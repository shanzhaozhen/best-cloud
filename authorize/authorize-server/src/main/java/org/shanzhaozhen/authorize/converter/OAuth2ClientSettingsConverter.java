package org.shanzhaozhen.authorize.converter;

import org.shanzhaozhen.authorize.pojo.dto.OAuth2ClientSettingsDTO;
import org.shanzhaozhen.authorize.pojo.entity.OAuth2ClientSettingsDO;
import org.springframework.beans.BeanUtils;
import org.springframework.security.oauth2.jose.JwaAlgorithm;
import org.springframework.security.oauth2.jose.jws.JwsAlgorithm;
import org.springframework.security.oauth2.jose.jws.SignatureAlgorithm;
import org.springframework.security.oauth2.server.authorization.config.ClientSettings;
import org.springframework.util.StringUtils;

import java.util.Optional;

import static org.springframework.security.oauth2.jose.jws.JwsAlgorithms.RS256;

public class OAuth2ClientSettingsConverter {

    public static ClientSettings toClientSettings(OAuth2ClientSettingsDTO oAuth2ClientSettingsDTO) {
        ClientSettings.Builder builder = ClientSettings.builder()
                .requireProofKey(oAuth2ClientSettingsDTO.isRequireProofKey())
                .requireAuthorizationConsent(oAuth2ClientSettingsDTO.isRequireAuthorizationConsent());

        if (StringUtils.hasText(oAuth2ClientSettingsDTO.getJwkSetUrl())) {
            builder.jwkSetUrl(oAuth2ClientSettingsDTO.getJwkSetUrl());
        }

        if (StringUtils.hasText(oAuth2ClientSettingsDTO.getTokenEndpointAuthenticationSigningAlgorithm())) {
            builder.tokenEndpointAuthenticationSigningAlgorithm(SignatureAlgorithm.from(oAuth2ClientSettingsDTO.getTokenEndpointAuthenticationSigningAlgorithm()));
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

    public static OAuth2ClientSettingsDO toDO(OAuth2ClientSettingsDTO oAuth2ClientSettingsDTO) {
        OAuth2ClientSettingsDO oAuth2ClientSettingsDO = new OAuth2ClientSettingsDO();
        BeanUtils.copyProperties(oAuth2ClientSettingsDTO, oAuth2ClientSettingsDO);
        return oAuth2ClientSettingsDO;
    }

}
