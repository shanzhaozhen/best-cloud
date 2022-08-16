package org.shanzhaozhen.uaa.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.shanzhaozhen.common.core.utils.CustomBeanUtils;
import org.shanzhaozhen.uaa.converter.RoleConverter;
import org.shanzhaozhen.uaa.pojo.dto.RoleAuthorizedData;
import org.shanzhaozhen.uaa.pojo.entity.RoleDO;
import org.shanzhaozhen.uaa.pojo.entity.RoleMenuDO;
import org.shanzhaozhen.uaa.pojo.entity.RolePermissionDO;
import org.shanzhaozhen.uaa.pojo.dto.RoleDTO;
import org.shanzhaozhen.uaa.mapper.RolePermissionMapper;
import org.shanzhaozhen.uaa.service.PermissionService;
import org.shanzhaozhen.uaa.service.RoleService;
import org.shanzhaozhen.uaa.mapper.RoleMapper;
import org.shanzhaozhen.uaa.mapper.RoleMenuMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleMapper roleMapper;
    private final RoleMenuMapper roleMenuMapper;
    private final RolePermissionMapper rolePermissionMapper;
    private final PermissionService permissionService;

    @Override
    public List<RoleDTO> getRolesByUserId(String userId) {
        return roleMapper.getRoleListByUserId(userId);
    }

    @Override
    public Page<RoleDTO> getRolePage(Page<RoleDTO> page, String keyword) {
        return roleMapper.getRolePage(page, keyword);
    }

    @Override
    public List<RoleDTO> getAllRoles() {
        return roleMapper.getAllRoles();
    }

    @Override
    public RoleDTO getRoleById(String roleId) {
        RoleDO roleDO = roleMapper.selectById(roleId);
        Assert.notNull(roleDO, "获取失败：没有找到该角色或已被删除");
        return RoleConverter.toDTO(roleDO);
    }

    @Override
    public RoleDTO getRoleDetailById(String roleId) {
        RoleDTO roleDTO = roleMapper.getRoleDetailById(roleId);
        Assert.notNull(roleDTO, "获取失败：没有找到该角色或已被删除");
        return roleDTO;
    }

    @Override
    @Transactional
    public String addRole(RoleDTO roleDTO) {
        RoleDTO roleInDB = roleMapper.getRoleByCode(roleDTO.getCode());
        Assert.isNull(roleInDB, "创建失败：角色编码已被占用");
        RoleDO roleDO = RoleConverter.toDO(roleDTO);
        roleMapper.insert(roleDO);
        return roleDO.getId();
    }

    @Override
    @Transactional
    public String updateRole(RoleDTO roleDTO) {
        Assert.notNull(roleDTO.getId(), "角色id不能为空");
        RoleDTO roleInDB = roleMapper.getRoleByCode(roleDTO.getCode());
        Assert.isTrue(roleInDB == null || roleInDB.getId().equals(roleDTO.getId()), "更新失败：角色编码已被占用");
        RoleDO roleDO = roleMapper.selectById(roleDTO.getId());
        Assert.notNull(roleDO, "更新失败：没有找到该角色或已被删除");
        CustomBeanUtils.copyPropertiesExcludeMetaAndNull(roleDTO, roleDO);
        roleMapper.updateById(roleDO);
        return roleDO.getId();
    }

    @Override
    @Transactional
    public String deleteRole(String roleId) {
        roleMenuMapper.deleteByRoleId(roleId);
        rolePermissionMapper.deleteByRoleId(roleId);
        roleMapper.deleteById(roleId);
        permissionService.refreshPermissionCache();
        return roleId;
    }

    @Override
    @Transactional
    public List<String> batchDeleteRole(@NotEmpty(message = "没有需要删除的角色") List<String> roleIds) {
        for (String roleId : roleIds) {
            this.deleteRole(roleId);
        }
        return roleIds;
    }

    @Override
    @Transactional
    public void updateMenuAndPermission(@NotNull String roleId, List<String> menuIds, List<String> permissionIds) {
        // 添加角色-菜单关联
        roleMenuMapper.deleteByRoleId(roleId);
        if (menuIds != null && menuIds.size() > 0) {
            this.batchAddRoleMenu(roleId, menuIds);
        }
        // 添加角色-权限关联
        rolePermissionMapper.deleteByRoleId(roleId);
        if (permissionIds != null && permissionIds.size() > 0) {
            this.batchAddRolePermission(roleId, permissionIds);
        }
    }

    @Override
    @Transactional
    public void batchAddRoleMenu(String roleId, List<String> menuIds) {
        for (String menuId : menuIds) {
            roleMenuMapper.insert(new RoleMenuDO(null, roleId, menuId));
        }
    }

    @Override
    @Transactional
    public void batchAddRolePermission(String roleId, List<String> permissionIds) {
        for (String permissionId : permissionIds) {
            rolePermissionMapper.insert(new RolePermissionDO(null, roleId, permissionId));
        }
    }

    @Override
    public RoleAuthorizedData getRoleAuthorizedData(String roleId) {
        Assert.hasText(roleId, "角色ID不能为空");
        RoleDO roleDO = this.roleMapper.selectById(roleId);
        Assert.notNull(roleDO, "没有找到对应的角色");

        List<String> permissionIds = rolePermissionMapper.getPermissionIdsByRoleId(roleId);
        List<String> menuIds = roleMenuMapper.getMenuIdsByRoleId(roleId);

        return RoleAuthorizedData.builder()
                .roleId(roleId)
                .permissionIds(permissionIds)
                .menuIds(menuIds)
                .build();
    }

    @Override
    @Transactional
    public void updateRoleAuthorizedData(RoleAuthorizedData roleAuthorizedData) {
        Assert.notNull(roleAuthorizedData, "没有接收到有限得角色关联信息");
        String roleId = roleAuthorizedData.getRoleId();
        Assert.notNull(roleId, "角色ID不能为空");
        // 检查角色是否存在
        RoleDO roleDO = this.roleMapper.selectById(roleAuthorizedData.getRoleId());
        Assert.notNull(roleDO, "角色不存在");
        this.updateMenuAndPermission(roleId, roleAuthorizedData.getMenuIds(), roleAuthorizedData.getPermissionIds());
        permissionService.refreshPermissionCache();
    }

}
