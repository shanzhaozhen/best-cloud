package org.shanzhaozhen.authorize.authentication.federated;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.util.Assert;

import java.util.Collection;

public class FederatedIdentityAuthenticationToken extends AbstractAuthenticationToken {

    private static final long serialVersionUID = 8268700794524314159L;

    /**
     * principal的作用有两个, 在未登录之前是手机号，那么在登录之后是用户的信息。
     */
    private final Object principal;

    private final String authorizedClientRegistrationId;


    /**
     * 此构造函数用来初始化未授信凭据
     *
     * @param principal
     * @param authorizedClientRegistrationId
     */
    public FederatedIdentityAuthenticationToken(Object principal, String authorizedClientRegistrationId) {
        super(null);
        this.principal = principal;
        this.authorizedClientRegistrationId = authorizedClientRegistrationId;
        setAuthenticated(false);
    }

    /**
     * 此构造函数用来初始化授信凭据
     *
     * @param principal
     * @param authorities
     * @param authorizedClientRegistrationId
     */
    public FederatedIdentityAuthenticationToken(Object principal, Collection<? extends GrantedAuthority> authorities, String authorizedClientRegistrationId) {
        super(authorities);
        this.principal = principal;
        this.authorizedClientRegistrationId = authorizedClientRegistrationId;
        super.setAuthenticated(true); // must use super, as we override
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return this.principal;
    }

    @Override
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
        Assert.isTrue(!isAuthenticated,
                "Cannot set this token to trusted - use constructor which takes a GrantedAuthority list instead");
        super.setAuthenticated(false);
    }

    @Override
    public void eraseCredentials() {
        super.eraseCredentials();
    }

    public String getAuthorizedClientRegistrationId() {
        return this.authorizedClientRegistrationId;
    }

}
