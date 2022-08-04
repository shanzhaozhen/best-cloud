package org.shanzhaozhen.authorize.authentication.phone;

import org.springframework.lang.Nullable;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.util.Assert;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class PhoneAuthenticationFilter extends AbstractAuthenticationProcessingFilter {
    
    public static final String SPRING_SECURITY_FORM_PHONE_KEY = "phone";

    public static final String SPRING_SECURITY_FORM_CAPTCHA_KEY = "captcha";

    public static final String DEFAULT_FILTER_PROCESSES_URI = "/login/phone";

    private static final AntPathRequestMatcher DEFAULT_ANT_PATH_REQUEST_MATCHER = new AntPathRequestMatcher(DEFAULT_FILTER_PROCESSES_URI,
            "POST");

    private String phoneParameter = SPRING_SECURITY_FORM_PHONE_KEY;

    private String captchaParameter = SPRING_SECURITY_FORM_CAPTCHA_KEY;

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
        String phone = obtainPhone(request);
        phone = (phone != null) ? phone : "";
        phone = phone.trim();
        String captcha = obtainCaptcha(request);
        captcha = (captcha != null) ? captcha : "";
        PhoneAuthenticationToken authRequest = new PhoneAuthenticationToken(phone, captcha);
        // Allow subclasses to set the "details" property
        setDetails(request, authRequest);
        return this.getAuthenticationManager().authenticate(authRequest);
    }

    @Nullable
    protected String obtainCaptcha(HttpServletRequest request) {
        return request.getParameter(this.captchaParameter);
    }

    @Nullable
    protected String obtainPhone(HttpServletRequest request) {
        return request.getParameter(this.phoneParameter);
    }


    protected void setDetails(HttpServletRequest request, PhoneAuthenticationToken authRequest) {
        authRequest.setDetails(this.authenticationDetailsSource.buildDetails(request));
    }

    public void setPhoneParameter(String phoneParameter) {
        Assert.hasText(phoneParameter, "Phone parameter must not be empty or null");
        this.phoneParameter = phoneParameter;
    }

    public void setCaptchaParameter(String captchaParameter) {
        Assert.hasText(captchaParameter, "Captcha parameter must not be empty or null");
        this.captchaParameter = captchaParameter;
    }

    public final String getPhoneParameter() {
        return this.phoneParameter;
    }

    public final String getCaptchaParameter() {
        return this.captchaParameter;
    }


}
