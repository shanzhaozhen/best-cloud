package org.shanzhaozhen.authorize.service;


import org.shanzhaozhen.oauth.pojo.dto.OAuth2UserInfoDTO;

import java.util.List;

public interface OAuth2UserInfoService {

    /**
     * 通过 id 获取用户信息
     * @param oauth2UserInfoId
     * @return
     */
    OAuth2UserInfoDTO getOAuth2UserInfoById(String oauth2UserInfoId);

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
     * @param oauth2UserInfoDTO
     * @return
     */
    String addOAuth2UserInfo(OAuth2UserInfoDTO oauth2UserInfoDTO);

    /**
     * 修改用户信息
     * @param oauth2UserInfoDTO
     * @return
     */
    String updateOAuth2UserInfo(OAuth2UserInfoDTO oauth2UserInfoDTO);

    /**
     * 更新当前用户信息
     * @param oauth2UserInfoDTO
     */
    void updateCurrentUserinfo(OAuth2UserInfoDTO oauth2UserInfoDTO);

    /**
     * 删除用户信息
     * @param oauth2UserInfoId
     * @return
     */
    String deleteOAuth2UserInfo(String oauth2UserInfoId);

    /**
     * 批量删除用户信息
     * @param oauth2UserInfoIds
     * @return
     */
    List<String> batchDeleteOAuth2UserInfo(List<String> oauth2UserInfoIds);

    /**
     * 通过用户 ID 删除用户信息
     * @param userId
     * @return
     */
    void deleteOAuth2UserInfoByUserId(String userId);

}
