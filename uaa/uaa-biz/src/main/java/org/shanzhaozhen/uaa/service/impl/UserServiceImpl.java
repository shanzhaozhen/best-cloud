package org.shanzhaozhen.uaa.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.shanzhaozhen.common.core.utils.CustomBeanUtils;
import org.shanzhaozhen.common.core.utils.PasswordUtils;
import org.shanzhaozhen.common.web.utils.JwtUtils;
import org.shanzhaozhen.uaa.converter.UserInfoConverter;
import org.shanzhaozhen.uaa.pojo.dto.UserInfoDTO;
import org.shanzhaozhen.uaa.service.RoleService;
import org.shanzhaozhen.uaa.pojo.entity.UserDO;
import org.shanzhaozhen.uaa.pojo.dto.JWTUser;
import org.shanzhaozhen.uaa.pojo.dto.UserDTO;
import org.shanzhaozhen.uaa.service.UserInfoService;
import org.shanzhaozhen.uaa.service.UserRoleService;
import org.shanzhaozhen.uaa.service.UserService;
import org.shanzhaozhen.uaa.mapper.UserMapper;
import org.shanzhaozhen.uaa.mapper.UserRoleMapper;
import lombok.RequiredArgsConstructor;
import org.shanzhaozhen.uaa.pojo.vo.CurrentUser;
import org.springframework.beans.BeanUtils;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
@RequiredArgsConstructor
@CacheConfig(cacheNames = "user")
public class UserServiceImpl implements UserService {

    private final RoleService roleService;
    private final UserRoleService userRoleService;
    private final UserInfoService userInfoService;
    private final UserMapper userMapper;
//    private final MenuService menuService;
    private final UserRoleMapper userRoleMapper;

    @Override
    public UserDTO getUserById(String userId) {
        UserDTO user = userMapper.getUserById(userId);
        Assert.notNull(user, "用户不存在");
        UserInfoDTO userInfo = userInfoService.getUserInfoByUserId(userId);
        user.setUserInfo(userInfo);
        return user;
    }

    @Override
    public UserDTO getUserByUsername(String username) {
        UserDTO user = userMapper.getUserByUsername(username);
        if (user != null) {
            user.setRoles(roleService.getRolesByUserId(user.getId()));
        }
        return user;
    }

    @Override
    @Cacheable(key = "#root.methodName + ':' + #userId")
    public JWTUser getJWTUser(String userId) {
        return userMapper.getJWTUserByUserId(userId);
    }

    @Override
    public UserDTO getCurrentUser() {
        String userId = JwtUtils.getUserIdWithoutError();
        Assert.notNull(userId, "请求头没有包含用户信息");
        UserDTO userDTO = userMapper.getUserAndRolesByUserId(userId);
        Assert.notNull(userDTO, "没有找到当前用户信息");
        return userDTO;
    }

    @Override
    @Transactional
    public String register(UserDTO userDTO) {
        Assert.isNull(userMapper.selectUserByUsername(userDTO.getUsername()), "注册失败，该用户名已存在！");
        UserDO newUser = new UserDO();
        BeanUtils.copyProperties(userDTO, newUser, "accountNonExpired", "accountNonLocked", "credentialsNonExpired", "enabled");
        newUser.setPassword(PasswordUtils.encryption(userDTO.getPassword()));
        userMapper.insert(newUser);
        return newUser.getId();
    }

    @Override
    public Boolean isExistUser(String username) {
        UserDO userDO = userMapper.selectUserByUsername(username);
        return userDO == null;
    }

    @Override
    public CurrentUser getUserInfo() {
        UserDTO userDTO = this.getCurrentUser();
        CurrentUser currentUser = new CurrentUser();

        // 获取用户信息
        UserInfoDTO userInfo = userInfoService.getUserInfoByUserId(userDTO.getId());
        if (userInfo != null) {
            currentUser.setUserInfo(UserInfoConverter.toVO(userInfo));
        }

        // todo: 获取用户角色

        // todo: 获取用户所拥有的的菜单

        return currentUser;

    }

    @Override
    @Transactional
    public Page<UserDTO> getUserPage(Page<UserDTO> page, String keyword) {
        return userMapper.getUserPage(page, keyword);
    }

    @Override
    @Transactional
    public String addUser(UserDTO userDTO) {
        Assert.isNull(userMapper.selectUserByUsername(userDTO.getUsername()), "注册失败，该用户名已存在！");
        UserDO userDO = new UserDO();
        CustomBeanUtils.copyPropertiesExcludeMetaAndNull(userDTO, userDO, "password");
        userDO.setPassword(PasswordUtils.encryption(userDTO.getPassword()));
        userMapper.insert(userDO);
        if (!CollectionUtils.isEmpty(userDTO.getRoleIds())) {
            userRoleService.batchAddUserRole(userDO.getId(), userDTO.getRoleIds());
        }
        return userDO.getId();
    }

    @Override
    @Transactional
    public String updateUser(UserDTO userDTO) {
        Assert.notNull(userDTO.getId(), "用户id不能为空");
        UserDO userDO = userMapper.selectById(userDTO.getId());
        Assert.notNull(userDO, "更新失败：没有找到该用户或已被删除");
        CustomBeanUtils.copyPropertiesExcludeMetaAndNull(userDTO, userDO, "password");
        // 密码不为空则更新密码
        if (StringUtils.hasText(userDTO.getPassword())) {
            userDO.setPassword(PasswordUtils.encryption(userDTO.getPassword()));
        }

        // 更新主用户
        userMapper.updateById(userDO);

        // 更新用户信息
        UserInfoDTO userInfo = userDTO.getUserInfo();
        if (userInfo != null) {
            userInfo.setPid(userDTO.getId());
            userInfoService.updateUserInfo(userInfo);
        }

        // 更新用户关联角色
//        userRoleMapper.deleteByUserId(userDO.getId());
//        List<String> roleIds = userDTO.getRoleIds();
//        if (!CollectionUtils.isEmpty(roleIds)) {
//            userRoleService.batchAddUserRole(userDO.getId(), roleIds);
//        }

        return userDO.getId();
    }

    @Override
    @Transactional
    public String deleteUser(String userId) {
        userRoleMapper.deleteByUserId(userId);
        userMapper.deleteById(userId);
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
    public Page<UserDTO> getUserPageByRoleId(Page<UserDTO> page, String roleId, String keyword) {
        Assert.notNull(roleId, "没有有效的角色ID！");
        return userMapper.getUserPageByRoleId(page, roleId, keyword);
    }

    @Override
    public Page<UserDTO> getUserPageByDepartmentId(Page<UserDTO> page, String departmentId, String keyword) {
        Assert.notNull(departmentId, "没有有效的部门ID！");
        return userMapper.getUserPageByDepartmentId(page, departmentId, keyword);
    }

    @Override
    public Boolean logout() {
//        String userId = UserDetailsUtils.getUserId();
        return true;
    }

}
