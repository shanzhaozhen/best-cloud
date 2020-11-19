package org.shanzhaozhen.authorize.config.oauth2;

import org.shanzhaozhen.basiccommon.dto.UserDTO;
import org.shanzhaozhen.common.enums.AuthConstants;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * JWT内容增强器
 */
@Component
public class JwtTokenEnhancer implements TokenEnhancer {

    // 令牌增强
    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
        UserDTO user = (UserDTO) authentication.getPrincipal();
        Map<String, Object> info = new HashMap<>();
        //把用户信息设置到JWT中
        info.put(AuthConstants.JWT_USER_ID_KEY, user.getId());
        info.put(AuthConstants.JWT_ROLE_KEY, user.getAuthorities());
//        info.put(AuthConstants.JWT_CLIENT_ID_KEY, user.getClientId());
        ((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(info);
        return accessToken;
    }

}
