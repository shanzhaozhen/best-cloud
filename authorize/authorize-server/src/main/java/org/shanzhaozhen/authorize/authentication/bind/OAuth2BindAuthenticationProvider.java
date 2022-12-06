package org.shanzhaozhen.authorize.authentication.bind;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthorizationCodeAuthenticationProvider;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthorizationCodeAuthenticationToken;
import org.springframework.security.oauth2.client.endpoint.OAuth2AccessTokenResponseClient;
import org.springframework.security.oauth2.client.endpoint.OAuth2AuthorizationCodeGrantRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2AuthorizationException;
import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.util.Assert;

import java.util.Collection;
import java.util.Map;

public class OAuth2BindAuthenticationProvider implements AuthenticationProvider {

	private final OAuth2AuthorizationCodeAuthenticationProvider authorizationCodeAuthenticationProvider;

	private final OAuth2UserService<OAuth2UserRequest, OAuth2User> userService;

	private GrantedAuthoritiesMapper authoritiesMapper = ((authorities) -> authorities);


	public OAuth2BindAuthenticationProvider(
			OAuth2AccessTokenResponseClient<OAuth2AuthorizationCodeGrantRequest> accessTokenResponseClient,
			OAuth2UserService<OAuth2UserRequest, OAuth2User> userService) {
		Assert.notNull(userService, "userService cannot be null");
		this.authorizationCodeAuthenticationProvider = new OAuth2AuthorizationCodeAuthenticationProvider(
				accessTokenResponseClient);
		this.userService = userService;
	}

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		OAuth2BindAuthenticationToken loginAuthenticationToken = (OAuth2BindAuthenticationToken) authentication;
		// Section 3.1.2.1 Authentication Request -
		// https://openid.net/specs/openid-connect-core-1_0.html#AuthRequest scope
		// REQUIRED. OpenID Connect requests MUST contain the "openid" scope value.
		if (loginAuthenticationToken.getAuthorizationExchange().getAuthorizationRequest().getScopes()
				.contains("openid")) {
			// This is an OpenID Connect Authentication Request so return null
			// and let OidcAuthorizationCodeAuthenticationProvider handle it instead
			return null;
		}
		OAuth2AuthorizationCodeAuthenticationToken authorizationCodeAuthenticationToken;
		try {
			authorizationCodeAuthenticationToken = (OAuth2AuthorizationCodeAuthenticationToken) this.authorizationCodeAuthenticationProvider
					.authenticate(new OAuth2AuthorizationCodeAuthenticationToken(
							loginAuthenticationToken.getClientRegistration(),
							loginAuthenticationToken.getAuthorizationExchange()));
		}
		catch (OAuth2AuthorizationException ex) {
			OAuth2Error oauth2Error = ex.getError();

			if (oauth2Error.getDescription().contains("timed out")) {
				throw new OAuth2AuthenticationException(oauth2Error, "第三方服务器访问超时，请稍后重试！", ex);
			} else {
				throw new OAuth2AuthenticationException(oauth2Error, oauth2Error.toString(), ex);
			}
		}
		OAuth2AccessToken accessToken = authorizationCodeAuthenticationToken.getAccessToken();
		Map<String, Object> additionalParameters = authorizationCodeAuthenticationToken.getAdditionalParameters();
		OAuth2User oauth2User = this.userService.loadUser(new OAuth2UserRequest(
				loginAuthenticationToken.getClientRegistration(), accessToken, additionalParameters));
		Collection<? extends GrantedAuthority> mappedAuthorities = this.authoritiesMapper
				.mapAuthorities(oauth2User.getAuthorities());
		OAuth2BindAuthenticationToken authenticationResult = new OAuth2BindAuthenticationToken(
				loginAuthenticationToken.getClientRegistration(), loginAuthenticationToken.getAuthorizationExchange(),
				oauth2User, mappedAuthorities, accessToken, authorizationCodeAuthenticationToken.getRefreshToken());
		authenticationResult.setDetails(loginAuthenticationToken.getDetails());
		return authenticationResult;
	}

	public final void setAuthoritiesMapper(GrantedAuthoritiesMapper authoritiesMapper) {
		Assert.notNull(authoritiesMapper, "authoritiesMapper cannot be null");
		this.authoritiesMapper = authoritiesMapper;
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return OAuth2BindAuthenticationToken.class.isAssignableFrom(authentication);
	}

}
