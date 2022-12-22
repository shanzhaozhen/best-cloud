package org.shanzhaozhen.authorize.authentication.account;

import org.shanzhaozhen.authorize.constant.SocialType;
import org.shanzhaozhen.authorize.pojo.dto.AuthUser;
import org.shanzhaozhen.authorize.service.SocialUserService;
import org.shanzhaozhen.common.core.result.R;
import org.shanzhaozhen.common.core.utils.HttpServletUtils;
import org.springframework.lang.Nullable;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.util.Assert;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AccountBindAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    public static final String SPRING_SECURITY_FORM_USERNAME_KEY = "username";

    public static final String SPRING_SECURITY_FORM_PASSWORD_KEY = "password";

    public static final String DEFAULT_FILTER_PROCESSES_URI = "/bind/account";

    private static final AntPathRequestMatcher DEFAULT_ANT_PATH_REQUEST_MATCHER = new AntPathRequestMatcher(DEFAULT_FILTER_PROCESSES_URI,
            "POST");

    private String usernameParameter = SPRING_SECURITY_FORM_USERNAME_KEY;

    private String passwordParameter = SPRING_SECURITY_FORM_PASSWORD_KEY;

    private boolean postOnly = true;

    private final SocialUserService socialUserService;

    public AccountBindAuthenticationFilter(SocialUserService socialUserService) {
        super(DEFAULT_ANT_PATH_REQUEST_MATCHER);
        this.socialUserService = socialUserService;
    }

    public AccountBindAuthenticationFilter(AuthenticationManager authenticationManager, SocialUserService socialUserService) {
        super(DEFAULT_ANT_PATH_REQUEST_MATCHER, authenticationManager);
        this.socialUserService = socialUserService;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {
        if (this.postOnly && !request.getMethod().equals("POST")) {
            throw new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());
        }
        String username = obtainUsername(request);
        username = (username != null) ? username : "";
        username = username.trim();
        String password = obtainPassword(request);
        password = (password != null) ? password : "";
        UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(username, password);
        // Allow subclasses to set the "details" property
        setDetails(request, authRequest);

        // 验证通过
        Authentication authenticate = this.getAuthenticationManager().authenticate(authRequest);
        AuthUser authUser = (AuthUser) authenticate.getPrincipal();

        // 进行绑定
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication instanceof OAuth2AuthenticationToken && authentication.getPrincipal() instanceof DefaultOAuth2User) {
            DefaultOAuth2User principal = (DefaultOAuth2User) authentication.getPrincipal();
            String registrationId = ((OAuth2AuthenticationToken) authentication).getAuthorizedClientRegistrationId();
            SocialType socialType = null;
            if (SocialType.GITHUB.getRegistrationId().equals(registrationId)) {
                socialType = SocialType.GITHUB;
            }
            Assert.notNull(socialType, "不支持该第三方账号类型绑定账号");

            try {
                socialUserService.bindSocialUser(authUser.getUserId(), principal.getName(), socialType.getName());
            } catch (Exception e) {
                e.printStackTrace();
                throw new IllegalArgumentException("未知错误，绑定失败！");
            }
        } else {
            throw new IllegalArgumentException("不支持该登陆状态进行绑定");
        }
        return authenticate;
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
//        super.successfulAuthentication(request, response, chain, authResult);
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(authResult);
        SecurityContextHolder.setContext(context);
        HttpServletUtils.resultJson(response, HttpServletResponse.SC_OK, R.ok(null, "绑定成功！"));
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException {
        failed.printStackTrace();
        String msg;
        if (failed instanceof BadCredentialsException) {
            msg = "账号或密码错误！";
        } else {
            msg = failed.getMessage();
        }
        HttpServletUtils.resultJson(response, HttpServletResponse.SC_BAD_REQUEST, R.failed(msg));
    }

    @Nullable
    protected String obtainPassword(HttpServletRequest request) {
        return request.getParameter(this.passwordParameter);
    }

    @Nullable
    protected String obtainUsername(HttpServletRequest request) {
        return request.getParameter(this.usernameParameter);
    }

    /**
     * Provided so that subclasses may configure what is put into the authentication
     * request's details property.
     * @param request that an authentication request is being created for
     * @param authRequest the authentication request object that should have its details
     * set
     */
    protected void setDetails(HttpServletRequest request, UsernamePasswordAuthenticationToken authRequest) {
        authRequest.setDetails(this.authenticationDetailsSource.buildDetails(request));
    }

    public void setUsernameParameter(String usernameParameter) {
        Assert.hasText(usernameParameter, "Username parameter must not be empty or null");
        this.usernameParameter = usernameParameter;
    }

    public void setPasswordParameter(String passwordParameter) {
        Assert.hasText(passwordParameter, "Password parameter must not be empty or null");
        this.passwordParameter = passwordParameter;
    }

    public void setPostOnly(boolean postOnly) {
        this.postOnly = postOnly;
    }

    public final String getUsernameParameter() {
        return this.usernameParameter;
    }

    public final String getPasswordParameter() {
        return this.passwordParameter;
    }

}
