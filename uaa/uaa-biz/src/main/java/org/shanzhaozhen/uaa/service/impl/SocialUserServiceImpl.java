package org.shanzhaozhen.uaa.service.impl;

import lombok.RequiredArgsConstructor;
import org.shanzhaozhen.common.core.utils.CustomBeanUtils;
import org.shanzhaozhen.uaa.mapper.GithubUserMapper;
import org.shanzhaozhen.uaa.pojo.entity.GithubUser;
import org.shanzhaozhen.uaa.pojo.entity.SocialUser;
import org.shanzhaozhen.uaa.pojo.form.SocialUserBindForm;
import org.shanzhaozhen.uaa.service.SocialUserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

/**
 * @Author: shanzhaozhen
 * @Date: 2022-09-02
 * @Description:
 */
@Service
@RequiredArgsConstructor
public class SocialUserServiceImpl implements SocialUserService {

    private final GithubUserMapper githubUserMapper;

    @Override
    @Transactional
    public GithubUser updateGithubUser(GithubUser githubUser) {
        GithubUser githubUserInDB = githubUserMapper.getGithubUserByLogin(githubUser.getLogin());
        if (githubUserInDB == null) {
            githubUserMapper.insert(githubUser);
            return githubUser;
        } else {
            CustomBeanUtils.copyPropertiesExcludeMeta(githubUser, githubUserInDB);
            githubUserMapper.updateById(githubUserInDB);
            return githubUserInDB;
        }
    }

    @Override
    @Transactional
    public void bindGithubUser(SocialUserBindForm<GithubUser> socialUserBindForm) {
        GithubUser socialUser = socialUserBindForm.getSocialUser();
        Assert.notNull(socialUser, "没有真实的 github 用户信息，绑定失败！");
        String currentUserId = socialUserBindForm.getUserId();
        Assert.hasText(currentUserId, "没有获得当前登陆用户的信息，绑定失败！");
        GithubUser githubUser = this.updateGithubUser(socialUser);

        String userId = githubUser.getUserId();
        Assert.isTrue(!StringUtils.hasText(userId) || userId.equals(currentUserId), "该github账号已被绑定，请更换！");

        githubUser.setUserId(userId);
        githubUserMapper.updateById(githubUser);
    }

}
