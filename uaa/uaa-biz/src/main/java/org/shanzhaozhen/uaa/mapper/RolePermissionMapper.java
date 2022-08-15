package org.shanzhaozhen.uaa.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.shanzhaozhen.uaa.pojo.entity.RolePermissionDO;

import java.util.List;

public interface RolePermissionMapper extends BaseMapper<RolePermissionDO> {

    int deleteByRoleId(@Param("roleId") String roleId);

    List<String> getPermissionIdsByRoleId(@Param("roleId") String roleId);

}
