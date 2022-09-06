package org.shanzhaozhen.uaa.service;

import org.shanzhaozhen.uaa.pojo.dto.SocialInfo;
import org.shanzhaozhen.uaa.pojo.entity.GithubUser;
import org.shanzhaozhen.uaa.pojo.form.SocialUserBindForm;

/**
 * @Author: shanzhaozhen
 * @Date: 2022-09-02
 * @Description:
 */
public interface SocialUserService {

    /**
     * 更新github用户信息
     * @param userId
     */
    SocialInfo getSocialInfo(String userId);

    /**
     * 更新github用户信息
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

}
