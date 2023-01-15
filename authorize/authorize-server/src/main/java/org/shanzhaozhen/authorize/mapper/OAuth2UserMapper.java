package org.shanzhaozhen.authorize.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.shanzhaozhen.oauth.pojo.dto.OAuth2UserDTO;
import org.shanzhaozhen.oauth.pojo.entity.OAuth2UserDO;

public interface OAuth2UserMapper extends BaseMapper<OAuth2UserDO> {

    OAuth2UserDO selectUserByUsername(@Param("username") String username);

    Integer countByUsername(@Param("username") String username);

    OAuth2UserDTO getUserById(@Param("id") String id);

    OAuth2UserDTO getUserByUsername(@Param("username") String username);

    OAuth2UserDTO getUserByPhone(@Param("phone") String phone);

    OAuth2UserDTO getUserByUserId(@Param("userId") String userId);

    Page<OAuth2UserDTO> getUserPage(Page<OAuth2UserDTO> page, @Param("keyword") String keyword);

    Page<OAuth2UserDTO> getUserPageByRoleId(Page<OAuth2UserDTO>page, @Param("roleId") String roleId, @Param("keyword") String keyword);

    Page<OAuth2UserDTO> getUserPageByDepartmentId(Page<OAuth2UserDTO> page, @Param("departmentId") String departmentId, @Param("keyword") String keyword);

}
