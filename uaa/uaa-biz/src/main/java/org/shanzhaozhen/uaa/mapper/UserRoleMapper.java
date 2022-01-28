package org.shanzhaozhen.uaa.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.shanzhaozhen.uaa.domain.UserRoleDO;
import org.shanzhaozhen.uaa.dto.UserDTO;

public interface UserRoleMapper extends BaseMapper<UserRoleDO> {

    Page<UserDTO> getUserRolePage(Page<UserDTO> page, @Param("roleId") Long roleId, @Param("keyword") String keyword);

    @Select("select * from sys_user_role where user_id = #{userId} and role_id = #{roleId} ")
    UserRoleDO getUserRoleByUserIdAndRoleId(@Param("userId") Long userId, @Param("roleId") Long roleId);

    @Delete("delete from sys_user_role where user_id = #{userId}")
    void deleteByUserId(@Param("userId") Long userId);

    @Delete("delete from sys_user_role where user_id = #{userId} and  role_id = #{roleId}")
    void deleteByUserIdAndRoleId(@Param("userId") Long userId, @Param("roleId") Long roleId);

}
