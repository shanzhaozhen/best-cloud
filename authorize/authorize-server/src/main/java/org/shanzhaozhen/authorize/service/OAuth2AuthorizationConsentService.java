package org.shanzhaozhen.authorize.service;

import org.shanzhaozhen.authorize.pojo.dto.OAuth2AuthorizationConsentDTO;

/**
 * @Author: shanzhaozhen
 * @Date: 2022-06-17
 * @Description: oauth2 客户端授权信息服务
 */
public interface OAuth2AuthorizationConsentService {

    /**
     * 通过客户端id、及用户名信息查找客户端授权信息
     * @param registeredClientId
     * @param principalName
     * @return
     */
    OAuth2AuthorizationConsentDTO getOAuth2AuthorizationConsent(String registeredClientId, String principalName);

    /**
     * 添加或更新 oauth2 客户端授权信息
     * @param oauth2AuthorizationConsentDTO
     */
    void addOrUpdateOAuth2AuthorizationConsent(OAuth2AuthorizationConsentDTO oauth2AuthorizationConsentDTO);

    /**
     * 删除 oauth2 客户端授权信息
     * @param registeredClientId
     * @param principalName
     */
    void deleteOAuth2AuthorizationConsent(String registeredClientId, String principalName);

}
