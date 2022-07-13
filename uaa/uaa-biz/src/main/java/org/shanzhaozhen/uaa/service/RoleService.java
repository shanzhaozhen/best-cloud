package org.shanzhaozhen.uaa.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.shanzhaozhen.uaa.pojo.dto.RoleDTO;

import javax.validation.constraints.NotNull;
import java.util.List;

public interface RoleService {

    /**
     * 通过用户id获取用户角色
     * @param userId
     * @return
     */
    List<RoleDTO> getRolesByUserId(Long userId);

    /**
     * 获取角色的分页列表
     * @param page
     * @param keyword
     * @return
     */
    Page<RoleDTO> getRolePage(Page<RoleDTO> page, String keyword);

    /**
     * 获取所有角色
     * @return
     */
    List<RoleDTO> getAllRoles();

    /**
     * 通过角色id获取
     * @param roleId
     * @return
     */
    RoleDTO getRoleById(Long roleId);

    /**
     * 通过角色id获取（细节）
     * @param roleId
     * @return
     */
    RoleDTO getRoleDetailById(Long roleId);

    /**
     * 新增角色
     * @param roleDTO
     * @return
     */
    Long addRole(RoleDTO roleDTO);

    /**
     * 修改角色
     * @param roleDTO
     * @return
     */
    Long updateRole(RoleDTO roleDTO);

    /**
     * 删除角色(通过角色id删除)
     * @param roleId
     * @return
     */
    Long deleteRole(Long roleId);

    /**
     * 批量删除角色(通过角色id删除)
     * @param roleIds
     * @return
     */
    List<Long> batchDeleteRole(List<Long> roleIds);

    /**
     * 更新角色与菜单和权限的关系表
     * @param roleId
     * @param menuIds
     */
    void updateMenuAndPermission(@NotNull Long roleId, List<Long> menuIds, List<Long> permissionIds);

    /**
     * 批量插入角色-菜单关系表
     * @param roleId
     * @param menuIds
     */
    void batchAddRoleMenu(Long roleId, List<Long> menuIds);

    /**
     * 批量插入角色-权限关系表
     * @param roleId
     * @param permissionIds
     */
    void batchAddRolePermission(Long roleId, List<Long> permissionIds);

}
