package org.shanzhaozhen.uaa.service;

import org.shanzhaozhen.uaa.dto.PermissionDTO;

import java.util.List;

public interface PermissionService {

    /**
     * 通过 PermissionType 类型获取所有的Permission（多对多含有角色信息）
     * @param type
     * @return
     */
    List<PermissionDTO> getPermissionRoleListByType(Integer type);

    /**
     * 获取所有权限的树形结构
     * @return
     */
    List<PermissionDTO> getPermissionTreeByType(Integer type);

    /**
     * 通过权限id获取权限实体
     * @param permissionId
     * @return
     */
    PermissionDTO getPermissionById(Long permissionId);

    /**
     * 增加权限
     * @param permissionDTO
     * @return
     */
    Long addPermission(PermissionDTO permissionDTO);

    /**
     * 修改权限
     * @param permissionDTO
     * @return
     */
    Long updatePermission(PermissionDTO permissionDTO);

    /**
     * 删除权限(通过权限id删除)
     * @param permissionId
     * @return
     */
    Long deletePermission(Long permissionId);

    /**
     * 批量删除权限(通过权限id删除)
     * @param permissionIds
     * @return
     */
    List<Long> batchDeletePermission(List<Long> permissionIds);
}
