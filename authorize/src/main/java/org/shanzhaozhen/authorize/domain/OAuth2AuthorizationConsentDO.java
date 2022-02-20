package org.shanzhaozhen.authorize.domain;

import org.springframework.security.core.GrantedAuthority;

import java.util.Set;

public class OAuth2AuthorizationConsentDO {

    private static final String AUTHORITIES_SCOPE_PREFIX = "SCOPE_";

    private String registeredClientId;
    private String principalName;
    private Set<GrantedAuthority> authorities;

}
