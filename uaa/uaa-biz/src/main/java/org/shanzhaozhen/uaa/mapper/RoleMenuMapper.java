package org.shanzhaozhen.uaa.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.shanzhaozhen.uaa.pojo.entity.RoleMenuDO;

public interface RoleMenuMapper extends BaseMapper<RoleMenuDO> {

    int deleteByRoleId(Long roleId);

}
