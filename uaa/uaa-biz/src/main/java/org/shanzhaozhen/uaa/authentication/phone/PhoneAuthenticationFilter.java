package org.shanzhaozhen.uaa.authentication.phone;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.shanzhaozhen.uaa.pojo.form.AccountLogin;
import org.shanzhaozhen.uaa.pojo.form.PhoneLogin;
import org.springframework.lang.Nullable;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.util.Assert;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class PhoneAuthenticationFilter extends AbstractAuthenticationProcessingFilter {
    public static final String SPRING_SECURITY_FORM_PHONE_KEY = "phone";

    public static final String SPRING_SECURITY_FORM_CAPTCHA_KEY = "captcha";

    public static final String DEFAULT_FILTER_PROCESSES_URI = "/login/phone";

    private static final AntPathRequestMatcher DEFAULT_ANT_PATH_REQUEST_MATCHER = new AntPathRequestMatcher(DEFAULT_FILTER_PROCESSES_URI,
            "POST");

    public PhoneAuthenticationFilter() {
        super(DEFAULT_ANT_PATH_REQUEST_MATCHER);
    }

    public PhoneAuthenticationFilter(AuthenticationManager authenticationManager) {
        super(DEFAULT_ANT_PATH_REQUEST_MATCHER, authenticationManager);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {
        if (!request.getMethod().equals("POST")) {
            throw new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());
        }

        PhoneLogin phoneLogin;
        try {
            phoneLogin = new ObjectMapper().readValue(request.getInputStream(), PhoneLogin.class);
        } catch (IOException e) {
            throw new AuthenticationServiceException("登陆失败，手机号或验证码格式不正确");
        }

        String phone = phoneLogin.getPhone();
        phone = (phone != null) ? phone : "";
        phone = phone.trim();
        String captcha = phoneLogin.getCaptcha();
        captcha = (captcha != null) ? captcha : "";
        PhoneAuthenticationToken authRequest = new PhoneAuthenticationToken(phone, captcha);
        // Allow subclasses to set the "details" property
        setDetails(request, authRequest);
        return this.getAuthenticationManager().authenticate(authRequest);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        super.successfulAuthentication(request, response, chain, authResult);
    }

    protected void setDetails(HttpServletRequest request, PhoneAuthenticationToken authRequest) {
        authRequest.setDetails(this.authenticationDetailsSource.buildDetails(request));
    }

}
