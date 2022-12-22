package org.shanzhaozhen.authorize.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.RequiredArgsConstructor;
import org.shanzhaozhen.authorize.converter.OAuth2UserInfoConverter;
import org.shanzhaozhen.authorize.mapper.OAuth2UserInfoMapper;
import org.shanzhaozhen.authorize.mapper.OAuth2UserMapper;
import org.shanzhaozhen.authorize.pojo.dto.OAuth2UserInfoDTO;
import org.shanzhaozhen.authorize.pojo.entity.OAuth2UserDO;
import org.shanzhaozhen.authorize.pojo.entity.OAuth2UserInfoDO;
import org.shanzhaozhen.authorize.service.OAuth2UserInfoService;
import org.shanzhaozhen.authorize.utils.SecurityUtils;
import org.shanzhaozhen.common.core.utils.CustomBeanUtils;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.List;


@Service
@RequiredArgsConstructor
@CacheConfig(cacheNames = "user-info")
public class OAuth2UserInfoServiceImpl implements OAuth2UserInfoService {

    private final OAuth2UserInfoMapper oauth2UserInfoMapper;
    private final OAuth2UserMapper oauth2UserMapper;


    @Override
    public OAuth2UserInfoDTO getOAuth2UserInfoById(String userInfoId) {
        return oauth2UserInfoMapper.getOAuth2UserInfoById(userInfoId);
    }

    @Override
    public OAuth2UserInfoDTO getOAuth2UserInfoByUserId(String userId) {
        return oauth2UserInfoMapper.getOAuth2UserInfoByUserId(userId);
    }

    @Override
    public OAuth2UserInfoDTO getCurrentUserInfo() {
        String userId = SecurityUtils.getCurrentUserId();
        Assert.notNull(userId, "当前访问没有获取到登陆状态");
        return this.getOAuth2UserInfoByUserId(userId);
    }

    @Override
    @Transactional
    public String addOAuth2UserInfo(OAuth2UserInfoDTO OAuth2UserInfoDTO) {
        Assert.notNull(OAuth2UserInfoDTO.getPid(), "关联的用户ID不能为空");
        OAuth2UserInfoDO OAuth2UserInfoDO = OAuth2UserInfoConverter.toDO(OAuth2UserInfoDTO);
        oauth2UserInfoMapper.insert(OAuth2UserInfoDO);
        return OAuth2UserInfoDO.getId();
    }

    @Override
    @Transactional
    public String updateOAuth2UserInfo(OAuth2UserInfoDTO oauth2UserInfoDTO) {
//        Assert.notNull(OAuth2UserInfoDTO.getId(), "用户信息ID不能为空");
        Assert.notNull(oauth2UserInfoDTO.getPid(), "关联的用户ID不能为空");

        OAuth2UserDO user = oauth2UserMapper.selectById(oauth2UserInfoDTO.getPid());
        Assert.notNull(user, "用户不存在");

        OAuth2UserInfoDO oauth2UserInfoDO = oauth2UserInfoMapper.selectOne(new LambdaQueryWrapper<OAuth2UserInfoDO>().eq(OAuth2UserInfoDO::getPid, oauth2UserInfoDTO.getPid()));
        if (oauth2UserInfoDO != null) {
            CustomBeanUtils.copyPropertiesExcludeMetaAndNull(oauth2UserInfoDTO, oauth2UserInfoDO);
            oauth2UserInfoMapper.updateById(oauth2UserInfoDO);
            return oauth2UserInfoDO.getId();
        } else {
            return this.addOAuth2UserInfo(oauth2UserInfoDTO);
        }
    }

    @Override
    public void updateCurrentUserinfo(OAuth2UserInfoDTO oauth2UserInfoDTO) {
        String userId = SecurityUtils.getCurrentUserId();
        Assert.notNull(userId, "当前访问没有获取到登陆状态");
        oauth2UserInfoDTO.setPid(userId);
        updateOAuth2UserInfo(oauth2UserInfoDTO);
    }

    @Override
    @Transactional
    public String deleteOAuth2UserInfo(String userInfoId) {
        oauth2UserInfoMapper.deleteById(userInfoId);
        return userInfoId;
    }

    @Override
    @Transactional
    public List<String> batchDeleteOAuth2UserInfo(List<String> userInfoIds) {
        Assert.notEmpty(userInfoIds, "没有需要删除的用户信息");
        for (String userInfoId : userInfoIds) {
            this.deleteOAuth2UserInfo(userInfoId);
        }
        return userInfoIds;
    }

    @Override
    @Transactional
    public void deleteOAuth2UserInfoByUserId(String userId) {
        oauth2UserInfoMapper.deleteByUserId(userId);
    }

}
