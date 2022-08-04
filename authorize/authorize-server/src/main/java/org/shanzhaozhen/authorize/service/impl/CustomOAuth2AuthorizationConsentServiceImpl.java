package org.shanzhaozhen.authorize.service.impl;

import org.shanzhaozhen.authorize.service.CustomOAuth2AuthorizationConsentService;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationConsent;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationConsentService;
import org.springframework.stereotype.Service;

/**
 * @Author: shanzhaozhen
 * @Date: 2022-06-17
 * @Description:
 */
//@Service
public class CustomOAuth2AuthorizationConsentServiceImpl implements CustomOAuth2AuthorizationConsentService {

    @Override
    public void save(OAuth2AuthorizationConsent authorizationConsent) {

    }

    @Override
    public void remove(OAuth2AuthorizationConsent authorizationConsent) {

    }

    @Override
    public OAuth2AuthorizationConsent findById(String registeredClientId, String principalName) {
        return null;
    }
}
