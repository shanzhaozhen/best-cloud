package org.shanzhaozhen.authorize.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.shanzhaozhen.authorize.pojo.dto.OAuth2UserDTO;
import org.shanzhaozhen.authorize.pojo.dto.OAuth2UserDTO;
import org.shanzhaozhen.authorize.pojo.form.ChangePasswordForm;

import java.util.List;

public interface OAuth2UserService {

    /**
     * 通过用户id查找用户
     * @param userId
     * @return
     */
    OAuth2UserDTO getUserById(String userId);

    /**
     * 通过用户名查找用户
     * @param username
     * @return
     */
    OAuth2UserDTO getUserByUsername(String username);

    /**
     * 通过用户ID查找用户
     * @param userId
     * @return
     */
    OAuth2UserDTO getUserByUserId(String userId);

    /**
     * 获取当前用户
     */
    OAuth2UserDTO getCurrentUser();

    /**
     * 注册新用户
     * @param oauth2OAuth2UserDTO
     * @return
     */
    String register(OAuth2UserDTO oauth2OAuth2UserDTO);

    /**
     * 检查用户名是否已存在
     * @param username
     * @return
     */
    Boolean isExistUser(String username);

//    /**
//     * 获取当前用户的主要信息
//     * @return
//     */
//    CurrentUser getUserInfo();

    /**
     * 获取用户的分页列表
     * @param page
     * @param keyword
     * @return
     */
    Page<OAuth2UserDTO> getUserPage(Page<OAuth2UserDTO> page, String keyword);

    /**
     * 新增用户
     * @param oauth2OAuth2UserDTO
     * @return
     */
    String addUser(OAuth2UserDTO oauth2OAuth2UserDTO);

    /**
     * 修改用户
     * @param oauth2OAuth2UserDTO
     * @return
     */
    String updateUser(OAuth2UserDTO oauth2OAuth2UserDTO);

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
    Page<OAuth2UserDTO> getUserPageByRoleId(Page<OAuth2UserDTO> page, String roleId, String keyword);

    /**
     * 通过获取部门Id获取用户
     * @param page
     * @param departmentId
     * @param keyword
     * @return
     */
    Page<OAuth2UserDTO> getUserPageByDepartmentId(Page<OAuth2UserDTO> page, String departmentId, String keyword);

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
