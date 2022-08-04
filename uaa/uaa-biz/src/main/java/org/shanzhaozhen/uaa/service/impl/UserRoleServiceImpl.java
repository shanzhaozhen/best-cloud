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
    public String addUserRole(String userId, String roleId) {
        Assert.notNull(userId, "没有有效的用户ID！");
        Assert.notNull(roleId, "没有有效的角色ID！");
        // 先检查是否存在
        UserRoleDO userRoleDO = this.userRoleMapper.getUserRoleByUserIdAndRoleId(userId, roleId);
        if (null == userRoleDO) {
            userRoleDO = new UserRoleDO(null, userId, roleId, null);
            this.userRoleMapper.insert(userRoleDO);
        }
        return userRoleDO.getId();
    }

    @Override
    @Transactional
    public List<String> batchAddUserRole(List<String> userIds, String roleId) {
        Assert.notEmpty(userIds, "没有有效的用户ID！");
        Assert.notNull(roleId, "没有有效的角色ID！");
        List<String> userRoleIds = new ArrayList<>();
        for (String userId : userIds) {
            userRoleIds.add(this.addUserRole(userId, roleId));
        }
        return userRoleIds;
    }

    @Override
    public List<String> batchAddUserRole(String userId, List<String> roleIds) {
        Assert.notNull(userId, "没有有效的用户ID！");
        Assert.notEmpty(roleIds, "没有有效的角色ID！");
        List<String> userRoleIds = new ArrayList<>();
        for (String roleId : roleIds) {
            userRoleIds.add(this.addUserRole(userId, roleId));
        }
        return userRoleIds;
    }

    @Override
    @Transactional
    public void deleteUserRole(String userId, String roleId) {
        Assert.notNull(userId, "没有有效的用户ID！");
        Assert.notNull(roleId, "没有有效的角色ID！");
        userRoleMapper.deleteByUserIdAndRoleId(userId, roleId);
    }

    @Override
    @Transactional
    public int batchDeleteUserRole(List<String> userIds, String roleId) {
        Assert.notEmpty(userIds, "没有有效的用户ID！");
        Assert.notNull(roleId, "没有有效的角色ID！");
        for (String userId : userIds) {
            this.deleteUserRole(userId, roleId);
        }
        return userIds.size();
    }

}
