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
    public Long addDepartmentUser(Long departmentId, Long userId) {
        Assert.notNull(departmentId, "没有有效的部门ID！");
        Assert.notNull(userId, "没有有效的用户ID！");
        // 先检查是否存在
        DepartmentUserDO departmentUserDO = this.departmentUserMapper.getDepartmentUserByDepartmentIdAndUserId(userId, userId);
        if (null == departmentUserDO) {
            departmentUserDO = new DepartmentUserDO(null, userId, userId);
            this.departmentUserMapper.insert(departmentUserDO);
        }
        return departmentUserDO.getId();
    }

    @Override
    public List<Long> bathAddDepartmentUser(Long departmentId, List<Long> userIds) {
        Assert.notNull(departmentId, "没有有效的部门ID！");
        Assert.notEmpty(userIds, "没有有效的用户ID！");
        List<Long> departmentUserIds = new ArrayList<>();
        for (Long userId : userIds) {
            departmentUserIds.add(this.addDepartmentUser(userId, userId));
        }
        return departmentUserIds;
    }

    @Override
    @Transactional
    public void deleteDepartmentUser(Long departmentId, Long userId) {
        Assert.notNull(departmentId, "没有有效的部门ID！");
        Assert.notNull(userId, "没有有效的用户ID！");
        departmentUserMapper.deleteByDepartmentIdAndUserId(departmentId, userId);
    }

    @Override
    public int deleteDepartmentUser(Long departmentId, List<Long> userIds) {
        Assert.notEmpty(userIds, "没有有效的用户ID！");
        Assert.notNull(departmentId, "没有有效的用户ID！");
        for (Long userId : userIds) {
            this.deleteDepartmentUser(departmentId, userId);
        }
        return userIds.size();
    }

}
