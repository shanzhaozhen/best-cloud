package org.shanzhaozhen.basicrepo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.shanzhaozhen.basiccommon.domain.sys.RoleDO;
import org.shanzhaozhen.basiccommon.dto.RoleDTO;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;

public interface RoleMapper extends BaseMapper<RoleDO> {

    RoleDTO getRoleByRoleId(@Param("roleId") Long roleId);

    @Select("select r.id, r.name, r.identification, r.description, " +
            "r.created_by, r.created_date, r.last_modified_by, r.last_modified_date " +
            "from sys_role r " +
            "inner join sys_role_route srm on srm.route_id = #{routeId} and r.id = srm.role_id")
    List<RoleDTO> getRoleByRouteId(@Param("routeId") Long routeId);

    @Select("select r.id, r.name, r.identification, r.description, " +
            "r.created_by, r.created_date, r.last_modified_by, r.last_modified_date " +
            "from sys_role r " +
            "inner join sys_role_resource srr on srr.resource_id = #{resourceId} and r.id = srr.role_id")
    List<RoleDTO> getRoleByResourceId(@Param("resourceId") Long resourceId);

    @Select("select r.id, r.name, r.identification, r.description, " +
            "r.created_by, r.created_date, r.last_modified_by, r.last_modified_date " +
            "from sys_role r inner join sys_user_role sur on sur.user_id = #{userId} and r.id = sur.role_id "
//            + "inner join sys_user su on sur.user_id = su.id "
    )
    List<RoleDTO> getRoleListByUserId(@Param("userId") Long userId);

    @Select("select r.identification from sys_role r inner join sys_user_role sur on sur.user_id = #{userId} and r.id = sur.role_id ")
    List<SimpleGrantedAuthority> getAuthoritiesByUserId(@Param("userId") Long userId);

    @Select("select id, name, identification, description, " +
            "created_by, created_date, last_modified_by, last_modified_date " +
            "from sys_role where name like concat ('%', #{keyword}, '%') or identification like concat ('%', #{keyword}, '%') or description like concat ('%', #{keyword}, '%')")
    Page<RoleDTO> getRolePage(Page<RoleDTO> page, @Param("keyword") String keyword);

    @Select("select r.id, r.name from sys_role r")
    List<RoleDTO> getAllRoles();

    @Select("select id, name, identification, description, " +
            "created_by, created_date, last_modified_by, last_modified_date " +
            "from sys_role where identification = #{identification}")
    RoleDTO getRoleByIdentification(@Param("identification") String identification);

    @Select("select id, name, identification, description, " +
            "created_by, created_date, last_modified_by, last_modified_date " +
            "from sys_role where id != #{id} and identification = #{identification}")
    RoleDTO getRoleByIdNotEqualAndIdentificationEqual(@Param("id") Long id, @Param("identification") String identification);
}
