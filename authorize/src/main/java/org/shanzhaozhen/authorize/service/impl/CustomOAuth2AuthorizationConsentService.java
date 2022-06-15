package org.shanzhaozhen.authorize.service.impl;


import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationConsent;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationConsentService;

/**
 * 自定义管理，改成 mybatis 实现
 */
public class CustomOAuth2AuthorizationConsentService implements OAuth2AuthorizationConsentService {

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
