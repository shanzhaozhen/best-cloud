package org.shanzhaozhen.uaa.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.shanzhaozhen.uaa.pojo.entity.UserRoleDO;
import org.shanzhaozhen.uaa.pojo.dto.UserDTO;
import org.shanzhaozhen.uaa.mapper.UserRoleMapper;
import lombok.RequiredArgsConstructor;
import org.shanzhaozhen.uaa.service.UserRoleService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserRoleServiceImpl implements UserRoleService {

    private final UserRoleMapper userRoleMapper;

    @Override
    @Transactional
    public Long addUserRole(Long userId, Long roleId) {
        Assert.notNull(userId, "没有有效的用户ID！");
        Assert.notNull(roleId, "没有有效的角色ID！");
        // 先检查是否存在
        UserRoleDO userRoleDO = this.userRoleMapper.getUserRoleByUserIdAndRoleId(userId, roleId);
        if (null == userRoleDO) {
            userRoleDO = new UserRoleDO(null, userId, roleId);
            this.userRoleMapper.insert(userRoleDO);
        }
        return userRoleDO.getId();
    }

    @Override
    @Transactional
    public List<Long> batchAddUserRole(List<Long> userIds, Long roleId) {
        Assert.notEmpty(userIds, "没有有效的用户ID！");
        Assert.notNull(roleId, "没有有效的角色ID！");
        List<Long> userRoleIds = new ArrayList<>();
        for (Long userId : userIds) {
            userRoleIds.add(this.addUserRole(userId, roleId));
        }
        return userRoleIds;
    }

    @Override
    public List<Long> batchAddUserRole(Long userId, List<Long> roleIds) {
        Assert.notNull(userId, "没有有效的用户ID！");
        Assert.notEmpty(roleIds, "没有有效的角色ID！");
        List<Long> userRoleIds = new ArrayList<>();
        for (Long roleId : roleIds) {
            userRoleIds.add(this.addUserRole(userId, roleId));
        }
        return userRoleIds;
    }

    @Override
    @Transactional
    public void deleteUserRole(Long userId, Long roleId) {
        Assert.notNull(userId, "没有有效的用户ID！");
        Assert.notNull(roleId, "没有有效的角色ID！");
        userRoleMapper.deleteByUserIdAndRoleId(userId, roleId);
    }

    @Override
    @Transactional
    public int batchDeleteUserRole(List<Long> userIds, Long roleId) {
        Assert.notEmpty(userIds, "没有有效的用户ID！");
        Assert.notNull(roleId, "没有有效的角色ID！");
        for (Long userId : userIds) {
            this.deleteUserRole(userId, roleId);
        }
        return userIds.size();
    }

}
