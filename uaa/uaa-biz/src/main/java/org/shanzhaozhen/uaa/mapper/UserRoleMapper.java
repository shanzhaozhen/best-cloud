package org.shanzhaozhen.uaa.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.shanzhaozhen.uaa.pojo.entity.UserRoleDO;
import org.shanzhaozhen.uaa.pojo.dto.UserDTO;

public interface UserRoleMapper extends BaseMapper<UserRoleDO> {

    UserRoleDO getUserRoleByUserIdAndRoleId(@Param("userId") String userId, @Param("roleId") String roleId);

    int deleteByUserId(@Param("userId") String userId);

    int deleteByUserIdAndRoleId(@Param("userId") String userId, @Param("roleId") String roleId);

}
