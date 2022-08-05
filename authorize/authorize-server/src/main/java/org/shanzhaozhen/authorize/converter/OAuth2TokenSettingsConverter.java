package org.shanzhaozhen.authorize.converter;

import org.shanzhaozhen.authorize.pojo.dto.OAuth2TokenSettingsDTO;
import org.shanzhaozhen.authorize.pojo.entity.OAuth2TokenSettingsDO;
import org.springframework.beans.BeanUtils;
import org.springframework.security.oauth2.core.OAuth2TokenFormat;
import org.springframework.security.oauth2.jose.jws.SignatureAlgorithm;
import org.springframework.security.oauth2.server.authorization.config.TokenSettings;

public class OAuth2TokenSettingsConverter {

    public static TokenSettings toTokenSettings(OAuth2TokenSettingsDTO oAuth2TokenSettingsDTO) {
        TokenSettings.Builder builder = TokenSettings.builder()
                .accessTokenTimeToLive(oAuth2TokenSettingsDTO.getAccessTokenTimeToLive())
                .accessTokenFormat(new OAuth2TokenFormat(oAuth2TokenSettingsDTO.getAccessTokenFormat()))
                .reuseRefreshTokens(oAuth2TokenSettingsDTO.isReuseRefreshTokens())
                .refreshTokenTimeToLive(oAuth2TokenSettingsDTO.getRefreshTokenTimeToLive())
                .idTokenSignatureAlgorithm(SignatureAlgorithm.from(oAuth2TokenSettingsDTO.getIdTokenSignatureAlgorithm()));
        return builder.build();
    }

    public static OAuth2TokenSettingsDTO toDTO(TokenSettings tokenSettings) {
        OAuth2TokenSettingsDTO.OAuth2TokenSettingsDTOBuilder builder = OAuth2TokenSettingsDTO.builder()
                .accessTokenTimeToLive(tokenSettings.getAccessTokenTimeToLive())
                .accessTokenFormat(tokenSettings.getAccessTokenFormat().getValue())
                .reuseRefreshTokens(tokenSettings.isReuseRefreshTokens())
                .idTokenSignatureAlgorithm(tokenSettings.getIdTokenSignatureAlgorithm().getName());
        return builder.build();
    }

    public static OAuth2TokenSettingsDO toDO(OAuth2TokenSettingsDTO oAuth2TokenSettingsDTO) {
        OAuth2TokenSettingsDO oAuth2TokenSettingsDO = new OAuth2TokenSettingsDO();
        BeanUtils.copyProperties(oAuth2TokenSettingsDTO, oAuth2TokenSettingsDO);
        return oAuth2TokenSettingsDO;
    }

}
