package org.shanzhaozhen.uaa.service;

import org.shanzhaozhen.uaa.pojo.dto.UserInfoDTO;

import java.util.List;


public interface UserInfoService {

    /**
     * 通过 id 获取用户信息
     * @param userInfoId
     * @return
     */
    UserInfoDTO getUserInfoById(String userInfoId);

    /**
     * 通过用户 id 获取用户信息
     * @param userId
     * @return
     */
    UserInfoDTO getUserInfoByUserId(String userId);

    /**
     * 根据当前用户的用户信息
     * @return
     */
    UserInfoDTO getCurrentUserInfo();

    /**
     * 新增用户信息
     * @param userInfoDTO
     * @return
     */
    String addUserInfo(UserInfoDTO userInfoDTO);

    /**
     * 修改用户信息
     * @param userInfoDTO
     * @return
     */
    String updateUserInfo(UserInfoDTO userInfoDTO);

    /**
     * 删除用户信息
     * @param userInfoId
     * @return
     */
    String deleteUserInfo(String userInfoId);

    /**
     * 批量删除用户信息
     * @param userInfoIds
     * @return
     */
    List<String> batchDeleteUserInfo(List<String> userInfoIds);

    /**
     * 通过用户 ID 删除用户信息
     * @param userId
     * @return
     */
    void deleteUserInfoByUserId(String userId);

}
