package org.shanzhaozhen.authorize.service;

import org.shanzhaozhen.authorize.pojo.dto.OAuth2AuthorizationConsentDTO;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationConsentService;

/**
 * @Author: shanzhaozhen
 * @Date: 2022-06-17
 * @Description:
 */
public interface CustomOAuth2AuthorizationConsentService extends OAuth2AuthorizationConsentService {

    void addOAuth2AuthorizationConsent(OAuth2AuthorizationConsentDTO oAuth2AuthorizationConsentDTO);

    void deleteOAuth2AuthorizationConsent(String registeredClientId, String principalName);

    OAuth2AuthorizationConsentDTO findOAuth2AuthorizationConsent(String registeredClientId, String principalName);

}
