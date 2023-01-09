package org.shanzhaozhen.authorize.converter;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.shanzhaozhen.authorize.jackson.SecurityJacksonConfig;
import org.shanzhaozhen.authorize.pojo.dto.OAuth2AuthorizationDTO;
import org.springframework.security.oauth2.core.*;
import org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.server.authorization.OAuth2Authorization;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationCode;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.time.Instant;
import java.util.*;

public class OAuth2AuthorizationConverter {

    // 还是逃不开这个反序列化的问题
    private static final ObjectMapper objectMapper = SecurityJacksonConfig.objectMapper;

    public static OAuth2Authorization toOAuth2Authorization(OAuth2AuthorizationDTO oauth2AuthorizationDTO, RegisteredClient registeredClient) {
        OAuth2Authorization.Builder builder = OAuth2Authorization.withRegisteredClient(registeredClient);
        String id = oauth2AuthorizationDTO.getId();
        String principalName = oauth2AuthorizationDTO.getPrincipalName();
        String authorizationGrantType = oauth2AuthorizationDTO.getAuthorizationGrantType();

        Map<String, Object> attributes = parseMap(oauth2AuthorizationDTO.getAttributes());

        builder.id(id)
                .principalName(principalName)
                .authorizationGrantType(new AuthorizationGrantType(authorizationGrantType))
                .attributes((attrs) -> attrs.putAll(attributes));

        if (oauth2AuthorizationDTO.getAuthorizedScopes() != null) {
            builder.authorizedScopes(StringUtils.commaDelimitedListToSet(oauth2AuthorizationDTO.getAuthorizedScopes()));
        }

        String state = oauth2AuthorizationDTO.getState();
        if (StringUtils.hasText(state)) {
            builder.attribute(OAuth2ParameterNames.STATE, state);
        }

        Instant tokenIssuedAt;
        Instant tokenExpiresAt;
        String authorizationCodeValue = oauth2AuthorizationDTO.getAuthorizationCodeValue();

        if (StringUtils.hasText(authorizationCodeValue)) {
            tokenIssuedAt = oauth2AuthorizationDTO.getAuthorizationCodeIssuedAt();
            tokenExpiresAt = oauth2AuthorizationDTO.getAuthorizationCodeExpiresAt();
            Map<String, Object> authorizationCodeMetadata = parseMap(oauth2AuthorizationDTO.getAuthorizationCodeMetadata());
            OAuth2AuthorizationCode authorizationCode = new OAuth2AuthorizationCode(authorizationCodeValue, tokenIssuedAt, tokenExpiresAt);
            builder.token(authorizationCode, (metadata) -> metadata.putAll(authorizationCodeMetadata));
        }

        String accessTokenValue = oauth2AuthorizationDTO.getAccessTokenValue();
        if (StringUtils.hasText(accessTokenValue)) {
            tokenIssuedAt = oauth2AuthorizationDTO.getAccessTokenIssuedAt();
            tokenExpiresAt = oauth2AuthorizationDTO.getAccessTokenExpiresAt();
            Map<String, Object> accessTokenMetadata = parseMap(oauth2AuthorizationDTO.getAccessTokenMetadata());

            OAuth2AccessToken.TokenType tokenType = null;
            if (OAuth2AccessToken.TokenType.BEARER.getValue().equalsIgnoreCase(oauth2AuthorizationDTO.getAccessTokenType())) {
                tokenType = OAuth2AccessToken.TokenType.BEARER;
            }

            Set<String> scopes = Collections.emptySet();
            String accessTokenScopes = oauth2AuthorizationDTO.getAccessTokenScopes();
            if (accessTokenScopes != null) {
                scopes = StringUtils.commaDelimitedListToSet(accessTokenScopes);
            }
            OAuth2AccessToken accessToken = new OAuth2AccessToken(tokenType, accessTokenValue, tokenIssuedAt, tokenExpiresAt, scopes);
            builder.token(accessToken, (metadata) -> metadata.putAll(accessTokenMetadata));
        }

        String oidcIdTokenValue = oauth2AuthorizationDTO.getOidcIdTokenValue();
        if (StringUtils.hasText(oidcIdTokenValue)) {
            tokenIssuedAt = oauth2AuthorizationDTO.getOidcIdTokenIssuedAt();
            tokenExpiresAt = oauth2AuthorizationDTO.getOidcIdTokenExpiresAt();
            Map<String, Object> oidcTokenMetadata = parseMap(oauth2AuthorizationDTO.getOidcIdTokenMetadata());

            OidcIdToken oidcToken = new OidcIdToken(
                    oidcIdTokenValue, tokenIssuedAt, tokenExpiresAt, (Map<String, Object>) oidcTokenMetadata.get(OAuth2Authorization.Token.CLAIMS_METADATA_NAME));
            builder.token(oidcToken, (metadata) -> metadata.putAll(oidcTokenMetadata));
        }

        String refreshTokenValue = oauth2AuthorizationDTO.getRefreshTokenValue();
        if (StringUtils.hasText(refreshTokenValue)) {
            tokenIssuedAt = oauth2AuthorizationDTO.getRefreshTokenIssuedAt();
            tokenExpiresAt = null;

            Instant refreshTokenExpiresAt = oauth2AuthorizationDTO.getRefreshTokenExpiresAt();
            if (refreshTokenExpiresAt != null) {
                tokenExpiresAt = refreshTokenExpiresAt;
            }
            Map<String, Object> refreshTokenMetadata = parseMap(oauth2AuthorizationDTO.getRefreshTokenMetadata());
            OAuth2RefreshToken refreshToken = new OAuth2RefreshToken(
                    refreshTokenValue, tokenIssuedAt, tokenExpiresAt);
            builder.token(refreshToken, (metadata) -> metadata.putAll(refreshTokenMetadata));
        }
        return builder.build();
    }


    public static OAuth2AuthorizationDTO toDTO(OAuth2Authorization oauth2Authorization) {
        OAuth2AuthorizationDTO.OAuth2AuthorizationDTOBuilder builder = OAuth2AuthorizationDTO.builder()
                .id(oauth2Authorization.getId())
                .registeredClientId(oauth2Authorization.getRegisteredClientId())
                .principalName(oauth2Authorization.getPrincipalName())
                .authorizationGrantType(Optional.ofNullable(oauth2Authorization.getAuthorizationGrantType())
                        .map(AuthorizationGrantType::getValue)
                        .orElse(null))
                .attributes(writeMap(oauth2Authorization.getAttributes()))
                .state(oauth2Authorization.getAttribute(OAuth2ParameterNames.STATE));

        if (!CollectionUtils.isEmpty(oauth2Authorization.getAuthorizedScopes())) {
            builder.authorizedScopes(StringUtils.collectionToDelimitedString(oauth2Authorization.getAuthorizedScopes(), ","));
        }

        OAuth2Authorization.Token<OAuth2AuthorizationCode> authorizationCode = oauth2Authorization.getToken(OAuth2AuthorizationCode.class);

        if (authorizationCode != null) {
            OAuth2Token oauth2Token = authorizationCode.getToken();
            builder
                    .authorizationCodeValue(oauth2Token.getTokenValue())
                    .authorizationCodeIssuedAt(oauth2Token.getIssuedAt())
                    .authorizationCodeExpiresAt(oauth2Token.getExpiresAt())
                    .authorizationCodeMetadata(writeMap(authorizationCode.getMetadata()));
        }


        OAuth2Authorization.Token<OAuth2AccessToken> accessToken = oauth2Authorization.getToken(OAuth2AccessToken.class);

        if (accessToken != null) {
            OAuth2Token oauth2Token = accessToken.getToken();
            builder
                    .accessTokenValue(oauth2Token.getTokenValue())
                    .accessTokenIssuedAt(oauth2Token.getIssuedAt())
                    .accessTokenExpiresAt(oauth2Token.getExpiresAt())
                    .accessTokenMetadata(writeMap(accessToken.getMetadata()));

            builder.accessTokenType(accessToken.getToken().getTokenType().getValue());
            if (!CollectionUtils.isEmpty(accessToken.getToken().getScopes())) {
                builder.accessTokenScopes(StringUtils.collectionToDelimitedString(accessToken.getToken().getScopes(), ","));
            }
        }

        OAuth2Authorization.Token<OAuth2RefreshToken> refreshToken = oauth2Authorization.getToken(OAuth2RefreshToken.class);

        if (refreshToken != null) {
            OAuth2Token oauth2Token = refreshToken.getToken();
            builder
                    .refreshTokenValue(oauth2Token.getTokenValue())
                    .refreshTokenIssuedAt(oauth2Token.getIssuedAt())
                    .refreshTokenExpiresAt(oauth2Token.getExpiresAt())
                    .refreshTokenMetadata(writeMap(refreshToken.getMetadata()));
        }

        OAuth2Authorization.Token<OidcIdToken> oidcIdToken = oauth2Authorization.getToken(OidcIdToken.class);

        if (oidcIdToken != null) {
            OAuth2Token oauth2Token = oidcIdToken.getToken();
            builder
                    .oidcIdTokenValue(oauth2Token.getTokenValue())
                    .oidcIdTokenIssuedAt(oauth2Token.getIssuedAt())
                    .oidcIdTokenExpiresAt(oauth2Token.getExpiresAt())
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
