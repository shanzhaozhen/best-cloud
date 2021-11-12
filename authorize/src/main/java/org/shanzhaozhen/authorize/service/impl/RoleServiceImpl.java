package org.shanzhaozhen.authorize.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.shanzhaozhen.basiccommon.converter.RoleConverter;
import org.shanzhaozhen.basiccommon.domain.sys.RoleDO;
import org.shanzhaozhen.basiccommon.domain.sys.RoleResourceDO;
import org.shanzhaozhen.basiccommon.domain.sys.RoleMenuDO;
import org.shanzhaozhen.basiccommon.dto.RoleDTO;
import org.shanzhaozhen.basiccommon.utils.CustomBeanUtils;
import org.shanzhaozhen.authorize.mapper.RoleMapper;
import org.shanzhaozhen.authorize.mapper.RoleResourceMapper;
import org.shanzhaozhen.authorize.mapper.RoleMenuMapper;
import org.shanzhaozhen.authorize.service.RoleService;
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

    private final RoleResourceMapper roleResourceMapper;

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
        Assert.isNull(roleInDB, "创建失败：角色代码已被占用");
        RoleDO roleDO = RoleConverter.toDO(roleDTO);
        roleMapper.insert(roleDO);
        updateMenuAndResource(roleDO.getId(), roleDTO.getMenuIds(), roleDTO.getResourceIds());
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
        updateMenuAndResource(roleDO.getId(), roleDTO.getMenuIds(), roleDTO.getResourceIds());
        return roleDO.getId();
    }

    @Override
    @Transactional
    public Long deleteRole(Long roleId) {
        roleMenuMapper.deleteByRoleId(roleId);
        roleResourceMapper.deleteByRoleId(roleId);
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
    public void updateMenuAndResource(@NotNull Long roleId, List<Long> menuIds, List<Long> resourceIds) {
        // 添加角色-菜单关联
        if (menuIds != null && menuIds.size() > 0) {
            roleMenuMapper.deleteByRoleId(roleId);
            this.bathAddRoleMenu(roleId, menuIds);
        }
        // 添加角色-资源关联
        if (resourceIds != null && resourceIds.size() > 0) {
            roleResourceMapper.deleteByRoleId(roleId);
            this.bathAddRoleResource(roleId, resourceIds);
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
    public void bathAddRoleResource(Long roleId, List<Long> resourceIds) {
        for (Long resourceId : resourceIds) {
            RoleResourceDO roleResourceDO = new RoleResourceDO(null, roleId, resourceId);
            roleResourceMapper.insert(roleResourceDO);
        }
    }

}
