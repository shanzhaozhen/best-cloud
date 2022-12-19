package org.shanzhaozhen.authorize.converter;

import org.shanzhaozhen.uaa.pojo.dto.OAuth2TokenSettingsDTO;
import org.springframework.security.oauth2.jose.jws.SignatureAlgorithm;
import org.springframework.security.oauth2.server.authorization.settings.OAuth2TokenFormat;
import org.springframework.security.oauth2.server.authorization.settings.TokenSettings;
import org.springframework.util.StringUtils;

import java.time.Duration;
import java.util.Optional;

public class OAuth2TokenSettingsConverter {

    public static TokenSettings toTokenSettings(OAuth2TokenSettingsDTO oAuth2TokenSettingsDTO) {
        TokenSettings.Builder builder = TokenSettings.builder()
                .accessTokenTimeToLive(Optional.ofNullable(oAuth2TokenSettingsDTO.getAccessTokenTimeToLive())
                        .map(Duration::ofSeconds)
                        .orElse(Duration.ofMinutes(5)))
                .reuseRefreshTokens(oAuth2TokenSettingsDTO.isReuseRefreshTokens())
                .refreshTokenTimeToLive(Optional.ofNullable(oAuth2TokenSettingsDTO.getRefreshTokenTimeToLive())
                                .map(Duration::ofSeconds)
                                .orElse(Duration.ofMinutes(60)))
                .idTokenSignatureAlgorithm(SignatureAlgorithm.from(oAuth2TokenSettingsDTO.getIdTokenSignatureAlgorithm()));

        if (StringUtils.hasText(oAuth2TokenSettingsDTO.getAccessTokenFormat())) {
            builder.accessTokenFormat(new OAuth2TokenFormat(oAuth2TokenSettingsDTO.getAccessTokenFormat()));
        }

        return builder.build();
    }

    public static OAuth2TokenSettingsDTO toDTO(TokenSettings tokenSettings) {
        OAuth2TokenSettingsDTO.OAuth2TokenSettingsDTOBuilder builder = OAuth2TokenSettingsDTO.builder()
                .accessTokenTimeToLive(tokenSettings.getAccessTokenTimeToLive().getSeconds())
                .accessTokenFormat(Optional.ofNullable(tokenSettings.getAccessTokenFormat())
                        .map(OAuth2TokenFormat::getValue)
                        .orElse(null)
                )
                .reuseRefreshTokens(tokenSettings.isReuseRefreshTokens())
                .refreshTokenTimeToLive(tokenSettings.getRefreshTokenTimeToLive().getSeconds())
                .idTokenSignatureAlgorithm(tokenSettings.getIdTokenSignatureAlgorithm().getName());

        return builder.build();
    }
}
