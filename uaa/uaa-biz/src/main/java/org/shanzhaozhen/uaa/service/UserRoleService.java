package org.shanzhaozhen.uaa.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.shanzhaozhen.uaa.pojo.dto.UserDTO;

import java.util.List;

public interface UserRoleService {

    /**
     * 添加用户和角色关联
     * @param userId
     * @param roleId
     * @return
     */
    String addUserRole(String userId, String roleId);

    /**
     * 批量添加用户和角色关联
     * @param userIds
     * @param roleId
     * @return
     */
    List<String> batchAddUserRole(List<String> userIds, String roleId);

    /**
     * 批量添加用户和角色关联
     * @param userId
     * @param roleIds
     * @return
     */
    List<String> batchAddUserRole(String userId, List<String> roleIds);

    /**
     * 删除用户和角色关联
     * @param userId
     * @param roleId
     * @return
     */
    void deleteUserRole(String userId, String roleId);

    /**
     * 批量删除用户和角色关联
     * @param userIds
     * @param roleId
     * @return
     */
    int batchDeleteUserRole(List<String> userIds, String roleId);
}
