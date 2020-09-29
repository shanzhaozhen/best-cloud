package org.shanzhaozhen.basicservice.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import org.shanzhaozhen.basiccommon.converter.RoleConverter;
import org.shanzhaozhen.basiccommon.domain.sys.RoleDO;
import org.shanzhaozhen.basiccommon.domain.sys.RoleResourceDO;
import org.shanzhaozhen.basiccommon.domain.sys.RoleRouteDO;
import org.shanzhaozhen.basiccommon.dto.RoleDTO;
import org.shanzhaozhen.basiccommon.form.BaseSearchForm;
import org.shanzhaozhen.basicrepo.mapper.RoleMapper;
import org.shanzhaozhen.basicrepo.mapper.RoleResourceMapper;
import org.shanzhaozhen.basicrepo.mapper.RoleRouteMapper;
import org.shanzhaozhen.basicservice.service.RoleService;
import org.shanzhaozhen.basiccommon.utils.CustomBeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.validation.constraints.NotNull;
import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {

    private final RoleMapper roleMapper;

    private final RoleRouteMapper roleRouteMapper;

    private final RoleResourceMapper roleResourceMapper;

    public RoleServiceImpl(RoleMapper roleMapper, RoleRouteMapper roleRouteMapper, RoleResourceMapper roleResourceMapper) {
        this.roleMapper = roleMapper;
        this.roleRouteMapper = roleRouteMapper;
        this.roleResourceMapper = roleResourceMapper;
    }

    @Override
    public List<RoleDTO> getRolesByUserId(Long userId) {
        return roleMapper.getRoleListByUserId(userId);
    }

    @Override
    public Page<RoleDTO> getRolePage(BaseSearchForm<RoleDTO> baseSearchForm) {
        return roleMapper.getRolePage(baseSearchForm.getPage(), baseSearchForm.getKeyword());
    }

    @Override
    public List<RoleDTO> getAllRoles() {
        return roleMapper.getAllRoles();
    }

    @Override
    public RoleDTO getRoleById(Long roleId) {
        RoleDTO roleDTO = roleMapper.getRoleByRoleId(roleId);
        Assert.notNull(roleDTO, "获取失败：没有找到该角色或已被删除");
        return roleDTO;
    }

    @Override
    @Transactional
    public Long addRole(RoleDTO roleDTO) {
        RoleDTO roleInDB = roleMapper.getRoleByIdentification(roleDTO.getIdentification());
        Assert.isNull(roleInDB, "创建失败：标识名称已被占用");
        RoleDO roleDO = RoleConverter.toDO(roleDTO);
        roleMapper.insert(roleDO);
        updateRouteAndResource(roleDO.getId(), roleDTO.getRouteIds(), roleDTO.getResourceIds());
        return roleDO.getId();
    }

    @Override
    @Transactional
    public Long updateRole(RoleDTO roleDTO) {
        Assert.notNull(roleDTO.getId(), "角色id不能为空");
        RoleDTO roleInDB = roleMapper.getRoleByIdNotEqualAndIdentificationEqual(roleDTO.getId(), roleDTO.getIdentification());
        Assert.isNull(roleInDB, "更新失败：标识名称已被占用");
        RoleDO roleDO = roleMapper.selectById(roleDTO.getId());
        Assert.notNull(roleDO, "更新失败：没有找到该角色或已被删除");
        CustomBeanUtils.copyPropertiesExcludeMeta(roleDTO, roleDO);
        roleMapper.updateById(roleDO);
        updateRouteAndResource(roleDO.getId(), roleDTO.getRouteIds(), roleDTO.getResourceIds());
        return roleDO.getId();
    }

    @Override
    @Transactional
    public Long deleteRole(Long roleId) {
        roleRouteMapper.deleteByRoleId(roleId);
        roleResourceMapper.deleteByRoleId(roleId);
        roleMapper.deleteById(roleId);
        return roleId;
    }

    @Override
    @Transactional
    public void updateRouteAndResource(@NotNull Long roleId, List<Long> routeIds, List<Long> resourceIds) {
        // 添加角色-路由关联
        if (routeIds != null && routeIds.size() > 0) {
            roleRouteMapper.deleteByRoleId(roleId);
            this.bathAddRoleRoute(roleId, routeIds);
        }
        // 添加角色-资源关联
        if (resourceIds != null && resourceIds.size() > 0) {
            roleResourceMapper.deleteByRoleId(roleId);
            this.bathAddRoleResource(roleId, resourceIds);
        }
    }

    @Override
    @Transactional
    public void bathAddRoleRoute(Long roleId, List<Long> routeIds) {
        for (Long routeId : routeIds) {
            RoleRouteDO RoleRouteDO = new RoleRouteDO(null, roleId, routeId);
            roleRouteMapper.insert(RoleRouteDO);
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
