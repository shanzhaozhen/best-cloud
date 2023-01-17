package org.shanzhaozhen.oauth.service;


import org.shanzhaozhen.oauth.pojo.dto.OAuth2UserInfoDTO;

import java.util.List;


public interface OAuth2UserInfoService {

    /**
     * 通过 id 获取用户信息
     * @param userInfoId
     * @return
     */
    OAuth2UserInfoDTO getOAuth2UserInfoById(String userInfoId);

    /**
     * 通过用户 id 获取用户信息
     * @param userId
     * @return
     */
    OAuth2UserInfoDTO getOAuth2UserInfoByUserId(String userId);

    /**
     * 根据当前用户的用户信息
     * @return
     */
    OAuth2UserInfoDTO getCurrentUserInfo();

    /**
     * 新增用户信息
     * @param userInfoDTO
     * @return
     */
    String addOAuth2UserInfo(OAuth2UserInfoDTO userInfoDTO);

    /**
     * 修改用户信息
     * @param userInfoDTO
     * @return
     */
    String updateOAuth2UserInfo(OAuth2UserInfoDTO userInfoDTO);

    /**
     * 删除用户信息
     * @param userInfoId
     * @return
     */
    String deleteOAuth2UserInfo(String userInfoId);

    /**
     * 批量删除用户信息
     * @param userInfoIds
     * @return
     */
    List<String> batchDeleteOAuth2UserInfo(List<String> userInfoIds);

    /**
     * 通过用户 ID 删除用户信息
     * @param userId
     * @return
     */
    void deleteOAuth2UserInfoByUserId(String userId);

}
