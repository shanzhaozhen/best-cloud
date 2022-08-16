package org.shanzhaozhen.uaa.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.shanzhaozhen.uaa.pojo.dto.RoleAuthorizedData;
import org.shanzhaozhen.uaa.pojo.dto.RoleDTO;

import javax.validation.constraints.NotNull;
import java.util.List;

public interface RoleService {

    /**
     * 通过用户id获取用户角色
     *
     * @param userId
     * @return
     */
    List<RoleDTO> getRolesByUserId(String userId);

    /**
     * 获取角色的分页列表
     *
     * @param page
     * @param keyword
     * @return
     */
    Page<RoleDTO> getRolePage(Page<RoleDTO> page, String keyword);

    /**
     * 获取所有角色
     *
     * @return
     */
    List<RoleDTO> getAllRoles();

    /**
     * 通过角色id获取
     *
     * @param roleId
     * @return
     */
    RoleDTO getRoleById(String roleId);

    /**
     * 通过角色id获取（细节）
     *
     * @param roleId
     * @return
     */
    RoleDTO getRoleDetailById(String roleId);

    /**
     * 新增角色
     *
     * @param roleDTO
     * @return
     */
    String addRole(RoleDTO roleDTO);

    /**
     * 修改角色
     *
     * @param roleDTO
     * @return
     */
    String updateRole(RoleDTO roleDTO);

    /**
     * 删除角色(通过角色id删除)
     *
     * @param roleId
     * @return
     */
    String deleteRole(String roleId);

    /**
     * 批量删除角色(通过角色id删除)
     *
     * @param roleIds
     * @return
     */
    List<String> batchDeleteRole(List<String> roleIds);

    /**
     * 更新角色与菜单和权限的关系表
     *
     * @param roleId
     * @param menuIds
     */
    void updateMenuAndPermission(@NotNull String roleId, List<String> menuIds, List<String> permissionIds);

    /**
     * 批量插入角色-菜单关系表
     *
     * @param roleId
     * @param menuIds
     */
    void batchAddRoleMenu(String roleId, List<String> menuIds);

    /**
     * 批量插入角色-权限关系表
     *
     * @param roleId
     * @param permissionIds
     */
    void batchAddRolePermission(String roleId, List<String> permissionIds);

    /**
     * 通过角色ID获取角色授权信息
     *
     * @param roleId
     * @return
     */
    RoleAuthorizedData getRoleAuthorizedData(String roleId);

    /**
     * 更新角色授权信息
     * @param roleAuthorizedData
     * @return
     */
    void updateRoleAuthorizedData(RoleAuthorizedData roleAuthorizedData);

}
