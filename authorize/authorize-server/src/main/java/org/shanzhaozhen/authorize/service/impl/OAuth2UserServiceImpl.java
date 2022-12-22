package org.shanzhaozhen.authorize.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.RequiredArgsConstructor;
import org.shanzhaozhen.authorize.mapper.OAuth2UserMapper;
import org.shanzhaozhen.authorize.pojo.dto.OAuth2UserDTO;
import org.shanzhaozhen.authorize.pojo.entity.OAuth2UserDO;
import org.shanzhaozhen.authorize.service.OAuth2UserService;
import org.shanzhaozhen.authorize.utils.SecurityUtils;
import org.shanzhaozhen.common.core.utils.CustomBeanUtils;
import org.shanzhaozhen.common.core.utils.PasswordUtils;
import org.shanzhaozhen.authorize.pojo.form.ChangePasswordForm;
import org.springframework.beans.BeanUtils;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
@RequiredArgsConstructor
@CacheConfig(cacheNames = "user")
public class OAuth2UserServiceImpl implements OAuth2UserService {

    private final OAuth2UserMapper oauth2UserMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public OAuth2UserDTO getUserById(String userId) {
        OAuth2UserDTO user = oauth2UserMapper.getUserById(userId);
        Assert.notNull(user, "用户不存在");
        return user;
    }

    @Override
    public OAuth2UserDTO getUserByUsername(String username) {
        return oauth2UserMapper.getUserByUsername(username);
    }

    @Override
    public OAuth2UserDTO getUserByUserId(String userId) {
        OAuth2UserDTO user = oauth2UserMapper.getUserByUserId(userId);
        return user;
    }

//    @Override
//    @Cacheable(key = "#root.methodName + ':' + #userId")
//    public JWTUser getJWTUser(String userId) {
//        return oauth2UserMapper.getJWTUserByUserId(userId);
//    }

    @Override
    public OAuth2UserDTO getCurrentUser() {
        return null;
    }

    @Override
    @Transactional
    public String register(OAuth2UserDTO oauth2UserDTO) {
        Assert.isNull(oauth2UserMapper.selectUserByUsername(oauth2UserDTO.getUsername()), "注册失败，该用户名已存在！");
        OAuth2UserDO newUser = new OAuth2UserDO();
        BeanUtils.copyProperties(oauth2UserDTO, newUser, "accountNonExpired", "accountNonLocked", "credentialsNonExpired", "enabled");
        newUser.setPassword(PasswordUtils.encryption(oauth2UserDTO.getPassword()));
        oauth2UserMapper.insert(newUser);
        return newUser.getId();
    }

    @Override
    public Boolean isExistUser(String username) {
        OAuth2UserDO userDO = oauth2UserMapper.selectUserByUsername(username);
        return userDO == null;
    }

//    @Override
//    public CurrentUser getUserInfo() {
//        CurrentUser currentUser = new CurrentUser();
//        return currentUser;
//    }

    @Override
    @Transactional
    public Page<OAuth2UserDTO> getUserPage(Page<OAuth2UserDTO> page, String keyword) {
        return oauth2UserMapper.getUserPage(page, keyword);
    }

    @Override
    @Transactional
    public String addUser(OAuth2UserDTO oauth2UserDTO) {
        Assert.isNull(oauth2UserMapper.selectUserByUsername(oauth2UserDTO.getUsername()), "注册失败，该用户名已存在！");
        OAuth2UserDO userDO = new OAuth2UserDO();
        CustomBeanUtils.copyPropertiesExcludeMetaAndNull(oauth2UserDTO, userDO, "password");
        userDO.setPassword(PasswordUtils.encryption(oauth2UserDTO.getPassword()));
        oauth2UserMapper.insert(userDO);
        return userDO.getId();
    }

    @Override
    @Transactional
    public String updateUser(OAuth2UserDTO oauth2UserDTO) {
        Assert.notNull(oauth2UserDTO.getId(), "用户id不能为空");
        OAuth2UserDO userDO = oauth2UserMapper.selectById(oauth2UserDTO.getId());
        Assert.notNull(userDO, "更新失败：没有找到该用户或已被删除");
        CustomBeanUtils.copyPropertiesExcludeMetaAndNull(oauth2UserDTO, userDO, "password");
        // 密码不为空则更新密码
        if (StringUtils.hasText(oauth2UserDTO.getPassword())) {
            userDO.setPassword(PasswordUtils.encryption(oauth2UserDTO.getPassword()));
        }
        oauth2UserMapper.updateById(userDO);
        return userDO.getId();
    }

    @Override
    @Transactional
    public String deleteUser(String userId) {
        oauth2UserMapper.deleteById(userId);
        return userId;
    }

    @Override
    @Transactional
    public List<String> batchDeleteUser(List<String> userIds) {
        Assert.notEmpty(userIds, "没有需要删除的用户");
        for (String userId : userIds) {
            this.deleteUser(userId);
        }
        return userIds;
    }

    @Override
    public Page<OAuth2UserDTO> getUserPageByRoleId(Page<OAuth2UserDTO> page, String roleId, String keyword) {
        Assert.notNull(roleId, "没有有效的角色ID！");
        return oauth2UserMapper.getUserPageByRoleId(page, roleId, keyword);
    }

    @Override
    public Page<OAuth2UserDTO> getUserPageByDepartmentId(Page<OAuth2UserDTO> page, String departmentId, String keyword) {
        Assert.notNull(departmentId, "没有有效的部门ID！");
        return oauth2UserMapper.getUserPageByDepartmentId(page, departmentId, keyword);
    }

    @Override
    public Boolean logout() {
//        String userId = UserDetailsUtils.getUserId();
        return true;
    }

    @Override
    @Transactional
    public void changePassword(ChangePasswordForm changePasswordForm) {
        String userId = SecurityUtils.getCurrentUserId();
        Assert.hasText(userId, "当前登陆状态不存在用户id");

        // 检验用户是否存在
        OAuth2UserDO userDO = oauth2UserMapper.selectById(userId);
        Assert.notNull(userDO, "用户不存在");

        String encodePassword = userDO.getPassword();
        boolean matches = passwordEncoder.matches(changePasswordForm.getOldPassword(), encodePassword);
        Assert.isTrue(matches, "原密码不正确");

        encodePassword = passwordEncoder.encode(changePasswordForm.getNewPassword());
        userDO.setPassword(encodePassword);
        oauth2UserMapper.updateById(userDO);
    }

}
