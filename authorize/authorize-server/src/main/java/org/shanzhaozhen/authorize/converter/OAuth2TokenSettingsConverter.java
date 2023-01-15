package org.shanzhaozhen.authorize.converter;

import org.shanzhaozhen.oauth.pojo.dto.OAuth2TokenSettingsDTO;
import org.springframework.security.oauth2.jose.jws.SignatureAlgorithm;
import org.springframework.security.oauth2.server.authorization.settings.OAuth2TokenFormat;
import org.springframework.security.oauth2.server.authorization.settings.TokenSettings;
import org.springframework.util.StringUtils;

import java.time.Duration;
import java.util.Optional;

public class OAuth2TokenSettingsConverter {

    public static TokenSettings toTokenSettings(OAuth2TokenSettingsDTO oauth2TokenSettingsDTO) {
        TokenSettings.Builder builder = TokenSettings.builder()
                .accessTokenTimeToLive(Optional.ofNullable(oauth2TokenSettingsDTO.getAccessTokenTimeToLive())
                        .map(Duration::ofSeconds)
                        .orElse(Duration.ofMinutes(5)))
                .reuseRefreshTokens(oauth2TokenSettingsDTO.isReuseRefreshTokens())
                .refreshTokenTimeToLive(Optional.ofNullable(oauth2TokenSettingsDTO.getRefreshTokenTimeToLive())
                                .map(Duration::ofSeconds)
                                .orElse(Duration.ofMinutes(60)))
                .idTokenSignatureAlgorithm(SignatureAlgorithm.from(oauth2TokenSettingsDTO.getIdTokenSignatureAlgorithm()));

        if (StringUtils.hasText(oauth2TokenSettingsDTO.getAccessTokenFormat())) {
            builder.accessTokenFormat(new OAuth2TokenFormat(oauth2TokenSettingsDTO.getAccessTokenFormat()));
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
