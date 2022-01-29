package org.shanzhaozhen.uaa.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.shanzhaozhen.uaa.pojo.entity.RolePermissionDO;

public interface RolePermissionMapper extends BaseMapper<RolePermissionDO> {

    int deleteByRoleId(Long roleId);

}
