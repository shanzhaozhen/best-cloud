package org.shanzhaozhen.authorize.authentication;

import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationDetailsSource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.HttpSecurityBuilder;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.ExceptionHandlingConfigurer;
import org.springframework.security.config.annotation.web.configurers.FormLoginConfigurer;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.PortMapper;
import org.springframework.security.web.authentication.*;
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.util.matcher.*;
import org.springframework.web.accept.ContentNegotiationStrategy;
import org.springframework.web.accept.HeaderContentNegotiationStrategy;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Collections;

public abstract class AbstractLoginFilterConfigurer<B extends HttpSecurityBuilder<B>, T extends AbstractLoginFilterConfigurer<B, T, F>, F extends AbstractAuthenticationProcessingFilter>
        extends AbstractHttpConfigurer<T, B> {

    private F authFilter;

    private AuthenticationDetailsSource<HttpServletRequest, ?> authenticationDetailsSource;

    private SavedRequestAwareAuthenticationSuccessHandler defaultSuccessHandler = new SavedRequestAwareAuthenticationSuccessHandler();

    private AuthenticationSuccessHandler successHandler = this.defaultSuccessHandler;

    private LoginUrlAuthenticationEntryPoint authenticationEntryPoint;

    private boolean customLoginPage;

    public static final String DEFAULT_LOGIN_PAGE = "/login";

    private String loginPage;

    private String loginProcessingUrl;

    private AuthenticationFailureHandler failureHandler;

    private boolean permitAll;

    private String failureUrl;


    protected AbstractLoginFilterConfigurer() {
        setLoginPage(DEFAULT_LOGIN_PAGE);
    }


    protected AbstractLoginFilterConfigurer(F authenticationFilter, String defaultLoginProcessingUrl) {
        this();
        this.authFilter = authenticationFilter;
        if (defaultLoginProcessingUrl != null) {
            loginProcessingUrl(defaultLoginProcessingUrl);
        }
    }

    public final T defaultSuccessUrl(String defaultSuccessUrl) {
        return defaultSuccessUrl(defaultSuccessUrl, false);
    }


    public final T defaultSuccessUrl(String defaultSuccessUrl, boolean alwaysUse) {
        SavedRequestAwareAuthenticationSuccessHandler handler = new SavedRequestAwareAuthenticationSuccessHandler();
        handler.setDefaultTargetUrl(defaultSuccessUrl);
        handler.setAlwaysUseDefaultTargetUrl(alwaysUse);
        this.defaultSuccessHandler = handler;
        return successHandler(handler);
    }

    /**
     * 指定验证权限的 URL
     * @param loginProcessingUrl 验证用户名和密码的 URL
     * @return the {@link FormLoginConfigurer} 用于其他自定义
     */
    public T loginProcessingUrl(String loginProcessingUrl) {
        this.loginProcessingUrl = loginProcessingUrl;
        this.authFilter.setRequiresAuthenticationRequestMatcher(createLoginProcessingUrlMatcher(loginProcessingUrl));
        return getSelf();
    }

    /**
     * 创建一个 {@link RequestMatcher} 给 loginProcessingUrl
     * @param loginProcessingUrl creates the {@link RequestMatcher} based upon the
     * loginProcessingUrl
     * @return the {@link RequestMatcher} to use based upon the loginProcessingUrl
     */
    protected abstract RequestMatcher createLoginProcessingUrlMatcher(String loginProcessingUrl);

    /**
     * 指定自定义 {@link AuthenticationDetailsSource}. The default is
     * {@link WebAuthenticationDetailsSource}.
     * @param authenticationDetailsSource the custom {@link AuthenticationDetailsSource}
     * @return the {@link FormLoginConfigurer} for additional customization
     */
    public final T authenticationDetailsSource(
            AuthenticationDetailsSource<HttpServletRequest, ?> authenticationDetailsSource) {
        this.authenticationDetailsSource = authenticationDetailsSource;
        return getSelf();
    }

    /**
     * 设置认证成功钩子
     * @param successHandler
     * @return
     */
    public final T successHandler(AuthenticationSuccessHandler successHandler) {
        this.successHandler = successHandler;
        return getSelf();
    }

    /**
     * Equivalent of invoking permitAll(true)
     * @return the {@link FormLoginConfigurer} for additional customization
     */
    public final T permitAll() {
        return permitAll(true);
    }

    /**
     * 允许任何用户对 url 的访问权限
     * @param permitAll
     * @return
     */
    public final T permitAll(boolean permitAll) {
        this.permitAll = permitAll;
        return getSelf();
    }

    /**
     * 设置认证失败跳转地址
     * @param authenticationFailureUrl
     * @return
     */
    public final T failureUrl(String authenticationFailureUrl) {
        T result = failureHandler(new SimpleUrlAuthenticationFailureHandler(authenticationFailureUrl));
        this.failureUrl = authenticationFailureUrl;
        return result;
    }

    /**
     * 设置认证失败钩子
     * @param authenticationFailureHandler
     * @return
     */
    public final T failureHandler(AuthenticationFailureHandler authenticationFailureHandler) {
        this.failureUrl = null;
        this.failureHandler = authenticationFailureHandler;
        return getSelf();
    }

    /**
     * 初始化
     * @param http
     * @throws Exception
     */
    @Override
    public void init(B http) throws Exception {
        updateAuthenticationDefaults();
        updateAccessDefaults(http);
        registerDefaultAuthenticationEntryPoint(http);
    }

    @SuppressWarnings("unchecked")
    protected final void registerDefaultAuthenticationEntryPoint(B http) {
        registerAuthenticationEntryPoint(http, this.authenticationEntryPoint);
    }

    @SuppressWarnings("unchecked")
    protected final void registerAuthenticationEntryPoint(B http, AuthenticationEntryPoint authenticationEntryPoint) {
        ExceptionHandlingConfigurer<B> exceptionHandling = http.getConfigurer(ExceptionHandlingConfigurer.class);
        if (exceptionHandling == null) {
            return;
        }
        exceptionHandling.defaultAuthenticationEntryPointFor(postProcess(authenticationEntryPoint),
                getAuthenticationEntryPointMatcher(http));
    }

    protected final RequestMatcher getAuthenticationEntryPointMatcher(B http) {
        ContentNegotiationStrategy contentNegotiationStrategy = http.getSharedObject(ContentNegotiationStrategy.class);
        if (contentNegotiationStrategy == null) {
            contentNegotiationStrategy = new HeaderContentNegotiationStrategy();
        }
        MediaTypeRequestMatcher mediaMatcher = new MediaTypeRequestMatcher(contentNegotiationStrategy,
                MediaType.APPLICATION_XHTML_XML, new MediaType("image", "*"), MediaType.TEXT_HTML,
                MediaType.TEXT_PLAIN);
        mediaMatcher.setIgnoredMediaTypes(Collections.singleton(MediaType.ALL));
        RequestMatcher notXRequestedWith = new NegatedRequestMatcher(
                new RequestHeaderRequestMatcher("X-Requested-With", "XMLHttpRequest"));
        return new AndRequestMatcher(Arrays.asList(notXRequestedWith, mediaMatcher));
    }

    @Override
    public void configure(B http) throws Exception {
        PortMapper portMapper = http.getSharedObject(PortMapper.class);
        if (portMapper != null) {
            this.authenticationEntryPoint.setPortMapper(portMapper);
        }
        RequestCache requestCache = http.getSharedObject(RequestCache.class);
        if (requestCache != null) {
            this.defaultSuccessHandler.setRequestCache(requestCache);
        }
        this.authFilter.setAuthenticationManager(http.getSharedObject(AuthenticationManager.class));
        this.authFilter.setAuthenticationSuccessHandler(this.successHandler);
        this.authFilter.setAuthenticationFailureHandler(this.failureHandler);
        if (this.authenticationDetailsSource != null) {
            this.authFilter.setAuthenticationDetailsSource(this.authenticationDetailsSource);
        }
        SessionAuthenticationStrategy sessionAuthenticationStrategy = http
                .getSharedObject(SessionAuthenticationStrategy.class);
        if (sessionAuthenticationStrategy != null) {
            this.authFilter.setSessionAuthenticationStrategy(sessionAuthenticationStrategy);
        }
        RememberMeServices rememberMeServices = http.getSharedObject(RememberMeServices.class);
        if (rememberMeServices != null) {
            this.authFilter.setRememberMeServices(rememberMeServices);
        }
        F filter = postProcess(this.authFilter);
        // 登陆跟 UsernamePasswordAuthenticationFilter 同级即可
        http.addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class);
    }

    protected T loginPage(String loginPage) {
        setLoginPage(loginPage);
        updateAuthenticationDefaults();
        this.customLoginPage = true;
        return getSelf();
    }

    public final boolean isCustomLoginPage() {
        return this.customLoginPage;
    }

    protected final F getAuthenticationFilter() {
        return this.authFilter;
    }

    protected final void setAuthenticationFilter(F authFilter) {
        this.authFilter = authFilter;
    }

    protected final String getLoginPage() {
        return this.loginPage;
    }

    protected final AuthenticationEntryPoint getAuthenticationEntryPoint() {
        return this.authenticationEntryPoint;
    }

    protected final String getLoginProcessingUrl() {
        return this.loginProcessingUrl;
    }

    protected final String getFailureUrl() {
        return this.failureUrl;
    }

    /**
     * Updates the default values for authentication.
     * @throws Exception
     */
    protected final void updateAuthenticationDefaults() {
        if (this.loginProcessingUrl == null) {
            loginProcessingUrl(this.loginPage);
        }
        if (this.failureHandler == null) {
            failureUrl(this.loginPage + "?error");
        }
//        LogoutConfigurer<B> logoutConfigurer = getBuilder().getConfigurer(LogoutConfigurer.class);
//        if (logoutConfigurer != null && !logoutConfigurer.isCustomLogoutSuccess()) {
//            logoutConfigurer.logoutSuccessUrl(this.loginPage + "?logout");
//        }
    }

    /**
     * Updates the default values for access.
     */
    protected final void updateAccessDefaults(B http) {
        if (this.permitAll) {
            PermitAllSupport.permitAll(http, this.loginPage, this.loginProcessingUrl, this.failureUrl);
        }
    }

    private void setLoginPage(String loginPage) {
        this.loginPage = loginPage;
        this.authenticationEntryPoint = new LoginUrlAuthenticationEntryPoint(loginPage);
    }

    @SuppressWarnings("unchecked")
    private T getSelf() {
        return (T) this;
    }

}