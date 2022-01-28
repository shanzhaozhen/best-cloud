package org.shanzhaozhen.uaa.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.shanzhaozhen.uaa.domain.RoleDO;
import org.shanzhaozhen.uaa.dto.CustomGrantedAuthority;
import org.shanzhaozhen.uaa.dto.RoleDTO;

import java.util.List;

public interface RoleMapper extends BaseMapper<RoleDO> {

    RoleDTO getRoleDetailById(Long roleId);

    @Select("select r.id, r.name, r.code, r.description, " +
            "r.created_by, r.created_date, r.last_modified_by, r.last_modified_date " +
            "from sys_role r " +
            "inner join sys_role_menu srm on srm.menu_id = #{menuId} and r.id = srm.role_id")
    List<RoleDTO> getRoleByMenuId(@Param("menuId") Long menuId);

    @Select("select r.id, r.name, r.code, r.description, " +
            "r.created_by, r.created_date, r.last_modified_by, r.last_modified_date " +
            "from sys_role r " +
            "inner join sys_role_permission srr on srr.permission_id = #{permissionId} and r.id = srr.role_id")
    List<RoleDTO> getRoleByPermissionId(@Param("permissionId") Long permissionId);

    @Select("select r.id, r.name, r.code, r.description, " +
            "r.created_by, r.created_date, r.last_modified_by, r.last_modified_date " +
            "from sys_role r inner join sys_user_role sur on sur.user_id = #{userId} and r.id = sur.role_id "
//            + "inner join sys_user su on sur.user_id = su.id "
    )
    List<RoleDTO> getRoleListByUserId(@Param("userId") Long userId);

    @Select("select r.code role from sys_role r inner join sys_user_role sur on sur.user_id = #{userId} and r.id = sur.role_id")
    List<CustomGrantedAuthority> getAuthoritiesByUserId(@Param("userId") Long userId);

    Page<RoleDTO> getRolePage(Page<RoleDTO> page, @Param("keyword") String keyword);

    @Select("select r.id, r.name from sys_role r")
    List<RoleDTO> getAllRoles();

    @Select("select id, name, code, description, " +
            "created_by, created_date, last_modified_by, last_modified_date " +
            "from sys_role where code = #{code}")
    RoleDTO getRoleByCode(@Param("code") String code);

//    @Select("select id, name, code, description, " +
//            "created_by, created_date, last_modified_by, last_modified_date " +
//            "from sys_role where id != #{id} and code = #{code}")
//    RoleDTO getRoleByIdNotEqualAndCodeEqual(@Param("id") Long id, @Param("code") String code);

}
