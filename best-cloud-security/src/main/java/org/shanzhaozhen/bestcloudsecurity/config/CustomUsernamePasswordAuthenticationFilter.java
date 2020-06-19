package org.shanzhaozhen.bestcloudsecurity.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.shanzhaozhen.basiccommon.enums.sys.JwtErrorConst;
import org.shanzhaozhen.basiccommon.dto.UserDTO;
import org.shanzhaozhen.basiccommon.form.UserLoginForm;
import org.shanzhaozhen.basiccommon.utils.HttpServletResponseUtils;
import org.shanzhaozhen.basiccommon.vo.ResultObject;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CustomUsernamePasswordAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    private final CustomJwtTokenProvider customJwtTokenProvider;

    protected CustomUsernamePasswordAuthenticationFilter(CustomJwtTokenProvider customJwtTokenProvider) {
        super(new AntPathRequestMatcher("/login", "POST"));
        this.customJwtTokenProvider = customJwtTokenProvider;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws AuthenticationException, IOException {
        //非post请求处理
        if (!httpServletRequest.getMethod().equals("POST")) {
            throw new AuthenticationServiceException("不支持该授权方式: " + httpServletRequest.getMethod());
        }

        try {
            UserLoginForm userLoginForm = new ObjectMapper().readValue(httpServletRequest.getInputStream(), UserLoginForm.class);
            String username = userLoginForm.getUsername();
            String password = userLoginForm.getPassword();

            if (username == null) {
                username = "";
            }
            if (password == null) {
                password = "";
            }
            username = username.trim();

            /**
             * UsernamePasswordAuthenticationToken 是 Authentication 的实现类
             * 封装到token中提交
             */
            UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(username, password);

            /**
             * authenticate()接受一个token参数,返回一个完全经过身份验证的对象，包括证书.
             * 里并没有对用户名密码进行验证,而是使用 AuthenticationProvider 提供的 authenticate 方法返回一个完全经过身份验证的对象，包括证书.
             * Authentication authenticate = authenticationManager.authenticate(authenticationToken);
             */
            setDetails(httpServletRequest, authRequest);

            return this.getAuthenticationManager().authenticate(authRequest);
        } catch (IOException e) {
            throw new InternalAuthenticationServiceException("登陆失败：" + e.getMessage());
        }
    }

    protected void setDetails(HttpServletRequest httpServletRequest,
                              UsernamePasswordAuthenticationToken authRequest) {
        authRequest.setDetails(authenticationDetailsSource.buildDetails(httpServletRequest));
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException {
        UserDTO userDTO = (UserDTO) authResult.getPrincipal();
        String token = customJwtTokenProvider.buildToken(userDTO.getId(), userDTO.getUsername());

        // 登陆成功返回
        HttpServletResponseUtils.resultJson(response, HttpServletResponse.SC_OK,
                new ResultObject<>(JwtErrorConst.LOGIN_SUCCESS.getCode(), "登陆成功", token));
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException {
        String msg = failed == null ? "参数错误" : failed.getMessage();
//        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, msg);
        HttpServletResponseUtils.resultJson(response, HttpServletResponse.SC_UNAUTHORIZED,
                new ResultObject<>(JwtErrorConst.LOGIN_ERROR.getCode(), msg)
        );
    }

}
