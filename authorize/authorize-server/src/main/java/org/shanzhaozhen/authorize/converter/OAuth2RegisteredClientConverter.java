package org.shanzhaozhen.authorize.converter;

import org.shanzhaozhen.authorize.pojo.entity.OAuth2RegisteredClientDO;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.config.ClientSettings;
import org.springframework.security.oauth2.server.authorization.config.TokenSettings;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

public class OAuth2RegisteredClientConverter {

    public static RegisteredClient toRegisteredClient(OAuth2RegisteredClientDO oAuth2RegisteredClientDO, ClientSettings clientSettings, TokenSettings tokenSetting) {
        Set<String> clientAuthenticationMethods = StringUtils.commaDelimitedListToSet(oAuth2RegisteredClientDO.getClientAuthenticationMethods());
        Set<String> authorizationGrantTypes = StringUtils.commaDelimitedListToSet(oAuth2RegisteredClientDO.getAuthorizationGrantTypes());
        Set<String> redirectUris = StringUtils.commaDelimitedListToSet(oAuth2RegisteredClientDO.getRedirectUris());
        Set<String> clientScopes = StringUtils.commaDelimitedListToSet(oAuth2RegisteredClientDO.getScopes());

        RegisteredClient.Builder builder = RegisteredClient.withId(oAuth2RegisteredClientDO.getId())
                .clientId(oAuth2RegisteredClientDO.getClientId())
                .clientIdIssuedAt(oAuth2RegisteredClientDO.getClientIdIssuedAt())
                .clientSecret(oAuth2RegisteredClientDO.getClientSecret())
                .clientSecretExpiresAt(oAuth2RegisteredClientDO.getClientSecretExpiresAt())
                .clientName(oAuth2RegisteredClientDO.getClientName())
                .clientAuthenticationMethods(authenticationMethods ->
                        clientAuthenticationMethods.forEach(authenticationMethod ->
                                authenticationMethods.add(new ClientAuthenticationMethod(authenticationMethod))))
                .authorizationGrantTypes(grantTypes ->
                        authorizationGrantTypes.forEach(grantType ->
                                grantTypes.add(new AuthorizationGrantType(grantType))))
                .redirectUris(uris -> uris.addAll(redirectUris))
                .scopes(scopes -> scopes.addAll(clientScopes))
                .clientSettings(clientSettings)
                .tokenSettings(tokenSetting);

        return builder.build();
    }

    public static OAuth2RegisteredClientDO toDO(RegisteredClient registeredClient) {
        OAuth2RegisteredClientDO.OAuth2RegisteredClientDOBuilder builder = OAuth2RegisteredClientDO.builder()
                .id(registeredClient.getId())
                .clientId(registeredClient.getClientId())
                .clientIdIssuedAt(registeredClient.getClientIdIssuedAt())
                .clientSecret(registeredClient.getClientSecret())
                .clientSecretExpiresAt(registeredClient.getClientSecretExpiresAt())
                .clientName(registeredClient.getClientName())
                .clientAuthenticationMethods(StringUtils.collectionToCommaDelimitedString(
                        registeredClient.getClientAuthenticationMethods().stream().map(ClientAuthenticationMethod::getValue).collect(Collectors.toSet())
                ))
                .authorizationGrantTypes(StringUtils.collectionToCommaDelimitedString(
                        registeredClient.getAuthorizationGrantTypes().stream().map(AuthorizationGrantType::getValue).collect(Collectors.toSet())
                ))
                .redirectUris(StringUtils.collectionToCommaDelimitedString(registeredClient.getRedirectUris()))
                .scopes(StringUtils.collectionToCommaDelimitedString(registeredClient.getScopes()))
                ;
        return builder.build();
    }

}
