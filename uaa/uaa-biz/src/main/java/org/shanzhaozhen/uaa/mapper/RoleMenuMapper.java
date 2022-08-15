package org.shanzhaozhen.uaa.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.shanzhaozhen.uaa.pojo.entity.RoleMenuDO;

import java.util.List;

public interface RoleMenuMapper extends BaseMapper<RoleMenuDO> {

    int deleteByRoleId(@Param("roleId") String roleId);

    List<String> getMenuIdsByRoleId(@Param("roleId") String roleId);
}
