package org.shanzhaozhen.basicrepo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.shanzhaozhen.basiccommon.domain.sys.UserDO;
import org.shanzhaozhen.common.entity.JWTUser;
import org.shanzhaozhen.basiccommon.dto.UserDTO;

public interface UserMapper extends BaseMapper<UserDO> {

    @Select("select id, username, password, account_non_expired, account_non_locked, credentials_non_expired, enabled, " +
            "name, nickname, sex, birthday, avatar, email, phone_number, address_code, detailed_address, introduction " +
            "from sys_user where username = #{username}")
    UserDO selectUserByUsername(@Param("username") String username);

    @Select("select count(id) from sys_user where username = #{username}")
    Integer countByUsername(@Param("username") String username);

    @Select("select id, username, password, account_non_expired, account_non_locked, credentials_non_expired, enabled, " +
            "name, nickname, sex, birthday, avatar, email, phone_number, address_code, detailed_address, introduction " +
            "from sys_user where id = #{id}")
    UserDTO getUserByUserId(@Param("id") Long id);

    UserDTO getUserAndRolesByUserId(@Param("id") Long id);

    JWTUser getJWTUserByUserId(Long userId);

    @Select("select id, username, password, account_non_expired, account_non_locked, credentials_non_expired, enabled, " +
            "name, nickname, sex, birthday, avatar, email, phone_number, address_code, detailed_address, introduction " +
            "from sys_user where username = #{username}")
    UserDTO getUserByUsername(@Param("username") String username);

    @Select("select id, username, account_non_expired, account_non_locked, credentials_non_expired, enabled, " +
            "name, nickname, sex, birthday, avatar, email, phone_number, address_code, detailed_address, introduction " +
            "from sys_user where username like concat ('%', #{keyword}, '%') or name like concat ('%', #{keyword}, '%') " +
            "or nickName like concat ('%', #{keyword}, '%') or introduction like concat ('%', #{keyword}, '%')")
    Page<UserDTO> getUserPage(Page<UserDTO> page, @Param("keyword") String keyword);
}
