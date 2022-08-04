package org.shanzhaozhen.authorize.converter;

import org.shanzhaozhen.authorize.pojo.dto.OAuth2TokenSettingsDTO;
import org.springframework.security.oauth2.core.OAuth2TokenFormat;
import org.springframework.security.oauth2.jose.jws.SignatureAlgorithm;
import org.springframework.security.oauth2.server.authorization.config.TokenSettings;

public class OAuth2TokenSettingsConverter {

    public static TokenSettings toTokenSettings(OAuth2TokenSettingsDTO oAuth2TokenSettingsDTO) {
        TokenSettings.Builder builder = TokenSettings.builder()
                .accessTokenTimeToLive(oAuth2TokenSettingsDTO.getAccessTokenTimeToLive())
                .accessTokenFormat(new OAuth2TokenFormat(oAuth2TokenSettingsDTO.getAccessTokenFormat()))
                .reuseRefreshTokens(oAuth2TokenSettingsDTO.getReuseRefreshTokens())
                .refreshTokenTimeToLive(oAuth2TokenSettingsDTO.getRefreshTokenTimeToLive())
                .idTokenSignatureAlgorithm(SignatureAlgorithm.from(oAuth2TokenSettingsDTO.getIdTokenSignatureAlgorithm()));
        return builder.build();
    }

}
