package org.shanzhaozhen.uaa.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.shanzhaozhen.uaa.pojo.entity.UserDO;
import org.shanzhaozhen.uaa.pojo.dto.JWTUser;
import org.shanzhaozhen.uaa.pojo.dto.UserDTO;

public interface UserMapper extends BaseMapper<UserDO> {

    UserDO selectUserByUsername(@Param("username") String username);

    Integer countByUsername(@Param("username") String username);

    UserDTO getUserById(@Param("id") String id);

    UserDTO getUserAndRolesByUserId(@Param("id") String id);

    JWTUser getJWTUserByUserId(@Param("userId") String userId);

    UserDTO getUserByUsername(@Param("username") String username);

    UserDTO getUserByUserId(@Param("userId") String userId);

    Page<UserDTO> getUserPage(Page<UserDTO> page, @Param("keyword") String keyword);

    Page<UserDTO> getUserPageByRoleId(Page<UserDTO>page, @Param("roleId") String roleId, @Param("keyword") String keyword);

    Page<UserDTO> getUserPageByDepartmentId(Page<UserDTO> page, @Param("departmentId") String departmentId, @Param("keyword") String keyword);

}
