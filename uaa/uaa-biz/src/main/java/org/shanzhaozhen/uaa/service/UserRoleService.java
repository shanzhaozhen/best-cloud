package org.shanzhaozhen.uaa.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.shanzhaozhen.uaa.pojo.dto.UserDTO;

import java.util.List;

public interface UserRoleService {

    /**
     * 通过获取角色Id获取用户
     * @param page
     * @param roleId
     * @param keyword
     * @return
     */
    Page<UserDTO> getUserRolePage(Page<UserDTO> page, Long roleId, String keyword);

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
    List<Long> bathAddUserRole(List<Long> userIds, Long roleId);

    /**
     * 批量添加用户和角色关联
     * @param userId
     * @param roleIds
     * @return
     */
    List<Long> bathAddUserRole(Long userId, List<Long> roleIds);

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
