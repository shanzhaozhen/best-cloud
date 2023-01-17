package org.shanzhaozhen.oauth.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.RequiredArgsConstructor;
import org.shanzhaozhen.common.core.utils.CustomBeanUtils;
import org.shanzhaozhen.common.web.utils.JwtUtils;
import org.shanzhaozhen.oauth.converter.OAuth2UserInfoConverter;
import org.shanzhaozhen.oauth.mapper.OAuth2UserInfoMapper;
import org.shanzhaozhen.oauth.mapper.OAuth2UserMapper;
import org.shanzhaozhen.oauth.pojo.dto.OAuth2UserInfoDTO;
import org.shanzhaozhen.oauth.pojo.entity.OAuth2UserDO;
import org.shanzhaozhen.oauth.pojo.entity.OAuth2UserInfoDO;
import org.shanzhaozhen.oauth.service.OAuth2UserInfoService;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.List;


@Service
@RequiredArgsConstructor
@CacheConfig(cacheNames = "user-info")
public class OAuth2UserInfoServiceImpl implements OAuth2UserInfoService {

    private final OAuth2UserInfoMapper oauth2userInfoMapper;
    private final OAuth2UserMapper oauth2UserMapper;


    @Override
    public OAuth2UserInfoDTO getOAuth2UserInfoById(String userInfoId) {
        return oauth2userInfoMapper.getOAuth2UserInfoById(userInfoId);
    }

    @Override
    public OAuth2UserInfoDTO getOAuth2UserInfoByUserId(String userId) {
        return oauth2userInfoMapper.getOAuth2UserInfoByUserId(userId);
    }

    @Override
    public OAuth2UserInfoDTO getCurrentUserInfo() {
        String userId = JwtUtils.getUserIdWithoutError();
        Assert.notNull(userId, "当前访问没有获取到登陆状态");
        return this.getOAuth2UserInfoByUserId(userId);
    }

    @Override
    @Transactional
    public String addOAuth2UserInfo(OAuth2UserInfoDTO userInfoDTO) {
        Assert.notNull(userInfoDTO.getPid(), "关联的用户ID不能为空");
        OAuth2UserInfoDO userInfoDO = OAuth2UserInfoConverter.toDO(userInfoDTO);
        oauth2userInfoMapper.insert(userInfoDO);
        return userInfoDO.getId();
    }

    @Override
    @Transactional
    public String updateOAuth2UserInfo(OAuth2UserInfoDTO userInfoDTO) {
        Assert.notNull(userInfoDTO.getPid(), "关联的用户ID不能为空");

        OAuth2UserDO user = oauth2UserMapper.selectById(userInfoDTO.getPid());
        Assert.notNull(user, "用户不存在");

        OAuth2UserInfoDO userInfoDO = oauth2userInfoMapper.selectOne(new LambdaQueryWrapper<OAuth2UserInfoDO>().eq(OAuth2UserInfoDO::getPid, userInfoDTO.getPid()));
        if (userInfoDO != null) {
            CustomBeanUtils.copyPropertiesExcludeMetaAndNull(userInfoDTO, userInfoDO);
            oauth2userInfoMapper.updateById(userInfoDO);
            return userInfoDO.getId();
        } else {
            return this.addOAuth2UserInfo(userInfoDTO);
        }
    }

    @Override
    @Transactional
    public String deleteOAuth2UserInfo(String userInfoId) {
        oauth2userInfoMapper.deleteById(userInfoId);
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
        oauth2userInfoMapper.deleteByUserId(userId);
    }


}
