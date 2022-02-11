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

    UserDTO getUserById(@Param("id") Long id);

    UserDTO getUserAndRolesByUserId(@Param("id") Long id);

    JWTUser getJWTUserByUserId(Long userId);

    UserDTO getUserByUsername(@Param("username") String username);

    Page<UserDTO> getUserPage(Page<UserDTO> page, @Param("keyword") String keyword);

    Page<UserDTO> getUserPageByRoleId(Page<UserDTO>page, @Param("roleId") Long roleId, @Param("keyword") String keyword);

    Page<UserDTO> getUserPageByDepartmentId(Page<UserDTO> page, @Param("departmentId") Long departmentId, @Param("keyword") String keyword);

}