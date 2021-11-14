package org.shanzhaozhen.security.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Delete;
import org.shanzhaozhen.security.domain.RoleMenuDO;

public interface RoleMenuMapper extends BaseMapper<RoleMenuDO> {

    @Delete("delete from sys_role_menu where role_id = #{roleId}")
    int deleteByRoleId(Long roleId);
}
