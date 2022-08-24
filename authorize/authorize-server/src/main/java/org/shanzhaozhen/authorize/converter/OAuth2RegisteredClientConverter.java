package org.shanzhaozhen.authorize.converter;

import org.shanzhaozhen.uaa.pojo.dto.OAuth2RegisteredClientDTO;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.util.StringUtils;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.*;
import java.util.stream.Collectors;

public class OAuth2RegisteredClientConverter {

    public static RegisteredClient toRegisteredClient(OAuth2RegisteredClientDTO oAuth2RegisteredClientDTO) {
        Set<String> clientAuthenticationMethods = StringUtils.commaDelimitedListToSet(oAuth2RegisteredClientDTO.getClientAuthenticationMethods());
        Set<String> authorizationGrantTypes = StringUtils.commaDelimitedListToSet(oAuth2RegisteredClientDTO.getAuthorizationGrantTypes());
        Set<String> redirectUris = StringUtils.commaDelimitedListToSet(oAuth2RegisteredClientDTO.getRedirectUris());
        Set<String> clientScopes = StringUtils.commaDelimitedListToSet(oAuth2RegisteredClientDTO.getScopes());

        RegisteredClient.Builder builder = RegisteredClient.withId(oAuth2RegisteredClientDTO.getId())
                .clientId(oAuth2RegisteredClientDTO.getClientId())
                .clientIdIssuedAt(Optional.ofNullable(oAuth2RegisteredClientDTO.getClientIdIssuedAt())
                        .map(o -> o.toInstant(ZoneOffset.UTC)).orElse(null))
                .clientSecret(oAuth2RegisteredClientDTO.getClientSecret())
                .clientSecretExpiresAt(Optional.ofNullable(oAuth2RegisteredClientDTO.getClientSecretExpiresAt())
                        .map(o -> o.toInstant(ZoneOffset.UTC)).orElse(null))
                .clientName(oAuth2RegisteredClientDTO.getClientName())
                .clientAuthenticationMethods(authenticationMethods ->
                        clientAuthenticationMethods.forEach(authenticationMethod ->
                                authenticationMethods.add(new ClientAuthenticationMethod(authenticationMethod))))
                .authorizationGrantTypes(grantTypes ->
                        authorizationGrantTypes.forEach(grantType ->
                                grantTypes.add(new AuthorizationGrantType(grantType))))
                .redirectUris(uris -> uris.addAll(redirectUris))
                .scopes(scopes -> scopes.addAll(clientScopes))
                .clientSettings(OAuth2ClientSettingsConverter.toClientSettings(oAuth2RegisteredClientDTO.getClientSettings()))
                .tokenSettings(OAuth2TokenSettingsConverter.toTokenSettings(oAuth2RegisteredClientDTO.getTokenSettings()));

        return builder.build();
    }

    public static OAuth2RegisteredClientDTO toDTO(RegisteredClient registeredClient) {
        OAuth2RegisteredClientDTO.OAuth2RegisteredClientDTOBuilder builder = OAuth2RegisteredClientDTO.builder()
                .id(registeredClient.getId())
                .clientId(registeredClient.getClientId())
                .clientIdIssuedAt(Optional.ofNullable(registeredClient.getClientIdIssuedAt())
                        .map(o -> LocalDateTime.ofInstant(o, ZoneId.systemDefault())).orElse(null))
                .clientSecret(registeredClient.getClientSecret())
                .clientSecretExpiresAt(Optional.ofNullable(registeredClient.getClientSecretExpiresAt())
                        .map(o -> LocalDateTime.ofInstant(o, ZoneId.systemDefault())).orElse(null))
                .clientName(registeredClient.getClientName())
                .clientAuthenticationMethods(StringUtils.collectionToCommaDelimitedString(
                        registeredClient.getClientAuthenticationMethods().stream().map(ClientAuthenticationMethod::getValue).collect(Collectors.toSet())
                ))
                .authorizationGrantTypes(StringUtils.collectionToCommaDelimitedString(
                        registeredClient.getAuthorizationGrantTypes().stream().map(AuthorizationGrantType::getValue).collect(Collectors.toSet())
                ))
                .redirectUris(StringUtils.collectionToCommaDelimitedString(registeredClient.getRedirectUris()))
                .scopes(StringUtils.collectionToCommaDelimitedString(registeredClient.getScopes()))
                .clientSettings(OAuth2ClientSettingsConverter.toDTO(registeredClient.getClientSettings()))
                .tokenSettings(OAuth2TokenSettingsConverter.toDTO(registeredClient.getTokenSettings()))
                ;
        return builder.build();
    }

}
