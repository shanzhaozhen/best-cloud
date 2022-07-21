package org.shanzhaozhen.uaa.authentication;

import org.shanzhaozhen.common.core.enums.JwtErrorConst;
import org.shanzhaozhen.common.core.result.R;
import org.shanzhaozhen.common.core.utils.HttpServletUtils;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class DefaultAuthenticationFailureHandler implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {

        // TODO: 2022/7/21 记录登陆失败记录

        String msg = exception == null ? "参数错误" : exception.getMessage();
        HttpServletUtils.resultJson(response, HttpServletResponse.SC_UNAUTHORIZED,
                new R<>(JwtErrorConst.LOGIN_ERROR.getCode(), msg)
        );

    }

}
