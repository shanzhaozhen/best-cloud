package org.shanzhaozhen.uaa.service;

import java.util.List;

public interface DepartmentUserService {

    /**
     * 添加部门和用户关联
     * @param departmentId
     * @param userId
     * @return
     */
    Long addDepartmentUser(Long departmentId, Long userId);

    /**
     * 批量添加部门和用户关联
     * @param departmentId
     * @param userIds
     * @return
     */
    List<Long> batchAddDepartmentUser(Long departmentId, List<Long> userIds);

    /**
     * 删除部门和用户关联
     * @param departmentId
     * @param userId
     * @return
     */
    void deleteDepartmentUser(Long departmentId, Long userId);

    /**
     * 批量删除部门和用户关联
     * @param departmentId
     * @param userIds
     * @return
     */
    int batchDeleteDepartmentUser(Long departmentId, List<Long> userIds);

}
