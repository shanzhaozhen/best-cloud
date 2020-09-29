package org.shanzhaozhen.basicservice.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.shanzhaozhen.basiccommon.converter.RoleConverter;
import org.shanzhaozhen.basiccommon.domain.sys.UserDO;
import org.shanzhaozhen.basiccommon.domain.sys.UserRoleDO;
import org.shanzhaozhen.basiccommon.dto.JWTUser;
import org.shanzhaozhen.basiccommon.form.BaseSearchForm;
import org.shanzhaozhen.basiccommon.utils.CustomBeanUtils;
import org.shanzhaozhen.basiccommon.vo.UserInfo;
import org.shanzhaozhen.basiccommon.dto.UserDTO;
import org.shanzhaozhen.basicrepo.mapper.UserMapper;
import org.shanzhaozhen.basicrepo.mapper.UserRoleMapper;
import org.shanzhaozhen.basicservice.service.RouteService;
import org.shanzhaozhen.basicservice.service.UserService;
import org.shanzhaozhen.basiccommon.utils.PasswordUtils;
import org.shanzhaozhen.basiccommon.utils.UserDetailsUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
@CacheConfig(cacheNames = "user")
public class UserServiceImpl implements UserService {

    private final UserMapper userMapper;

    private final RouteService routeService;

    private final UserRoleMapper userRoleMapper;

    public UserServiceImpl(RouteService routeService, UserMapper userMapper, UserRoleMapper userRoleMapper) {
        this.routeService = routeService;
        this.userMapper = userMapper;
        this.userRoleMapper = userRoleMapper;
    }

    @Override
    public UserDTO getUserById(Long userId) {
        return userMapper.getUserAndRolesByUserId(userId);
    }

    @Override
    public UserDTO getUserByUsername(String username) {
        return userMapper.getUserByUsername(username);
    }

    @Override
    @Cacheable(key = "#root.methodName + ':' + #userId")
    public JWTUser getJWTUser(Long userId) {
        return userMapper.getJWTUserByUserId(userId);
    }

    @Override
    public UserDTO getCurrentUser() {
        UserDTO userDTO = userMapper.getUserAndRolesByUserId(UserDetailsUtils.getUserId());
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
    public UserInfo getUserInfo() {
        UserDTO userDTO = this.getCurrentUser();
        return UserInfo.builder()
                .nickname(userDTO.getNickname())
                .avatar(userDTO.getAvatar())
                .introduction(userDTO.getIntroduction())
                .roles(RoleConverter.toBase(userDTO.getRoles()))
                .asyncRoutes(routeService.getRoutesByCurrentUser())
                .build();
    }

    @Override
    @Transactional
    public Page<UserDTO> getUserPage(BaseSearchForm<UserDTO> baseSearchForm) {
        return userMapper.getUserPage(baseSearchForm.getPage(), baseSearchForm.getKeyword());
    }

    @Override
    @Transactional
    public Long addUser(UserDTO userDTO) {
        Assert.isNull(userMapper.selectUserByUsername(userDTO.getUsername()), "注册失败，该用户名已存在！");
        UserDO userDO = new UserDO();
        CustomBeanUtils.copyPropertiesExcludeMeta(userDTO, userDO, "password");
        userDO.setPassword(PasswordUtils.encryption(userDTO.getPassword()));
        userMapper.insert(userDO);
        bathAddUserRole(userDO.getId(), userDTO.getRoleIds());
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
        bathAddUserRole(userDO.getId(), userDTO.getRoleIds());
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
    public void bathAddUserRole(Long userId, List<Long> roleIds) {
        for (Long roleId : roleIds) {
            UserRoleDO userRoleDO = new UserRoleDO(null, userId, roleId);
            userRoleMapper.insert(userRoleDO);
        }
    }

}
