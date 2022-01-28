package org.shanzhaozhen.uaa.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.shanzhaozhen.uaa.domain.PermissionDO;
import org.shanzhaozhen.uaa.dto.PermissionDTO;

import java.util.List;

public interface PermissionMapper extends BaseMapper<PermissionDO> {

    @Select("select r.id, r.name, r.path, r.type, r.pid, r.priority, r.description, " +
            "r.created_by, r.created_date, r.last_modified_by, r.last_modified_date " +
            "from sys_permission r " +
            "inner join sys_role_permission srr on srr.role_id = #{roleId} and r.id = srr.permission_id")
    List<PermissionDTO> getPermissionByRoleId(@Param("roleId") Long roleId);

    List<PermissionDTO> getPermissionListByType(@Param("type") Integer type);

    List<PermissionDTO> getPermissionRoleListByTypeAndUserId(@Param("type") Integer type, @Param("userId") Long userId);

    @Select("select id, name, path, type, pid, priority, description, " +
            "created_by, created_date, last_modified_by, last_modified_date " +
            "from sys_permission order by priority")
    List<PermissionDTO> getPermissionList();

}
