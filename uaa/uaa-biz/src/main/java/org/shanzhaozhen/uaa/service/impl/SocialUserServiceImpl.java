package org.shanzhaozhen.uaa.service.impl;

import lombok.RequiredArgsConstructor;
import org.shanzhaozhen.common.core.utils.CustomBeanUtils;
import org.shanzhaozhen.common.web.utils.JwtUtils;
import org.shanzhaozhen.uaa.constant.SocialType;
import org.shanzhaozhen.uaa.converter.SocialUserConverter;
import org.shanzhaozhen.uaa.mapper.GithubUserMapper;
import org.shanzhaozhen.uaa.pojo.dto.SocialInfo;
import org.shanzhaozhen.uaa.pojo.entity.GithubUser;
import org.shanzhaozhen.uaa.pojo.form.SocialUserBindForm;
import org.shanzhaozhen.uaa.service.SocialUserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.time.LocalDateTime;

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
    public SocialInfo getSocialInfo(String userId) {
        SocialInfo socialInfo = new SocialInfo();

        GithubUser githubUser = githubUserMapper.getGithubUserByUserId(userId);

        socialInfo.setGithub(SocialUserConverter.convertGithubUserInfo(githubUser));
        return socialInfo;
    }

    @Override
    @Transactional
    public void unbindSocial(String userId, String type) {
        Assert.hasText(userId, "没有获得当前登陆用户的信息，解绑失败！");
        if (SocialType.GITHUB.getName().equals(type)) {
            GithubUser githubUser = githubUserMapper.getGithubUserByLogin(userId);
            Assert.notNull(githubUser, "该用户没有绑定该类型的账号！");
            githubUser.setUserId(null);
            githubUserMapper.updateById(githubUser);
        } else {
            throw new IllegalArgumentException("不支持该类型账号（" + type + "）解绑！");
        }
    }

    @Override
    @Transactional
    public GithubUser updateGithubUser(GithubUser githubUser) {
        GithubUser githubUserInDB = githubUserMapper.getGithubUserByLogin(githubUser.getLogin());
        CustomBeanUtils.copyPropertiesExcludeMeta(githubUser, githubUserInDB);
        githubUserMapper.updateById(githubUserInDB);
        return githubUserInDB;
    }

    @Override
    @Transactional
    public void bindGithubUser(SocialUserBindForm<GithubUser> socialUserBindForm) {
        GithubUser socialUser = socialUserBindForm.getSocialUser();
        Assert.notNull(socialUser, "没有真实的 github 用户信息，绑定失败！");
        String currentUserId = socialUserBindForm.getUserId();
        Assert.hasText(currentUserId, "没有获得当前登陆用户的信息，绑定失败！");

        // 检查当前用户是否已绑定其他github用户
        GithubUser binding = this.githubUserMapper.getGithubUserByUserId(currentUserId);


        if (binding != null) {
            if (binding.getUsername().equals(socialUser.getUsername())) {
                // 已经绑定了当前用户，直接返回
                return;
            } else {
                throw new IllegalArgumentException("当前用户已跟其它 github 账号绑定，请先解绑后再操作！");
            }
        }

        GithubUser githubUserInDB = githubUserMapper.getGithubUserByUsername(socialUser.getUsername());
        Assert.isNull(githubUserInDB, "该 github 账号已被其他账号绑定，请更换！");

        socialUser.setUserId(currentUserId);
        socialUser.setBindDate(LocalDateTime.now());
        githubUserMapper.insert(socialUser);
    }

}
