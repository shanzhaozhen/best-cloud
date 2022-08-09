package org.shanzhaozhen.authorize.converter;

import org.shanzhaozhen.authorize.pojo.entity.OAuth2TokenSettingsDO;
import org.springframework.security.oauth2.core.OAuth2TokenFormat;
import org.springframework.security.oauth2.jose.jws.SignatureAlgorithm;
import org.springframework.security.oauth2.server.authorization.config.TokenSettings;
import org.springframework.util.StringUtils;

import java.time.Duration;
import java.util.Optional;

public class OAuth2TokenSettingsConverter {

    public static TokenSettings toTokenSettings(OAuth2TokenSettingsDO oAuth2TokenSettingsDO) {
        TokenSettings.Builder builder = TokenSettings.builder()
                .accessTokenTimeToLive(Optional.ofNullable(oAuth2TokenSettingsDO.getAccessTokenTimeToLive())
                        .map(Duration::ofSeconds)
                        .orElse(Duration.ofMinutes(5)))
                .reuseRefreshTokens(oAuth2TokenSettingsDO.isReuseRefreshTokens())
                .refreshTokenTimeToLive(Optional.ofNullable(oAuth2TokenSettingsDO.getRefreshTokenTimeToLive())
                                .map(Duration::ofSeconds)
                                .orElse(Duration.ofMinutes(60)))
                .idTokenSignatureAlgorithm(SignatureAlgorithm.from(oAuth2TokenSettingsDO.getIdTokenSignatureAlgorithm()));

        if (StringUtils.hasText(oAuth2TokenSettingsDO.getAccessTokenFormat())) {
            builder.accessTokenFormat(new OAuth2TokenFormat(oAuth2TokenSettingsDO.getAccessTokenFormat()));
        }

        return builder.build();
    }

    public static OAuth2TokenSettingsDO toDO(TokenSettings tokenSettings) {
        OAuth2TokenSettingsDO.OAuth2TokenSettingsDOBuilder builder = OAuth2TokenSettingsDO.builder()
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
