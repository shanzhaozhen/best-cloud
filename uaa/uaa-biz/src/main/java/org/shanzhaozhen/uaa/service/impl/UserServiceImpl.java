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
import org.shanzhaozhen.uaa.pojo.form.UserDepartmentForm;
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
    public UserDTO getUserById(Long userId) {
        return userMapper.getUserAndRolesByUserId(userId);
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
    public JWTUser getJWTUser(Long userId) {
        return userMapper.getJWTUserByUserId(userId);
    }

    @Override
    public UserDTO getCurrentUser() {
        Long userId = JwtUtils.getUserIdWithoutError();
        Assert.notNull(userId, "请求头没有包含用户信息");
        UserDTO userDTO = userMapper.getUserAndRolesByUserId(userId);
        Assert.notNull(userDTO, "没有找到当前用户信息");
        return userDTO;
    }

    @Override
    @Transactional
    public Long register(UserDTO userDTO) {
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
    public Long addUser(UserDTO userDTO) {
        Assert.isNull(userMapper.selectUserByUsername(userDTO.getUsername()), "注册失败，该用户名已存在！");
        UserDO userDO = new UserDO();
        CustomBeanUtils.copyPropertiesExcludeMeta(userDTO, userDO, "password");
        userDO.setPassword(PasswordUtils.encryption(userDTO.getPassword()));
        userMapper.insert(userDO);
        if (!CollectionUtils.isEmpty(userDTO.getRoleIds())) {
            userRoleService.bathAddUserRole(userDO.getId(), userDTO.getRoleIds());
        }
        return userDO.getId();
    }

    @Override
    @Transactional
    public Long updateUser(UserDTO userDTO) {
        Assert.notNull(userDTO.getId(), "用户id不能为空");
        UserDO userDO = userMapper.selectById(userDTO.getId());
        Assert.notNull(userDO, "更新失败：没有找到该用户或已被删除");
        CustomBeanUtils.copyPropertiesExcludeMeta(userDTO, userDO, "password");
        if (StringUtils.hasText(userDTO.getPassword())) {
            userDO.setPassword(PasswordUtils.encryption(userDTO.getPassword()));
        }
        userMapper.updateById(userDO);
        userRoleMapper.deleteByUserId(userDO.getId());
        userRoleService.bathAddUserRole(userDO.getId(), userDTO.getRoleIds());
        return userDO.getId();
    }

    @Override
    @Transactional
    public Long deleteUser(Long userId) {
        userRoleMapper.deleteByUserId(userId);
        userMapper.deleteById(userId);
        return userId;
    }

    @Override
    @Transactional
    public List<Long> batchDeleteUser(List<Long> userIds) {
        Assert.notEmpty(userIds, "没有需要删除的用户");
        for (Long userId : userIds) {
            this.deleteUser(userId);
        }
        return userIds;
    }

    @Override
    public Page<UserDTO> getUserPageByRoleId(Page<UserDTO> page, Long roleId, String keyword) {
        Assert.notNull(roleId, "没有有效的角色ID！");
        return userMapper.getUserPageByRoleId(page, roleId, keyword);
    }

    @Override
    public Page<UserDTO> getUserPageByDepartmentId(Page<UserDTO> page, Long departmentId, String keyword) {
        Assert.notNull(departmentId, "没有有效的部门ID！");
        return userMapper.getUserPageByDepartmentId(page, departmentId, keyword);
    }

    @Override
    @Transactional
    public Long updateUserDepartment(Long userId, Long departmentId) {
        UserDO userDO = userMapper.selectById(userId);
        Assert.notNull(userDO, "没有找到对应的用户");
        userMapper.updateById(userDO);
        return userDO.getId();
    }

    @Override
    @Transactional
    public List<Long> batchUpdateUserDepartment(UserDepartmentForm userDepartmentForm) {
        List<Long> userIds = userDepartmentForm.getUserIds();
        Long departmentId = userDepartmentForm.getDepartmentId();
        for (Long userId : userIds) {
            this.updateUserDepartment(userId, departmentId);
        }
        return userIds;
    }

    @Override
    public Boolean logout() {
//        Long userId = UserDetailsUtils.getUserId();
        return true;
    }

}
