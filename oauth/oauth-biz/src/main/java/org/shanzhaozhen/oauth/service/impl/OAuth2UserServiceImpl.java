package org.shanzhaozhen.oauth.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.RequiredArgsConstructor;
import org.shanzhaozhen.common.core.utils.CustomBeanUtils;
import org.shanzhaozhen.common.core.utils.PasswordUtils;
import org.shanzhaozhen.common.web.utils.JwtUtils;
import org.shanzhaozhen.oauth.mapper.OAuth2UserMapper;
import org.shanzhaozhen.oauth.pojo.dto.OAuth2UserDTO;
import org.shanzhaozhen.oauth.pojo.dto.OAuth2UserInfoDTO;
import org.shanzhaozhen.oauth.pojo.entity.OAuth2UserDO;
import org.shanzhaozhen.oauth.service.OAuth2UserInfoService;
import org.shanzhaozhen.oauth.service.OAuth2UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
@RequiredArgsConstructor
@CacheConfig(cacheNames = "oauth-user")
public class OAuth2UserServiceImpl implements OAuth2UserService {

    private final OAuth2UserInfoService oauth2UserInfoService;
    private final OAuth2UserMapper oauth2UserMapper;

    @Override
    public OAuth2UserDTO getOAuth2UserById(String userId) {
        OAuth2UserDTO oauth2UserDTO = oauth2UserMapper.getOAuth2UserById(userId);
        Assert.notNull(oauth2UserDTO, "用户不存在");
        OAuth2UserInfoDTO oauth2UserInfoDTO = oauth2UserInfoService.getOAuth2UserInfoByUserId(userId);
        oauth2UserDTO.setUserInfo(oauth2UserInfoDTO);
        return oauth2UserDTO;
    }

    @Override
    public OAuth2UserDTO getOAuth2UserByUserId(String userId) {
        return oauth2UserMapper.getOAuth2UserByUserId(userId);
    }

    @Override
    public OAuth2UserDTO getCurrentUser() {
        String userId = JwtUtils.getUserIdWithoutError();
        Assert.notNull(userId, "请求头没有包含用户信息");
        OAuth2UserDTO userDTO = oauth2UserMapper.getOAuth2UserByUserId(userId);
        Assert.notNull(userDTO, "没有找到当前用户信息");
        return userDTO;
    }

    @Override
    @Transactional
    public String register(OAuth2UserDTO userDTO) {
        Assert.isNull(oauth2UserMapper.selectUserByUsername(userDTO.getUsername()), "注册失败，该用户名已存在！");
        OAuth2UserDO newUser = new OAuth2UserDO();
        BeanUtils.copyProperties(userDTO, newUser, "accountNonExpired", "accountNonLocked", "credentialsNonExpired", "enabled");
        newUser.setPassword(PasswordUtils.encryption(userDTO.getPassword()));
        oauth2UserMapper.insert(newUser);
        return newUser.getId();
    }

    @Override
    public Boolean isExistUser(String username) {
        OAuth2UserDO oauth2UserDO = oauth2UserMapper.selectUserByUsername(username);
        return oauth2UserDO == null;
    }

    @Override
    @Transactional
    public Page<OAuth2UserDTO> getOAuth2UserPage(Page<OAuth2UserDTO> page, String keyword) {
        return oauth2UserMapper.getOAuth2UserPage(page, keyword);
    }

    @Override
    @Transactional
    public String addOAuth2User(OAuth2UserDTO userDTO) {
        Assert.isNull(oauth2UserMapper.selectUserByUsername(userDTO.getUsername()), "注册失败，该用户名已存在！");
        OAuth2UserDO userDO = new OAuth2UserDO();
        CustomBeanUtils.copyPropertiesExcludeMetaAndNull(userDTO, userDO, "password");
        userDO.setPassword(PasswordUtils.encryption(userDTO.getPassword()));
        oauth2UserMapper.insert(userDO);
        return userDO.getId();
    }

    @Override
    @Transactional
    public String updateOAuth2User(OAuth2UserDTO oauth2UserDTO) {
        Assert.notNull(oauth2UserDTO.getId(), "用户id不能为空");
        OAuth2UserDO oauth2UserDO = oauth2UserMapper.selectById(oauth2UserDTO.getId());
        Assert.notNull(oauth2UserDO, "更新失败：没有找到该用户或已被删除");
        CustomBeanUtils.copyPropertiesExcludeMetaAndNull(oauth2UserDTO, oauth2UserDO, "password");
        // 密码不为空则更新密码
        if (StringUtils.hasText(oauth2UserDTO.getPassword())) {
            oauth2UserDO.setPassword(PasswordUtils.encryption(oauth2UserDTO.getPassword()));
        }

        // 更新主用户
        oauth2UserMapper.updateById(oauth2UserDO);

        // 更新用户信息
        OAuth2UserInfoDTO userInfo = oauth2UserDTO.getUserInfo();
        if (userInfo != null) {
            userInfo.setPid(oauth2UserDTO.getId());
            oauth2UserInfoService.updateOAuth2UserInfo(userInfo);
        }

        return oauth2UserDO.getId();
    }

    @Override
    @Transactional
    public String deleteOAuth2User(String userId) {
        oauth2UserMapper.deleteById(userId);
        oauth2UserInfoService.deleteOAuth2UserInfoByUserId(userId);
        return userId;
    }

    @Override
    @Transactional
    public List<String> batchDeleteOAuth2User(List<String> userIds) {
        Assert.notEmpty(userIds, "没有需要删除的用户");
        for (String userId : userIds) {
            this.deleteOAuth2User(userId);
        }
        return userIds;
    }

}
