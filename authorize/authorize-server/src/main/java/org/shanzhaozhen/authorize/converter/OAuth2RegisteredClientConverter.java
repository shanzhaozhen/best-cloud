package org.shanzhaozhen.authorize.converter;

import org.shanzhaozhen.authorize.pojo.dto.*;
import org.shanzhaozhen.authorize.pojo.entity.OAuth2RegisteredClientDO;
import org.shanzhaozhen.uaa.pojo.entity.PermissionDO;
import org.springframework.beans.BeanUtils;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.util.CollectionUtils;

import java.lang.reflect.Array;
import java.util.*;
import java.util.stream.Collectors;

public class OAuth2RegisteredClientConverter {

    public static RegisteredClient toRegisteredClient(OAuth2RegisteredClientDTO oauth2RegisteredClientDTO) {

        RegisteredClient.Builder builder = RegisteredClient.withId(oauth2RegisteredClientDTO.getId())
                .clientId(oauth2RegisteredClientDTO.getClientId())
                .clientIdIssuedAt(oauth2RegisteredClientDTO.getClientIdIssuedAt())
                .clientSecret(oauth2RegisteredClientDTO.getClientSecret())
                .clientSecretExpiresAt(oauth2RegisteredClientDTO.getClientSecretExpiresAt())
                .clientName(oauth2RegisteredClientDTO.getClientName())
                .clientAuthenticationMethods((authenticationMethods) ->
                        Optional.ofNullable(oauth2RegisteredClientDTO.getClientAuthenticationMethods()).orElse(Collections.emptySet())
                                .forEach(authenticationMethod ->
                                        authenticationMethods.add(new ClientAuthenticationMethod(authenticationMethod.getClientAuthenticationMethod())))
                )
                .authorizationGrantTypes((grantTypes) ->
                        Optional.ofNullable(oauth2RegisteredClientDTO.getAuthorizationGrantTypes()).orElse(Collections.emptySet())
                                .forEach(grantType ->
                                        grantTypes.add(new AuthorizationGrantType(grantType.getGrantTypeName())))
                )
                .redirectUris((uris) ->
                        Optional.ofNullable(oauth2RegisteredClientDTO.getRedirectUris()).orElse(Collections.emptySet())
                                .forEach(redirectUri ->
                                        uris.add(redirectUri.getRedirectUri()))
                )
                .scopes((scopes) ->
                        Optional.ofNullable(oauth2RegisteredClientDTO.getScopes()).orElse(Collections.emptySet())
                                .forEach(scope ->
                                        scopes.add(scope.getScope()))
                )
                .clientSettings(OAuth2ClientSettingsConverter.toClientSettings(oauth2RegisteredClientDTO.getClientSettings()))
                .tokenSettings(OAuth2TokenSettingsConverter.toTokenSettings(oauth2RegisteredClientDTO.getTokenSettings()));

        return builder.build();
    }

    public static OAuth2RegisteredClientDTO toDTO(RegisteredClient registeredClient) {
        OAuth2RegisteredClientDTO.OAuth2RegisteredClientDTOBuilder builder = OAuth2RegisteredClientDTO.builder()
                .id(registeredClient.getId())
                .clientId(registeredClient.getClientId())
                .clientIdIssuedAt(registeredClient.getClientIdIssuedAt())
                .clientSecret(registeredClient.getClientSecret())
                .clientSecretExpiresAt(registeredClient.getClientSecretExpiresAt())
                .clientName(registeredClient.getClientName())
                .clientAuthenticationMethods(
                        Optional.ofNullable(registeredClient.getClientAuthenticationMethods())
                                .map(o -> o.stream().map(i -> OAuth2ClientAuthenticationMethodDTO.builder()
                                        .registeredClientId(registeredClient.getId())
                                        .clientAuthenticationMethod(i.getValue())
                                        .build()
                                ).collect(Collectors.toSet())).orElse(Collections.emptySet())
                )
                .authorizationGrantTypes(
                        Optional.ofNullable(registeredClient.getAuthorizationGrantTypes())
                                .map(o -> o.stream().map(i -> OAuth2AuthorizationGrantTypeDTO.builder()
                                        .registeredClientId(registeredClient.getId())
                                        .grantTypeName(i.getValue())
                                        .build()
                                ).collect(Collectors.toSet())).orElse(Collections.emptySet())
                )
                .redirectUris(
                        Optional.ofNullable(registeredClient.getRedirectUris())
                                .map(o -> o.stream().map(i -> OAuth2RedirectUriDTO.builder()
                                        .registeredClientId(registeredClient.getId())
                                        .redirectUri(i)
                                        .build()
                                ).collect(Collectors.toSet())).orElse(Collections.emptySet())
                )
                .scopes(
                        Optional.ofNullable(registeredClient.getScopes())
                                .map(o -> o.stream().map(i -> OAuth2ScopeDTO.builder()
                                        .registeredClientId(registeredClient.getId())
                                        .scope(i)
                                        .build()
                                ).collect(Collectors.toSet())).orElse(Collections.emptySet())
                )
                .clientSettings(OAuth2ClientSettingsConverter
                        .toDTO(registeredClient.getClientSettings())
                        .setRegisteredClientId(registeredClient.getId())
                )
                .tokenSettings(OAuth2TokenSettingsConverter
                        .toDTO(registeredClient.getTokenSettings())
                        .setRegisteredClientId(registeredClient.getId())
                );
        return builder.build();
    }

    public static OAuth2RegisteredClientDTO toDTO(OAuth2RegisteredClientDO oauth2RegisteredClientDO) {
        OAuth2RegisteredClientDTO oauth2RegisteredClientDTO = new OAuth2RegisteredClientDTO();
        BeanUtils.copyProperties(oauth2RegisteredClientDO, oauth2RegisteredClientDTO);
        return oauth2RegisteredClientDTO;
    }

    public static OAuth2RegisteredClientDO toDO(OAuth2RegisteredClientDTO oauth2RegisteredClientDTO) {
        OAuth2RegisteredClientDO oauth2RegisteredClientDO = new OAuth2RegisteredClientDO();
        BeanUtils.copyProperties(oauth2RegisteredClientDTO, oauth2RegisteredClientDO);
        return oauth2RegisteredClientDO;
    }

}
