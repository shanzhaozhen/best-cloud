package org.shanzhaozhen.authorize.service;

import org.shanzhaozhen.authorize.pojo.vo.OAuth2AuthorizationConsentVO;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationConsent;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationConsentService;

/**
 * @Author: shanzhaozhen
 * @Date: 2022-06-17
 * @Description:
 */
public interface CustomOAuth2AuthorizationConsentService extends OAuth2AuthorizationConsentService {

    void addOrUpdateOAuth2AuthorizationConsent(OAuth2AuthorizationConsent oAuth2AuthorizationConsent);

    void deleteOAuth2AuthorizationConsent(String registeredClientId, String principalName);

}
