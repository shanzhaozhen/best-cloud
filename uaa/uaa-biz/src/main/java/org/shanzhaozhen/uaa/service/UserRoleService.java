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
    Long addUserRole(Long userId, Long roleId);

    /**
     * 批量添加用户和角色关联
     * @param userIds
     * @param roleId
     * @return
     */
    List<Long> batchAddUserRole(List<Long> userIds, Long roleId);

    /**
     * 批量添加用户和角色关联
     * @param userId
     * @param roleIds
     * @return
     */
    List<Long> batchAddUserRole(Long userId, List<Long> roleIds);

    /**
     * 删除用户和角色关联
     * @param userId
     * @param roleId
     * @return
     */
    void deleteUserRole(Long userId, Long roleId);

    /**
     * 批量删除用户和角色关联
     * @param userIds
     * @param roleId
     * @return
     */
    int batchDeleteUserRole(List<Long> userIds, Long roleId);
}
