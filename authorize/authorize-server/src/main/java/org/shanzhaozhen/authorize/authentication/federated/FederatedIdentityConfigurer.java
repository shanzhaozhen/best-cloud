package org.shanzhaozhen.authorize.authentication.federated;

import org.shanzhaozhen.authorize.authentication.bind.OAuth2BindAuthenticationFilter;
import org.shanzhaozhen.authorize.authentication.bind.OAuth2BindAuthenticationProvider;
import org.shanzhaozhen.authorize.utils.SecurityUtils;
import org.shanzhaozhen.common.core.utils.HttpServletUtils;
import org.shanzhaozhen.uaa.feign.SocialUserFeignClient;
import org.shanzhaozhen.uaa.feign.UserFeignClient;
import org.shanzhaozhen.uaa.pojo.dto.AuthUser;
import org.springframework.context.ApplicationContext;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.client.authentication.OAuth2LoginAuthenticationToken;
import org.springframework.security.oauth2.client.endpoint.DefaultAuthorizationCodeTokenResponseClient;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.OAuth2LoginAuthenticationFilter;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.util.Assert;

import javax.servlet.http.Cookie;
import java.util.function.Consumer;


/**
 * A configurer for setting up Federated Identity Management.
 *
 * @author Steve Riesenberg
 * @since 0.2.3
 */
public final class FederatedIdentityConfigurer extends AbstractHttpConfigurer<FederatedIdentityConfigurer, HttpSecurity> {

	private String loginPageUrl = "/login";

	private String authorizationRequestUri;

	private Consumer<OAuth2User> oauth2UserHandler;

	private Consumer<OidcUser> oidcUserHandler;

	private final SocialUserFeignClient socialUserFeignClient;

	public FederatedIdentityConfigurer(SocialUserFeignClient socialUserFeignClient) {
		this.socialUserFeignClient = socialUserFeignClient;
	}

	/**
	 * @param loginPageUrl The URL of the login page, defaults to {@code "/login"}
	 * @return This configurer for additional configuration
	 */
	public FederatedIdentityConfigurer loginPageUrl(String loginPageUrl) {
		Assert.hasText(loginPageUrl, "loginPageUrl cannot be empty");
		this.loginPageUrl = loginPageUrl;
		return this;
	}

	/**
	 * @param authorizationRequestUri The authorization request URI for initiating
	 * the login flow with an external IDP, defaults to {@code
	 * "/oauth2/authorization/{registrationId}"}
	 * @return This configurer for additional configuration
	 */
	public FederatedIdentityConfigurer authorizationRequestUri(String authorizationRequestUri) {
		Assert.hasText(authorizationRequestUri, "authorizationRequestUri cannot be empty");
		this.authorizationRequestUri = authorizationRequestUri;
		return this;
	}

	/**
	 * @param oauth2UserHandler The {@link Consumer} for performing JIT account provisioning
	 * with an OAuth 2.0 IDP
	 * @return This configurer for additional configuration
	 */
	public FederatedIdentityConfigurer oauth2UserHandler(Consumer<OAuth2User> oauth2UserHandler) {
		Assert.notNull(oauth2UserHandler, "oauth2UserHandler cannot be null");
		this.oauth2UserHandler = oauth2UserHandler;
		return this;
	}

	/**
	 * @param oidcUserHandler The {@link Consumer} for performing JIT account provisioning
	 * with an OpenID Connect 1.0 IDP
	 * @return This configurer for additional configuration
	 */
	public FederatedIdentityConfigurer oidcUserHandler(Consumer<OidcUser> oidcUserHandler) {
		Assert.notNull(oidcUserHandler, "oidcUserHandler cannot be null");
		this.oidcUserHandler = oidcUserHandler;
		return this;
	}

	// @formatter:off
	@Override
	public void init(HttpSecurity http) throws Exception {
		ApplicationContext applicationContext = http.getSharedObject(ApplicationContext.class);
		ClientRegistrationRepository clientRegistrationRepository =
			applicationContext.getBean(ClientRegistrationRepository.class);

		FederatedIdentityAuthenticationEntryPoint authenticationEntryPoint =
			new FederatedIdentityAuthenticationEntryPoint(this.loginPageUrl, clientRegistrationRepository);
		if (this.authorizationRequestUri != null) {
			authenticationEntryPoint.setAuthorizationRequestUri(this.authorizationRequestUri);
		}

		FederatedIdentityAuthenticationSuccessHandler authenticationSuccessHandler =
			new FederatedIdentityAuthenticationSuccessHandler(socialUserFeignClient);

		FederatedIdentityAuthenticationFailureHandler authenticationFailureHandler =
				new FederatedIdentityAuthenticationFailureHandler();

		if (this.oauth2UserHandler != null) {
			authenticationSuccessHandler.setOAuth2UserHandler(this.oauth2UserHandler);
		}
		if (this.oidcUserHandler != null) {
			authenticationSuccessHandler.setOidcUserHandler(this.oidcUserHandler);
		}

		http
				.exceptionHandling(exceptionHandling ->
						exceptionHandling.authenticationEntryPoint(authenticationEntryPoint)
				)
				.oauth2Login(oauth2Login -> {
					oauth2Login
							.successHandler(authenticationSuccessHandler)
							.failureHandler(authenticationFailureHandler)
					;
					if (this.authorizationRequestUri != null) {
						String baseUri = this.authorizationRequestUri.replace("/{registrationId}", "");
						oauth2Login.authorizationEndpoint(authorizationEndpoint ->
								authorizationEndpoint.baseUri(baseUri)
						);
					}

//					DefaultOAuth2AuthorizationRequestResolver authorizationRequestResolver = new DefaultOAuth2AuthorizationRequestResolver(clientRegistrationRepository, DEFAULT_AUTHORIZATION_REQUEST_BASE_URI);
//					authorizationRequestResolver.setAuthorizationRequestCustomizer(customizer -> {
//					});
//					oauth2Login.authorizationEndpoint().authorizationRequestResolver(authorizationRequestResolver);

//					oauth2Login
//							.tokenEndpoint(tokenEndpointConfig -> {
//								tokenEndpointConfig.accessTokenResponseClient(accessTokenResponseClient);
//							})
//							.userInfoEndpoint(userInfoEndpointConfig ->
//									userInfoEndpointConfig.userService(new CustomOAuth2UserService())
//							);

//					oauth2Login.withObjectPostProcessor(new ObjectPostProcessor<OAuth2LoginAuthenticationFilter>() {
//						@Override
//						public <O extends OAuth2LoginAuthenticationFilter> O postProcess(O object) {
//							object.setAuthenticationResultConverter(o -> convertAuthenticationToken(o));
////							object.setAuthenticationFailureHandler(new OAuth2LoginAuthenticationFailureHandler());
//							return object;
//						}
//					});
				})
				.addFilterAfter(new FederatedIdentitySocialBindFilter(), FilterSecurityInterceptor.class)
		;
	}
	// @formatter:on

	public OAuth2AuthenticationToken convertAuthenticationToken(OAuth2LoginAuthenticationToken authenticationResult) {
		Cookie[] cookies = HttpServletUtils.getCookies();

		AuthUser currentUser = SecurityUtils.getCurrentUser();

		return new OAuth2AuthenticationToken(authenticationResult.getPrincipal(), authenticationResult.getAuthorities(),
				authenticationResult.getClientRegistration().getRegistrationId());
	}

}
