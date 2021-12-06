package org.shanzhaozhen.authorize.domain;

import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.OAuth2Token;
import org.springframework.security.oauth2.server.authorization.OAuth2Authorization;

import java.util.Map;

public class OAuth2AuthorizationDO {

    private String id;
    private String registeredClientId;
    private String principalName;
    private AuthorizationGrantType authorizationGrantType;
    private Map<Class<? extends OAuth2Token>, OAuth2Authorization.Token<?>> tokens;
    private Map<String, Object> attributes;

}
