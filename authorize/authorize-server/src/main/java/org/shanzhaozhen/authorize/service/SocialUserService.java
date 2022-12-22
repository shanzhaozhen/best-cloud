package org.shanzhaozhen.authorize.service;


import org.shanzhaozhen.authorize.pojo.dto.OAuth2UserDTO;
import org.shanzhaozhen.authorize.pojo.dto.SocialInfo;
import org.shanzhaozhen.authorize.pojo.entity.GithubUser;
import org.shanzhaozhen.authorize.pojo.form.SocialUserBindForm;

/**
 * @Author: shanzhaozhen
 * @Date: 2022-09-02
 * @Description:
 */
public interface SocialUserService {

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
     * 更新github用户信息
     * @param githubUser
     */
    GithubUser updateGithubUser(GithubUser githubUser);

    /**
     * 绑定github用户
     * @param socialUserBindForm
     */
    void bindGithubUser(SocialUserBindForm<GithubUser> socialUserBindForm);

    /**
     * 第三方账号登陆
     * @param username
     * @param type
     * @return
     */
    OAuth2UserDTO loadUserBySocial(String username, String type);

}
