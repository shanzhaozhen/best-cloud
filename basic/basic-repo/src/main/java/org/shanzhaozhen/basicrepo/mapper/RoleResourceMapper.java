package org.shanzhaozhen.basicrepo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Delete;
import org.shanzhaozhen.basiccommon.domain.sys.RoleResourceDO;

public interface RoleResourceMapper extends BaseMapper<RoleResourceDO> {

    @Delete("delete from sys_role_resource where role_id = #{roleId}")
    int deleteByRoleId(Long roleId);
}
