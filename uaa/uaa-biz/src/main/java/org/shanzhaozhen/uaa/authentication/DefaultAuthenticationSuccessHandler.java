package org.shanzhaozhen.uaa.authentication;

import org.shanzhaozhen.common.core.enums.JwtErrorConst;
import org.shanzhaozhen.common.core.result.R;
import org.shanzhaozhen.common.core.utils.HttpServletUtils;
import org.shanzhaozhen.uaa.pojo.dto.UserDTO;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class DefaultAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        UserDTO userDTO = (UserDTO) authentication.getPrincipal();

        // TODO: 2022/7/21 改成redis缓存

        // TODO: 2022/7/21 记录登陆记录

        // 生成 jwt token
//        String token = customJwtTokenProvider.buildToken(userDTO.getId(), userDTO.getUsername());

        // 登陆成功返回
        HttpServletUtils.resultJson(response, HttpServletResponse.SC_OK,
                new R<>(JwtErrorConst.LOGIN_SUCCESS.getCode(), "登陆成功", "token"));
    }

}
