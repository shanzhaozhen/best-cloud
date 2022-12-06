package org.shanzhaozhen.uaa.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.shanzhaozhen.uaa.pojo.dto.JWTUser;
import org.shanzhaozhen.uaa.pojo.dto.UserDTO;
import org.shanzhaozhen.uaa.pojo.dto.UserInfoDTO;
import org.shanzhaozhen.uaa.pojo.form.ChangePasswordForm;
import org.shanzhaozhen.uaa.pojo.vo.CurrentUser;

import java.util.List;

public interface UserService {

    /**
     * 通过用户id查找用户
     * @param userId
     * @return
     */
    UserDTO getUserById(String userId);

    /**
     * 通过用户名查找用户
     * @param username
     * @return
     */
    UserDTO getUserByUsername(String username);

    /**
     * 通过用户ID查找用户
     * @param userId
     * @return
     */
    UserDTO getUserByUserId(String userId);

    /**
     * 通过用户id获取JWT认证用户信息
     * @param userId
     * @return
     */
    JWTUser getJWTUser(String userId);

    /**
     * 获取当前用户
     */
    UserDTO getCurrentUser();

    /**
     * 注册新用户
     * @param userDTO
     * @return
     */
    String register(UserDTO userDTO);

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
    CurrentUser getUserInfo();

    /**
     * 获取用户的分页列表
     * @param page
     * @param keyword
     * @return
     */
    Page<UserDTO> getUserPage(Page<UserDTO> page, String keyword);

    /**
     * 新增用户
     * @param userDTO
     * @return
     */
    String addUser(UserDTO userDTO);

    /**
     * 修改用户
     * @param userDTO
     * @return
     */
    String updateUser(UserDTO userDTO);

    /**
     * 删除用户
     * @param userId
     * @return
     */
    String deleteUser(String userId);

    /**
     * 批量删除用户
     * @param userIds
     * @return
     */
    List<String> batchDeleteUser(List<String> userIds);

    /**
     * 通过获取角色Id获取用户
     * @param page
     * @param roleId
     * @param keyword
     * @return
     */
    Page<UserDTO> getUserPageByRoleId(Page<UserDTO> page, String roleId, String keyword);

    /**
     * 通过获取部门Id获取用户
     * @param page
     * @param departmentId
     * @param keyword
     * @return
     */
    Page<UserDTO> getUserPageByDepartmentId(Page<UserDTO> page, String departmentId, String keyword);

    /**
     * 用户注销
     * @return
     */
    Boolean logout();

    /**
     * 更新密码
     * @param changePasswordForm
     */
    void changePassword(ChangePasswordForm changePasswordForm);

}
