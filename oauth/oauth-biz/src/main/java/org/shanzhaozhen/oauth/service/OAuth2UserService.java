package org.shanzhaozhen.oauth.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.shanzhaozhen.oauth.pojo.dto.OAuth2UserDTO;

import java.util.List;

public interface OAuth2UserService {

    /**
     * 通过用户id查找用户
     * @param userId
     * @return
     */
    OAuth2UserDTO getOAuth2UserById(String userId);

    /**
     * 通过用户ID查找用户
     * @param userId
     * @return
     */
    OAuth2UserDTO getOAuth2UserByUserId(String userId);

    /**
     * 获取当前用户
     */
    OAuth2UserDTO getCurrentUser();

    /**
     * 注册新用户
     * @param userDTO
     * @return
     */
    String register(OAuth2UserDTO userDTO);

    /**
     * 检查用户名是否已存在
     * @param username
     * @return
     */
    Boolean isExistUser(String username);

    /**
     * 获取用户的分页列表
     * @param page
     * @param keyword
     * @return
     */
    Page<OAuth2UserDTO> getOAuth2UserPage(Page<OAuth2UserDTO> page, String keyword);

    /**
     * 新增用户
     * @param userDTO
     * @return
     */
    String addOAuth2User(OAuth2UserDTO userDTO);

    /**
     * 修改用户
     * @param userDTO
     * @return
     */
    String updateOAuth2User(OAuth2UserDTO userDTO);

    /**
     * 删除用户
     * @param userId
     * @return
     */
    String deleteOAuth2User(String userId);

    /**
     * 批量删除用户
     * @param userIds
     * @return
     */
    List<String> batchDeleteOAuth2User(List<String> userIds);

}
