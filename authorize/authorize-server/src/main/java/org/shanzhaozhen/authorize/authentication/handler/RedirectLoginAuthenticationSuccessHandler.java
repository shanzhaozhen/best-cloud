package org.shanzhaozhen.authorize.authentication.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.shanzhaozhen.common.core.enums.JwtErrorConst;
import org.shanzhaozhen.common.core.result.R;
import org.shanzhaozhen.common.core.utils.HttpServletUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.util.Assert;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Collections;

public class RedirectLoginAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    protected final Log logger = LogFactory.getLog(this.getClass());
    private final ObjectMapper objectMapper = new ObjectMapper()
            .registerModule(new JavaTimeModule());
    private RequestCache requestCache;
    private static final String defaultTargetUrl = "/";
    private final String redirect;

    public RedirectLoginAuthenticationSuccessHandler() {
        this(defaultTargetUrl, new HttpSessionRequestCache());
    }

    public RedirectLoginAuthenticationSuccessHandler(String redirect, RequestCache requestCache) {
        Assert.notNull(requestCache,"requestCache must not be null");
        this.redirect = redirect;
        this.requestCache= requestCache;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException {
        SavedRequest savedRequest = this.requestCache.getRequest(request, response);

        String targetUrl = savedRequest == null ? this.redirect : savedRequest.getRedirectUrl();
        clearAuthenticationAttributes(request);
        HttpServletUtils.resultJson(response, HttpServletResponse.SC_OK,
                new R<>(JwtErrorConst.LOGIN_SUCCESS.getCode(), "登录成功", Collections.singletonMap("targetUrl", targetUrl)));
    }

    public void setRequestCache(RequestCache requestCache) {
        this.requestCache = requestCache;
    }

    protected final void clearAuthenticationAttributes(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
        }
    }

}
