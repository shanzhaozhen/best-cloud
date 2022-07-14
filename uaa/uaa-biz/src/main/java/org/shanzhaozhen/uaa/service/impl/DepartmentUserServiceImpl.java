package org.shanzhaozhen.uaa.service.impl;

import lombok.RequiredArgsConstructor;
import org.shanzhaozhen.uaa.mapper.DepartmentUserMapper;
import org.shanzhaozhen.uaa.pojo.entity.DepartmentUserDO;
import org.shanzhaozhen.uaa.service.DepartmentUserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DepartmentUserServiceImpl implements DepartmentUserService {

    private final DepartmentUserMapper departmentUserMapper;

    @Override
    @Transactional
    public String addDepartmentUser(String departmentId, String userId) {
        Assert.notNull(departmentId, "没有有效的部门ID！");
        Assert.notNull(userId, "没有有效的用户ID！");
        // 先检查该用户是否已存在部门，若存在先删除
        DepartmentUserDO departmentUserDO = this.departmentUserMapper.getDepartmentUserByUserId(userId);
        if (departmentUserDO != null) {
            departmentUserMapper.deleteById(departmentUserDO.getId());
        }
        departmentUserDO = new DepartmentUserDO(null, departmentId, userId);
        this.departmentUserMapper.insert(departmentUserDO);
        return departmentUserDO.getId();
    }

    @Override
    public List<String> batchAddDepartmentUser(String departmentId, List<String> userIds) {
        Assert.notNull(departmentId, "没有有效的部门ID！");
        Assert.notEmpty(userIds, "没有有效的用户ID！");
        List<String> departmentUserIds = new ArrayList<>();
        for (String userId : userIds) {
            departmentUserIds.add(this.addDepartmentUser(departmentId, userId));
        }
        return departmentUserIds;
    }

    @Override
    @Transactional
    public void deleteDepartmentUser(String departmentId, String userId) {
        Assert.notNull(departmentId, "没有有效的部门ID！");
        Assert.notNull(userId, "没有有效的用户ID！");
        departmentUserMapper.deleteByDepartmentIdAndUserId(departmentId, userId);
    }

    @Override
    public int batchDeleteDepartmentUser(String departmentId, List<String> userIds) {
        Assert.notEmpty(userIds, "没有有效的用户ID！");
        Assert.notNull(departmentId, "没有有效的用户ID！");
        for (String userId : userIds) {
            this.deleteDepartmentUser(departmentId, userId);
        }
        return userIds.size();
    }

}
