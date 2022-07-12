package org.shanzhaozhen.uaa.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.shanzhaozhen.common.core.utils.CustomBeanUtils;
import org.shanzhaozhen.uaa.converter.RoleConverter;
import org.shanzhaozhen.uaa.pojo.entity.RoleDO;
import org.shanzhaozhen.uaa.pojo.entity.RoleMenuDO;
import org.shanzhaozhen.uaa.pojo.entity.RolePermissionDO;
import org.shanzhaozhen.uaa.pojo.dto.RoleDTO;
import org.shanzhaozhen.uaa.mapper.RolePermissionMapper;
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

    @Override
    public List<RoleDTO> getRolesByUserId(Long userId) {
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
    public RoleDTO getRoleById(Long roleId) {
        RoleDO roleDO = roleMapper.selectById(roleId);
        Assert.notNull(roleDO, "获取失败：没有找到该角色或已被删除");
        return RoleConverter.toDTO(roleDO);
    }

    @Override
    public RoleDTO getRoleDetailById(Long roleId) {
        RoleDTO roleDTO = roleMapper.getRoleDetailById(roleId);
        Assert.notNull(roleDTO, "获取失败：没有找到该角色或已被删除");
        return roleDTO;
    }

    @Override
    @Transactional
    public Long addRole(RoleDTO roleDTO) {
        RoleDTO roleInDB = roleMapper.getRoleByCode(roleDTO.getCode());
        Assert.isNull(roleInDB, "创建失败：角色编码已被占用");
        RoleDO roleDO = RoleConverter.toDO(roleDTO);
        roleMapper.insert(roleDO);
        updateMenuAndPermission(roleDO.getId(), roleDTO.getMenuIds(), roleDTO.getPermissionIds());
        return roleDO.getId();
    }

    @Override
    @Transactional
    public Long updateRole(RoleDTO roleDTO) {
        Assert.notNull(roleDTO.getId(), "角色id不能为空");
        RoleDTO roleInDB = roleMapper.getRoleByCode(roleDTO.getCode());
        Assert.isTrue(roleInDB == null || roleInDB.getId().equals(roleDTO.getId()), "创建失败：字典代码已被占用");
        RoleDO roleDO = roleMapper.selectById(roleDTO.getId());
        Assert.notNull(roleDO, "更新失败：没有找到该角色或已被删除");
        CustomBeanUtils.copyPropertiesExcludeMeta(roleDTO, roleDO);
        roleMapper.updateById(roleDO);
        updateMenuAndPermission(roleDO.getId(), roleDTO.getMenuIds(), roleDTO.getPermissionIds());
        return roleDO.getId();
    }

    @Override
    @Transactional
    public Long deleteRole(Long roleId) {
        roleMenuMapper.deleteByRoleId(roleId);
        rolePermissionMapper.deleteByRoleId(roleId);
        roleMapper.deleteById(roleId);
        return roleId;
    }

    @Override
    @Transactional
    public List<Long> batchDeleteRole(@NotEmpty(message = "没有需要删除的角色") List<Long> roleIds) {
        for (Long roleId : roleIds) {
            this.deleteRole(roleId);
        }
        return roleIds;
    }

    @Override
    @Transactional
    public void updateMenuAndPermission(@NotNull Long roleId, List<Long> menuIds, List<Long> permissionIds) {
        // 添加角色-菜单关联
        if (menuIds != null && menuIds.size() > 0) {
            roleMenuMapper.deleteByRoleId(roleId);
            this.bathAddRoleMenu(roleId, menuIds);
        }
        // 添加角色-权限关联
        if (permissionIds != null && permissionIds.size() > 0) {
            rolePermissionMapper.deleteByRoleId(roleId);
            this.bathAddRolePermission(roleId, permissionIds);
        }
    }

    @Override
    @Transactional
    public void bathAddRoleMenu(Long roleId, List<Long> menuIds) {
        for (Long menuId : menuIds) {
            RoleMenuDO RoleMenuDO = new RoleMenuDO(null, roleId, menuId);
            roleMenuMapper.insert(RoleMenuDO);
        }
    }

    @Override
    @Transactional
    public void bathAddRolePermission(Long roleId, List<Long> permissionIds) {
        for (Long permissionId : permissionIds) {
            RolePermissionDO rolePermissionDO = new RolePermissionDO(null, roleId, permissionId);
            rolePermissionMapper.insert(rolePermissionDO);
        }
    }

}
