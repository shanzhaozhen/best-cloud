package org.shanzhaozhen.uaa.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.shanzhaozhen.uaa.pojo.entity.RoleDO;
import org.shanzhaozhen.uaa.pojo.dto.CustomGrantedAuthority;
import org.shanzhaozhen.uaa.pojo.dto.RoleDTO;

import java.util.List;

public interface RoleMapper extends BaseMapper<RoleDO> {

    RoleDTO getRoleDetailById(@Param("roleId") Long roleId);

    List<RoleDTO> getRoleByMenuId(@Param("menuId") Long menuId);

    List<RoleDTO> getRoleByPermissionId(@Param("permissionId") Long permissionId);

    List<RoleDTO> getRoleListByUserId(@Param("userId") Long userId);

    List<CustomGrantedAuthority> getAuthoritiesByUserId(@Param("userId") Long userId);

    Page<RoleDTO> getRolePage(Page<RoleDTO> page, @Param("keyword") String keyword);

    List<RoleDTO> getAllRoles();

    RoleDTO getRoleByCode(@Param("code") String code);


}
