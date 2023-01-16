package org.shanzhaozhen.authorize.service.impl;

import lombok.RequiredArgsConstructor;
import org.shanzhaozhen.authorize.constant.SocialType;
import org.shanzhaozhen.oauth.converter.OAuth2UserSocialConverter;
import org.shanzhaozhen.authorize.mapper.OAuth2UserSocialMapper;
import org.shanzhaozhen.authorize.pojo.dto.SocialInfo;
import org.shanzhaozhen.authorize.service.OAuth2UserService;
import org.shanzhaozhen.authorize.service.OAuthUserSocialService;
import org.shanzhaozhen.authorize.utils.SecurityUtils;
import org.shanzhaozhen.oauth.pojo.dto.OAuth2UserDTO;
import org.shanzhaozhen.oauth.pojo.entity.OAuth2UserSocialDO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;

/**
 * @Author: shanzhaozhen
 * @Date: 2022-09-02
 * @Description:
 */
@Service
@RequiredArgsConstructor
public class OAuth2UserSocialServiceImpl implements OAuthUserSocialService {

    private final OAuth2UserSocialMapper oauth2UserSocialMapper;

    private final OAuth2UserService oauth2UserService;

    @Override
    public SocialInfo getSocialInfo(String userId) {
        SocialInfo socialInfo = new SocialInfo();

        OAuth2UserSocialDO githubUser = oauth2UserSocialMapper.getOAuth2UserSocialByUserIdAndIdentityType(userId, SocialType.GITHUB.getName());
        if (githubUser != null) {
            socialInfo.setGithub(OAuth2UserSocialConverter.toVO(githubUser));
        }

        return socialInfo;
    }

    @Override
    public SocialInfo getCurrentSocialInfo() {
        String currentUserId = SecurityUtils.getCurrentUserId();
        Assert.hasText(currentUserId, "当前没有登陆用户或为匿名用户");
        return getSocialInfo(currentUserId);
    }

    @Override
    public void bindSocialUser(String userId, String socialUsername, String socialType) {
        Assert.hasText(userId, "没有获取到账号ID！");
        OAuth2UserSocialDO oauth2UserSocial = oauth2UserSocialMapper.getOAuth2UserSocialByIdentityAndTypeAndIdentifier(socialType, socialUsername);
        Assert.notNull(oauth2UserSocial, "第三方账号不存在！");
        oauth2UserSocial.setUserId(userId);
        oauth2UserSocialMapper.updateById(oauth2UserSocial);
    }

    @Override
    @Transactional
    public void unbindSocial(String type) {
        String currentUserId = SecurityUtils.getCurrentUserId();
        Assert.hasText(currentUserId, "当前没有登陆用户或为匿名用户");
        OAuth2UserSocialDO oauth2UserSocial = oauth2UserSocialMapper.getOAuth2UserSocialByUserIdAndIdentityType(currentUserId, type);
        Assert.notNull(oauth2UserSocial, "第三方账号不存在！");
        oauth2UserSocial.setUserId("");
        oauth2UserSocialMapper.updateById(oauth2UserSocial);
    }

    @Override
    public void bindSocialUser(OAuth2UserSocialDO oauth2UserSocialDO) {
        String currentUserId = SecurityUtils.getCurrentUserId();
        Assert.hasText(currentUserId, "没有获得当前登陆用户的信息，绑定失败！");

        // 检查当前用户有没有绑定该类型账号
        OAuth2UserSocialDO binding = oauth2UserSocialMapper.getOAuth2UserSocialByUserIdAndIdentityType(currentUserId, oauth2UserSocialDO.getIdentityType());
        if (binding != null) {
            if (binding.getIdentifier().equals(oauth2UserSocialDO.getUserId())) {
                // 已经绑定了当前用户，直接返回
                return;
            } else {
                throw new IllegalArgumentException("当前用户已跟其它 github 账号绑定，请先解绑后再操作！");
            }
        }

        // 检查该账号有没有绑定其他账号
        OAuth2UserSocialDO oauth2UserSocialInDB = oauth2UserSocialMapper.getOAuth2UserSocialByIdentityAndTypeAndIdentifier(oauth2UserSocialDO.getIdentityType(), oauth2UserSocialDO.getIdentifier());
        Assert.isTrue(oauth2UserSocialInDB == null || !StringUtils.hasText(oauth2UserSocialInDB.getUserId()), "该第三方账号已被其他账号绑定，请更换！");

        oauth2UserSocialDO.setUserId(currentUserId);
        oauth2UserSocialDO.setBindDate(LocalDateTime.now());
        if (oauth2UserSocialInDB == null) {
            oauth2UserSocialMapper.insert(oauth2UserSocialDO);
        } else {
            oauth2UserSocialDO.setId(oauth2UserSocialInDB.getId());
            oauth2UserSocialMapper.updateById(oauth2UserSocialDO);
        }
    }

    @Override
    public OAuth2UserDTO loadUserBySocial(String identityType, String identifier) {
        // todo: 考虑一下要不要每次第三方登陆都更新

        OAuth2UserSocialDO oauth2UserSocial = oauth2UserSocialMapper.getOAuth2UserSocialByIdentityAndTypeAndIdentifier(identityType, identifier);
        Assert.notNull(oauth2UserSocial, "用户不存在该类型关联信息！");

        if (StringUtils.hasText(oauth2UserSocial.getUserId())) {
            return oauth2UserService.getUserById(oauth2UserSocial.getUserId());
        } else {
            return null;
        }
    }

}
