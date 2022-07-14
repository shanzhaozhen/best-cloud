package org.shanzhaozhen.uaa.service;

import java.util.List;

public interface DepartmentUserService {

    /**
     * 添加部门和用户关联
     * @param departmentId
     * @param userId
     * @return
     */
    String addDepartmentUser(String departmentId, String userId);

    /**
     * 批量添加部门和用户关联
     * @param departmentId
     * @param userIds
     * @return
     */
    List<String> batchAddDepartmentUser(String departmentId, List<String> userIds);

    /**
     * 删除部门和用户关联
     * @param departmentId
     * @param userId
     * @return
     */
    void deleteDepartmentUser(String departmentId, String userId);

    /**
     * 批量删除部门和用户关联
     * @param departmentId
     * @param userIds
     * @return
     */
    int batchDeleteDepartmentUser(String departmentId, List<String> userIds);

    /**
     * 通过部门ID删除对应部门与用户的关联关系
     * @param departmentId
     */
    void deleteDepartmentUserByDepartmentId(String departmentId);

}
