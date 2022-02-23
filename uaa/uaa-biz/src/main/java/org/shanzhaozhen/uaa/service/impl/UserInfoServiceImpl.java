package org.shanzhaozhen.uaa.service.impl;

import lombok.RequiredArgsConstructor;
import org.shanzhaozhen.common.core.utils.CustomBeanUtils;
import org.shanzhaozhen.common.web.utils.JwtUtils;
import org.shanzhaozhen.uaa.converter.UserInfoConverter;
import org.shanzhaozhen.uaa.mapper.UserInfoMapper;
import org.shanzhaozhen.uaa.pojo.dto.UserInfoDTO;
import org.shanzhaozhen.uaa.pojo.entity.UserInfoDO;
import org.shanzhaozhen.uaa.service.UserInfoService;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;


@Service
@RequiredArgsConstructor
@CacheConfig(cacheNames = "user-info")
public class UserInfoServiceImpl implements UserInfoService {

    private final UserInfoMapper userInfoMapper;


    @Override
    public UserInfoDTO getUserInfoById(Long userInfoId) {
        return userInfoMapper.getUserInfoById(userInfoId);
    }

    @Override
    public UserInfoDTO getUserInfoByUserId(Long userId) {
        return userInfoMapper.getUserInfoByUserId(userId);
    }

    @Override
    public UserInfoDTO getCurrentUserInfo() {
        Long userId = JwtUtils.getUserIdWithoutError();
        Assert.notNull(userId, "当前访问没有获取到登陆状态");
        return this.getUserInfoByUserId(userId);
    }

    @Override
    public Long addUserInfo(UserInfoDTO userInfoDTO) {
        Assert.notNull(userInfoDTO.getPid(), "关联的用户ID不能为空");
        UserInfoDO userInfoDO = UserInfoConverter.toDO(userInfoDTO);
        userInfoMapper.insert(userInfoDO);
        return userInfoDO.getId();
    }

    @Override
    public Long updateUserInfo(UserInfoDTO userInfoDTO) {
        Assert.notNull(userInfoDTO.getPid(), "用户信息ID不能为空");
        Assert.notNull(userInfoDTO.getPid(), "关联的用户ID不能为空");
        UserInfoDO userInfoDO = userInfoMapper.selectById(userInfoDTO.getId());
        Assert.notNull(userInfoDO, "更新失败：没有找到该用户信息或已被删除");
        CustomBeanUtils.copyPropertiesExcludeMeta(userInfoDTO, userInfoDO);
        userInfoMapper.updateById(userInfoDO);
        return userInfoDO.getId();
    }

    @Override
    public Long deleteUserInfo(Long userInfoId) {
        userInfoMapper.deleteById(userInfoId);
        return userInfoId;
    }

    @Override
    public List<Long> batchDeleteUserInfo(List<Long> userInfoIds) {
        Assert.notEmpty(userInfoIds, "没有需要删除的用户信息");
        for (Long userInfoId : userInfoIds) {
            this.deleteUserInfo(userInfoId);
        }
        return userInfoIds;
    }

    @Override
    public void deleteUserInfoByUserId(Long userId) {
        userInfoMapper.deleteByUserId(userId);
    }


}
