package org.shanzhaozhen.authorize.service;


import org.shanzhaozhen.authorize.pojo.dto.SocialInfo;
import org.shanzhaozhen.oauth.pojo.dto.OAuth2UserDTO;
import org.shanzhaozhen.oauth.pojo.entity.OAuth2UserSocialDO;

/**
 * @Author: shanzhaozhen
 * @Date: 2022-09-02
 * @Description:
 */
public interface OAuthUserSocialService {

    /**
     * 获取用户绑定信息
     * @param userId
     */
    SocialInfo getSocialInfo(String userId);

    /**
     * 获取当前用户绑定信息
     * @return
     */
    SocialInfo getCurrentSocialInfo();

    /**
     * 第三方账号绑定
     * @param userId
     * @param socialUsername
     * @param socialType
     */
    void bindSocialUser(String userId, String socialUsername, String socialType);

    /**
     * 解绑第三方账号
     * @param type
     */
    void unbindSocial(String type);

    /**
     * 绑定第三方用户
     * @param oauth2UserSocialDO
     */
    void bindSocialUser(OAuth2UserSocialDO oauth2UserSocialDO);

    /**
     * 第三方账号登陆
     * @param identifier
     * @param identityType
     * @return
     */
    OAuth2UserDTO loadUserBySocial(String identityType, String identifier);

}
