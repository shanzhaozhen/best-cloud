package org.shanzhaozhen.uaa.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Delete;
import org.shanzhaozhen.uaa.domain.RolePermissionDO;

public interface RolePermissionMapper extends BaseMapper<RolePermissionDO> {

    @Delete("delete from sys_role_permission where role_id = #{roleId}")
    int deleteByRoleId(Long roleId);

}
