package org.shanzhaozhen.authorize.converter;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.shanzhaozhen.authorize.jackson.SecurityJacksonConfig;
import org.shanzhaozhen.authorize.pojo.entity.OAuth2AuthorizationDO;
import org.springframework.security.oauth2.core.*;
import org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.server.authorization.OAuth2Authorization;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.util.StringUtils;

import java.time.Instant;
import java.util.*;

public class OAuth2AuthorizationConverter {

    // 还是逃不开这个反序列化的问题
    private static final ObjectMapper objectMapper = SecurityJacksonConfig.objectMapper;

    public static OAuth2Authorization toOAuth2Authorization(OAuth2AuthorizationDO oAuth2AuthorizationDO, RegisteredClient registeredClient) {
        OAuth2Authorization.Builder builder = OAuth2Authorization.withRegisteredClient(registeredClient);
        String id = oAuth2AuthorizationDO.getId();
        String principalName = oAuth2AuthorizationDO.getPrincipalName();
        String authorizationGrantType = oAuth2AuthorizationDO.getAuthorizationGrantType();

        Map<String, Object> attributes = parseMap(oAuth2AuthorizationDO.getAttributes());

        builder.id(id)
                .principalName(principalName)
                .authorizationGrantType(new AuthorizationGrantType(authorizationGrantType))
                .attributes((attrs) -> attrs.putAll(attributes));

        String state = oAuth2AuthorizationDO.getState();
        if (StringUtils.hasText(state)) {
            builder.attribute(OAuth2ParameterNames.STATE, state);
        }

        Instant tokenIssuedAt;
        Instant tokenExpiresAt;
        String authorizationCodeValue = oAuth2AuthorizationDO.getAuthorizationCodeValue();

        if (StringUtils.hasText(authorizationCodeValue)) {
            tokenIssuedAt = oAuth2AuthorizationDO.getAuthorizationCodeIssuedAt();
            tokenExpiresAt = oAuth2AuthorizationDO.getAuthorizationCodeExpiresAt();
            Map<String, Object> authorizationCodeMetadata = parseMap(oAuth2AuthorizationDO.getAuthorizationCodeMetadata());
            OAuth2AuthorizationCode authorizationCode = new OAuth2AuthorizationCode(authorizationCodeValue, tokenIssuedAt, tokenExpiresAt);
            builder.token(authorizationCode, (metadata) -> metadata.putAll(authorizationCodeMetadata));
        }

        String accessTokenValue = oAuth2AuthorizationDO.getAccessTokenValue();
        if (StringUtils.hasText(accessTokenValue)) {
            tokenIssuedAt = oAuth2AuthorizationDO.getAccessTokenIssuedAt();
            tokenExpiresAt = oAuth2AuthorizationDO.getAccessTokenExpiresAt();
            Map<String, Object> accessTokenMetadata = parseMap(oAuth2AuthorizationDO.getAccessTokenMetadata());

            OAuth2AccessToken.TokenType tokenType = null;
            if (OAuth2AccessToken.TokenType.BEARER.getValue().equalsIgnoreCase(oAuth2AuthorizationDO.getAccessTokenType())) {
                tokenType = OAuth2AccessToken.TokenType.BEARER;
            }

            Set<String> scopes = Collections.emptySet();
            String accessTokenScopes = oAuth2AuthorizationDO.getAccessTokenScopes();
            if (accessTokenScopes != null) {
                scopes = StringUtils.commaDelimitedListToSet(accessTokenScopes);
            }
            OAuth2AccessToken accessToken = new OAuth2AccessToken(tokenType, accessTokenValue, tokenIssuedAt, tokenExpiresAt, scopes);
            builder.token(accessToken, (metadata) -> metadata.putAll(accessTokenMetadata));
        }

        String oidcIdTokenValue = oAuth2AuthorizationDO.getOidcIdTokenValue();
        if (StringUtils.hasText(oidcIdTokenValue)) {
            tokenIssuedAt = oAuth2AuthorizationDO.getOidcIdTokenIssuedAt();
            tokenExpiresAt = oAuth2AuthorizationDO.getOidcIdTokenExpiresAt();
            Map<String, Object> oidcTokenMetadata = parseMap(oAuth2AuthorizationDO.getOidcIdTokenMetadata());

            OidcIdToken oidcToken = new OidcIdToken(
                    oidcIdTokenValue, tokenIssuedAt, tokenExpiresAt, (Map<String, Object>) oidcTokenMetadata.get(OAuth2Authorization.Token.CLAIMS_METADATA_NAME));
            builder.token(oidcToken, (metadata) -> metadata.putAll(oidcTokenMetadata));
        }

        String refreshTokenValue = oAuth2AuthorizationDO.getRefreshTokenValue();
        if (StringUtils.hasText(refreshTokenValue)) {
            tokenIssuedAt = oAuth2AuthorizationDO.getRefreshTokenIssuedAt();
            tokenExpiresAt = null;

            Instant refreshTokenExpiresAt = oAuth2AuthorizationDO.getRefreshTokenExpiresAt();
            if (refreshTokenExpiresAt != null) {
                tokenExpiresAt = refreshTokenExpiresAt;
            }
            Map<String, Object> refreshTokenMetadata = parseMap(oAuth2AuthorizationDO.getRefreshTokenMetadata());
            OAuth2RefreshToken refreshToken = new OAuth2RefreshToken(
                    refreshTokenValue, tokenIssuedAt, tokenExpiresAt);
            builder.token(refreshToken, (metadata) -> metadata.putAll(refreshTokenMetadata));
        }
        return builder.build();
    }


    public static OAuth2AuthorizationDO toDO(OAuth2Authorization oAuth2Authorization) {
        OAuth2AuthorizationDO.OAuth2AuthorizationDOBuilder builder = OAuth2AuthorizationDO.builder()
                .id(oAuth2Authorization.getId())
                .registeredClientId(oAuth2Authorization.getRegisteredClientId())
                .principalName(oAuth2Authorization.getPrincipalName())
                .authorizationGrantType(Optional.ofNullable(oAuth2Authorization.getAuthorizationGrantType())
                        .map(AuthorizationGrantType::getValue)
                        .orElse(null))
                .attributes(writeMap(oAuth2Authorization.getAttributes()))
                .state(oAuth2Authorization.getAttribute(OAuth2ParameterNames.STATE));

        OAuth2Authorization.Token<OAuth2AuthorizationCode> authorizationCode = oAuth2Authorization.getToken(OAuth2AuthorizationCode.class);

        if (authorizationCode != null) {
            OAuth2Token oAuth2Token = authorizationCode.getToken();
            builder
                    .authorizationCodeValue(oAuth2Token.getTokenValue())
                    .authorizationCodeIssuedAt(oAuth2Token.getIssuedAt())
                    .authorizationCodeExpiresAt(oAuth2Token.getExpiresAt())
                    .authorizationCodeMetadata(writeMap(authorizationCode.getMetadata()));
        }


        OAuth2Authorization.Token<OAuth2AccessToken> accessToken = oAuth2Authorization.getToken(OAuth2AccessToken.class);

        if (accessToken != null) {
            OAuth2Token oAuth2Token = accessToken.getToken();
            builder
                    .accessTokenValue(oAuth2Token.getTokenValue())
                    .accessTokenIssuedAt(oAuth2Token.getIssuedAt())
                    .accessTokenExpiresAt(oAuth2Token.getExpiresAt())
                    .accessTokenMetadata(writeMap(accessToken.getMetadata()));

            if (accessToken.getToken().getScopes() != null) {
                builder.accessTokenScopes(StringUtils.collectionToDelimitedString(accessToken.getToken().getScopes(), ","));
            }
        }

        OAuth2Authorization.Token<OAuth2RefreshToken> refreshToken = oAuth2Authorization.getToken(OAuth2RefreshToken.class);

        if (refreshToken != null) {
            OAuth2Token oAuth2Token = refreshToken.getToken();
            builder
                    .refreshTokenValue(oAuth2Token.getTokenValue())
                    .refreshTokenIssuedAt(oAuth2Token.getIssuedAt())
                    .refreshTokenExpiresAt(oAuth2Token.getExpiresAt())
                    .refreshTokenMetadata(writeMap(refreshToken.getMetadata()));
        }

        OAuth2Authorization.Token<OidcIdToken> oidcIdToken = oAuth2Authorization.getToken(OidcIdToken.class);

        if (oidcIdToken != null) {
            OAuth2Token oAuth2Token = oidcIdToken.getToken();
            builder
                    .oidcIdTokenValue(oAuth2Token.getTokenValue())
                    .oidcIdTokenIssuedAt(oAuth2Token.getIssuedAt())
                    .oidcIdTokenExpiresAt(oAuth2Token.getExpiresAt())
                    .oidcIdTokenMetadata(writeMap(oidcIdToken.getMetadata()))
            ;
        }
        return builder.build();
    }

    public static Map<String, Object> parseMap(String data) {
        try {
            return objectMapper.readValue(data, new TypeReference<Map<String, Object>>() {});
        } catch (Exception ex) {
            throw new IllegalArgumentException(ex.getMessage(), ex);
        }
    }

    public static String writeMap(Map<String, Object> data) {
        try {
            return objectMapper.writeValueAsString(data);
        } catch (Exception ex) {
            throw new IllegalArgumentException(ex.getMessage(), ex);
        }
    }

}
