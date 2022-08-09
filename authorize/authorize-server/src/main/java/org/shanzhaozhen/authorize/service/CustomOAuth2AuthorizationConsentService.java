package org.shanzhaozhen.authorize.service;

import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationConsent;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationConsentService;

/**
 * @Author: shanzhaozhen
 * @Date: 2022-06-17
 * @Description: oauth2 客户端授权信息服务
 */
public interface CustomOAuth2AuthorizationConsentService extends OAuth2AuthorizationConsentService {

    /**
     * 添加或更新 oauth2 客户端授权信息
     * @param oAuth2AuthorizationConsent
     */
    void addOrUpdateOAuth2AuthorizationConsent(OAuth2AuthorizationConsent oAuth2AuthorizationConsent);

    /**
     * 删除 oauth2 客户端授权信息
     * @param registeredClientId
     * @param principalName
     */
    void deleteOAuth2AuthorizationConsent(String registeredClientId, String principalName);

}
