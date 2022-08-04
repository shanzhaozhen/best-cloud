package org.shanzhaozhen.authorize.converter;

import org.shanzhaozhen.authorize.pojo.dto.Oauth2RegisteredClientDTO;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;

public class OAuth2RegisteredClientConverter {

    public static RegisteredClient toRegisteredClient(Oauth2RegisteredClientDTO oauth2RegisteredClientDTO) {

        RegisteredClient.Builder builder = RegisteredClient.withId(oauth2RegisteredClientDTO.getId())
                .clientId(oauth2RegisteredClientDTO.getClientId())
                .clientIdIssuedAt(oauth2RegisteredClientDTO.getClientIdIssuedAt())
                .clientSecret(oauth2RegisteredClientDTO.getClientSecret())
                .clientSecretExpiresAt(oauth2RegisteredClientDTO.getClientSecretExpiresAt())
                .clientName(oauth2RegisteredClientDTO.getClientName())
                .clientAuthenticationMethods((authenticationMethods) ->
                        oauth2RegisteredClientDTO.getClientAuthenticationMethods().forEach(authenticationMethod ->
                                authenticationMethods.add(new ClientAuthenticationMethod(authenticationMethod.getClientAuthenticationMethod())))
                )
                .authorizationGrantTypes((grantTypes) ->
                        oauth2RegisteredClientDTO.getAuthorizationGrantTypes().forEach(grantType ->
                                grantTypes.add(new AuthorizationGrantType(grantType.getGrantTypeName())))
                )
                .redirectUris((uris) ->
                        oauth2RegisteredClientDTO.getRedirectUris().forEach(redirectUri ->
                                uris.add(redirectUri.getRedirectUri()))
                )
                .scopes((scopes) ->
                        oauth2RegisteredClientDTO.getScopes().forEach(scope ->
                                scopes.add(scope.getScope()))
                )
                .clientSettings(OAuth2ClientSettingsConverter.toClientSettings(oauth2RegisteredClientDTO.getClientSettings()))
                .tokenSettings(OAuth2TokenSettingsConverter.toTokenSettings(oauth2RegisteredClientDTO.getTokenSettings()));

        return builder.build();
    }

    public static Oauth2RegisteredClientDTO toDTO(RegisteredClient registeredClient) {
        return null;
    }
}
