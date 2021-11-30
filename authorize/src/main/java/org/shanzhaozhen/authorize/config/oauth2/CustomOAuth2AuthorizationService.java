package org.shanzhaozhen.authorize.config.oauth2;


import org.springframework.security.oauth2.core.OAuth2TokenType;
import org.springframework.security.oauth2.server.authorization.OAuth2Authorization;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationService;

public class CustomOAuth2AuthorizationService implements OAuth2AuthorizationService {



	@Override
	public void save(OAuth2Authorization authorization) {

	}

	@Override
	public void remove(OAuth2Authorization authorization) {

	}

	@Override
	public OAuth2Authorization findById(String id) {
		return null;
	}

	@Override
	public OAuth2Authorization findByToken(String token, OAuth2TokenType tokenType) {
		return null;
	}
}
