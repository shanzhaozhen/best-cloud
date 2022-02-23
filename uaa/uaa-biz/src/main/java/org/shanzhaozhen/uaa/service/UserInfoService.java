package org.shanzhaozhen.uaa.service;

import org.shanzhaozhen.uaa.pojo.dto.UserInfoDTO;

import java.util.List;


public interface UserInfoService {

    /**
     * 通过 id 获取用户信息
     * @param userInfoId
     * @return
     */
    UserInfoDTO getUserInfoById(Long userInfoId);

    /**
     * 通过用户 id 获取用户信息
     * @param userId
     * @return
     */
    UserInfoDTO getUserInfoByUserId(Long userId);

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
    Long addUserInfo(UserInfoDTO userInfoDTO);

    /**
     * 修改用户信息
     * @param userInfoDTO
     * @return
     */
    Long updateUserInfo(UserInfoDTO userInfoDTO);

    /**
     * 删除用户信息
     * @param userInfoId
     * @return
     */
    Long deleteUserInfo(Long userInfoId);

    /**
     * 批量删除用户信息
     * @param userInfoIds
     * @return
     */
    List<Long> batchDeleteUserInfo(List<Long> userInfoIds);

    /**
     * 通过用户 ID 删除用户信息
     * @param userId
     * @return
     */
    void deleteUserInfoByUserId(Long userId);

}
