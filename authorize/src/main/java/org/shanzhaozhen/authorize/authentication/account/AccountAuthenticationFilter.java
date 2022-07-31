package org.shanzhaozhen.authorize.authentication.account;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.shanzhaozhen.uaa.pojo.form.AccountLogin;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AccountAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    public static final String DEFAULT_FILTER_PROCESSES_URI = "/login/account";

    private static final AntPathRequestMatcher DEFAULT_ANT_PATH_REQUEST_MATCHER = new AntPathRequestMatcher(DEFAULT_FILTER_PROCESSES_URI,
            "POST");

    public AccountAuthenticationFilter() {
        super(DEFAULT_ANT_PATH_REQUEST_MATCHER);
    }

    public AccountAuthenticationFilter(AuthenticationManager authenticationManager) {
        super(DEFAULT_ANT_PATH_REQUEST_MATCHER, authenticationManager);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {
        if (!request.getMethod().equals("POST")) {
            throw new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());
        }

        AccountLogin accountLogin;
        try {
            accountLogin = new ObjectMapper().readValue(request.getInputStream(), AccountLogin.class);
        } catch (IOException e) {
            throw new AuthenticationServiceException("登陆失败，用户名或密码格式不正确");
        }

        String username = accountLogin.getUsername();
        username = (username != null) ? username : "";
        username = username.trim();
        String password = accountLogin.getPassword();
        password = (password != null) ? password : "";
        UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(username, password);
        // Allow subclasses to set the "details" property
        setDetails(request, authRequest);
        return this.getAuthenticationManager().authenticate(authRequest);
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

}
