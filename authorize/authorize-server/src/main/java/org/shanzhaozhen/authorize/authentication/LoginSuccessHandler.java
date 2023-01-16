package org.shanzhaozhen.authorize.authentication;

import org.shanzhaozhen.authorize.pojo.dto.UserInfoBase;
import org.shanzhaozhen.authorize.service.OAuth2UserInfoService;
import org.shanzhaozhen.authorize.utils.SecurityUtils;
import org.shanzhaozhen.common.core.utils.SpringContextUtils;
import org.shanzhaozhen.oauth.pojo.dto.OAuth2UserInfoDTO;
import org.springframework.beans.BeanUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class LoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

//    private final OAuth2UserInfoService oauth2UserInfoService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws ServletException, IOException {
        OAuth2UserInfoService oauth2UserInfoService = SpringContextUtils.getBean("OAuth2UserInfoServiceImpl", OAuth2UserInfoService.class);

        // 获取用户信息记录在 session 中
        String currentUserId = SecurityUtils.getCurrentUserId();
        OAuth2UserInfoDTO oauth2UserInfo = oauth2UserInfoService.getOAuth2UserInfoByUserId(currentUserId);
        if (oauth2UserInfo != null) {
            UserInfoBase userInfo = new UserInfoBase();
            BeanUtils.copyProperties(oauth2UserInfo, userInfo);
            HttpSession httpSession = request.getSession();
            httpSession.setAttribute("userInfo", userInfo);
        }

        super.onAuthenticationSuccess(request, response, authentication);
    }

}
