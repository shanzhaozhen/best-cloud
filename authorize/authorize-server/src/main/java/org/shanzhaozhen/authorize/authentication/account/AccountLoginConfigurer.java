package org.shanzhaozhen.authorize.authentication.account;

import org.shanzhaozhen.authorize.authentication.AbstractLoginFilterConfigurer;
import org.shanzhaozhen.authorize.service.OAuthUserSocialService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.HttpSecurityBuilder;
import org.springframework.security.web.authentication.ForwardAuthenticationFailureHandler;
import org.springframework.security.web.authentication.ForwardAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.ui.DefaultLoginPageGeneratingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

import static org.shanzhaozhen.authorize.authentication.account.AccountAuthenticationFilter.DEFAULT_FILTER_PROCESSES_URI;

public class AccountLoginConfigurer<H extends HttpSecurityBuilder<H>> extends
        AbstractLoginFilterConfigurer<H, AccountLoginConfigurer<H>, AccountAuthenticationFilter> {

    private final OAuthUserSocialService socialUserService;

    public AccountLoginConfigurer(OAuthUserSocialService socialUserService) {
        super(new AccountAuthenticationFilter(), DEFAULT_FILTER_PROCESSES_URI);
        this.socialUserService = socialUserService;
        usernameParameter("username");
        passwordParameter("password");
    }

//    public AccountLoginConfigurer(HttpSecurity http, UserDetailsService userDetailsService) {
//        this();
//        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
//        authenticationProvider.setUserDetailsService(userDetailsService);
//        http.authenticationProvider(authenticationProvider);
//    }

    @Override
    public AccountLoginConfigurer<H> loginPage(String loginPage) {
        return super.loginPage(loginPage);
    }

    public AccountLoginConfigurer<H> usernameParameter(String usernameParameter) {
        getAuthenticationFilter().setUsernameParameter(usernameParameter);
        return this;
    }

    public AccountLoginConfigurer<H> passwordParameter(String passwordParameter) {
        getAuthenticationFilter().setPasswordParameter(passwordParameter);
        return this;
    }

    public AccountLoginConfigurer<H> failureForwardUrl(String forwardUrl) {
        failureHandler(new ForwardAuthenticationFailureHandler(forwardUrl));
        return this;
    }

    public AccountLoginConfigurer<H> successForwardUrl(String forwardUrl) {
        successHandler(new ForwardAuthenticationSuccessHandler(forwardUrl));
        return this;
    }

    @Override
    public void configure(H http) throws Exception {
        super.configure(http);
        AccountBindAuthenticationFilter filter = new AccountBindAuthenticationFilter(socialUserService);
        filter.setAuthenticationManager(http.getSharedObject(AuthenticationManager.class));
        http.addFilterAfter(filter, UsernamePasswordAuthenticationFilter.class);
    }

    /**
     * 初始化账号登陆校验方式的配置
     * @param http
     * @throws Exception
     */
    @Override
    public void init(H http) throws Exception {
        super.init(http);
        initDefaultLoginFilter(http);
    }

    /**
     * 设置账号登陆的验证地址
     * @param loginProcessingUrl creates the {@link RequestMatcher} based upon the
     * loginProcessingUrl
     * @return
     */
    @Override
    protected RequestMatcher createLoginProcessingUrlMatcher(String loginProcessingUrl) {
        return new AntPathRequestMatcher(loginProcessingUrl, "POST");
    }

    private String getUsernameParameter() {
        return getAuthenticationFilter().getUsernameParameter();
    }

    private String getPasswordParameter() {
        return getAuthenticationFilter().getPasswordParameter();
    }


    /**
     * 设置默认配置
     * @param http
     */
    private void initDefaultLoginFilter(H http) {
        DefaultLoginPageGeneratingFilter loginPageGeneratingFilter = http
                .getSharedObject(DefaultLoginPageGeneratingFilter.class);
        if (loginPageGeneratingFilter != null && !isCustomLoginPage()) {
            loginPageGeneratingFilter.setFormLoginEnabled(true);
            loginPageGeneratingFilter.setUsernameParameter(getUsernameParameter());
            loginPageGeneratingFilter.setPasswordParameter(getPasswordParameter());
//            loginPageGeneratingFilter.setLoginPageUrl(getLoginPage());
//            loginPageGeneratingFilter.setFailureUrl(getFailureUrl());
            loginPageGeneratingFilter.setAuthenticationUrl(getLoginProcessingUrl());
        }
    }

}