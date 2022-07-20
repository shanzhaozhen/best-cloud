package org.shanzhaozhen.uaa.authentication.phone;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.util.Assert;

import java.util.Collection;

public class PhoneAuthenticationToken extends AbstractAuthenticationToken {

    private static final long serialVersionUID = -3961499051444442525L;

    /**
     * principal的作用有两个, 在未登录之前是手机号，那么在登录之后是用户的信息。
     */
    private final Object principal;

    private Object captcha;

    /**
     * 此构造函数用来初始化未授信凭据
     * @param principal
     * @param captcha
     */
    public PhoneAuthenticationToken(Object principal, Object captcha) {
        super(null);
        this.principal = principal;
        this.captcha = captcha;
        setAuthenticated(false);
    }

    /**
     * 此构造函数用来初始化授信凭据
     * @param principal
     * @param captcha
     * @param authorities
     */
    public PhoneAuthenticationToken(Object principal, Object captcha,
                                               Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.principal = principal;
        this.captcha = captcha;
        super.setAuthenticated(true); // must use super, as we override
    }

    @Override
    public Object getCredentials() {
        return this.captcha;
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
        this.captcha = null;
    }

}
