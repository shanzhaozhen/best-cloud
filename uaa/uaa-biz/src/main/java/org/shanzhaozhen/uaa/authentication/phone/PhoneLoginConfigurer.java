package org.shanzhaozhen.uaa.authentication.phone;

import org.springframework.security.config.annotation.web.HttpSecurityBuilder;
import org.springframework.security.config.annotation.web.configurers.AbstractAuthenticationFilterConfigurer;
import org.springframework.security.web.authentication.ForwardAuthenticationFailureHandler;
import org.springframework.security.web.authentication.ForwardAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.ui.DefaultLoginPageGeneratingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

public class PhoneLoginConfigurer<H extends HttpSecurityBuilder<H>> extends
        AbstractAuthenticationFilterConfigurer<H, PhoneLoginConfigurer<H>, PhoneAuthenticationFilter> {

    public PhoneLoginConfigurer() {
        super(new PhoneAuthenticationFilter(), null);
        phoneParameter("phone");
        captchaParameter("captcha");
    }

    @Override
    public PhoneLoginConfigurer<H> loginPage(String loginPage) {
        return super.loginPage(loginPage);
    }

    public PhoneLoginConfigurer<H> phoneParameter(String phoneParameter) {
        getAuthenticationFilter().setPhoneParameter(phoneParameter);
        return this;
    }

    public PhoneLoginConfigurer<H> captchaParameter(String captchaParameter) {
        getAuthenticationFilter().setCaptchaParameter(captchaParameter);
        return this;
    }

    public PhoneLoginConfigurer<H> failureForwardUrl(String forwardUrl) {
        failureHandler(new ForwardAuthenticationFailureHandler(forwardUrl));
        return this;
    }

    public PhoneLoginConfigurer<H> successForwardUrl(String forwardUrl) {
        successHandler(new ForwardAuthenticationSuccessHandler(forwardUrl));
        return this;
    }

    @Override
    public void init(H http) throws Exception {
        super.init(http);
        initDefaultLoginFilter(http);
    }

    @Override
    protected RequestMatcher createLoginProcessingUrlMatcher(String loginProcessingUrl) {
        return new AntPathRequestMatcher(loginProcessingUrl, "POST");
    }

    private String getPhoneParameter() {
        return getAuthenticationFilter().getPhoneParameter();
    }

    private String getCaptchaParameter() {
        return getAuthenticationFilter().getCaptchaParameter();
    }

    private void initDefaultLoginFilter(H http) {
        DefaultLoginPageGeneratingFilter loginPageGeneratingFilter = http
                .getSharedObject(DefaultLoginPageGeneratingFilter.class);
        if (loginPageGeneratingFilter != null && !isCustomLoginPage()) {
            loginPageGeneratingFilter.setFormLoginEnabled(true);
            loginPageGeneratingFilter.setUsernameParameter(getPhoneParameter());
            loginPageGeneratingFilter.setPasswordParameter(getCaptchaParameter());
            loginPageGeneratingFilter.setLoginPageUrl(getLoginPage());
            loginPageGeneratingFilter.setFailureUrl(getFailureUrl());
            loginPageGeneratingFilter.setAuthenticationUrl(getLoginProcessingUrl());
        }
    }

}