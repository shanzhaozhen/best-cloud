package org.shanzhaozhen.authorize.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.shanzhaozhen.basiccommon.dto.JWTUser;
import org.shanzhaozhen.basiccommon.dto.UserDTO;
import org.shanzhaozhen.basiccommon.form.BaseSearchForm;
import org.shanzhaozhen.basiccommon.vo.UserInfo;

import java.util.List;

public interface UserService {

    /**
     * 通过用户id查找用户
     * @param userId
     * @return
     */
    UserDTO getUserById(Long userId);

    /**
     * 通过用户名查找用户
     * @param username
     * @return
     */
    UserDTO getUserByUsername(String username);

    /**
     * 通过用户id获取JWT认证用户信息
     * @param userId
     * @return
     */
    JWTUser getJWTUser(Long userId);

    /**
     * 获取当前用户
     */
    UserDTO getCurrentUser();

    /**
     * 注册新用户
     * @param userDTO
     * @return
     */
    Long register(UserDTO userDTO);

    /**
     * 检查用户名是否已存在
     * @param username
     * @return
     */
    Boolean isExistUser(String username);

    /**
     * 获取当前用户的主要信息
     * @return
     */
    UserInfo getUserInfo();

    /**
     * 获取用户的分页列表
     * @param baseSearchForm
     * @return
     */
    Page<UserDTO> getUserPage(BaseSearchForm<UserDTO> baseSearchForm);

    /**
     * 新增用户
     * @param userDTO
     * @return
     */
    Long addUser(UserDTO userDTO);

    /**
     * 修改角色
     * @param userDTO
     * @return
     */
    Long updateUser(UserDTO userDTO);

    /**
     * 删除用户
     * @param userId
     * @return
     */
    Long deleteUser(Long userId);

    /**
     * 批量添加用户角色
     * @param userId
     * @param roleIds
     */
    void bathAddUserRole(Long userId, List<Long> roleIds);

}
