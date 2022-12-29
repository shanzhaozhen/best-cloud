package org.shanzhaozhen.authorize.authentication.bind;

import org.shanzhaozhen.authorize.pojo.entity.OAuth2UserSocialDO;
import org.shanzhaozhen.authorize.service.OAuthUserSocialService;
import org.shanzhaozhen.authorize.utils.OAuth2AuthorizationResponseUtils;
import org.shanzhaozhen.authorize.constant.SocialType;
import org.shanzhaozhen.common.core.utils.JacksonUtils;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.log.LogMessage;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.*;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.security.oauth2.core.OAuth2ErrorCodes;
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationExchange;
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest;
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationResponse;
import org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.UrlUtils;
import org.springframework.util.Assert;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;


public class OAuth2BindAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

	/**
	 * The default {@code URI} where this {@code Filter} processes authentication
	 * requests.
	 */
	public static final String DEFAULT_FILTER_PROCESSES_URI = "/bind/oauth2/code/*";

	private static final String AUTHORIZATION_REQUEST_NOT_FOUND_ERROR_CODE = "authorization_request_not_found";

	private static final String CLIENT_REGISTRATION_NOT_FOUND_ERROR_CODE = "client_registration_not_found";

	public static final String SUCCESS_MESSAGE_ATTRIBUTE = "SUCCESS_MESSAGE";

	private final ClientRegistrationRepository clientRegistrationRepository;

	private final OAuth2AuthorizedClientRepository authorizedClientRepository;

	private final OAuthUserSocialService socialUserService;

	private AuthorizationRequestRepository<OAuth2AuthorizationRequest> authorizationRequestRepository = new HttpSessionOAuth2AuthorizationRequestRepository();

	private Converter<OAuth2BindAuthenticationToken, OAuth2AuthenticationToken> authenticationResultConverter = this::createAuthenticationResult;


	public OAuth2BindAuthenticationFilter(ClientRegistrationRepository clientRegistrationRepository,
                                          OAuth2AuthorizedClientService authorizedClientService,
										  OAuthUserSocialService socialUserService) {
		this(clientRegistrationRepository, authorizedClientService, socialUserService, DEFAULT_FILTER_PROCESSES_URI);
	}

	public OAuth2BindAuthenticationFilter(ClientRegistrationRepository clientRegistrationRepository,
										  OAuth2AuthorizedClientService authorizedClientService,
										  OAuthUserSocialService socialUserService, String filterProcessesUrl) {
		this(clientRegistrationRepository,
				new AuthenticatedPrincipalOAuth2AuthorizedClientRepository(authorizedClientService),
				socialUserService, filterProcessesUrl);
	}

	public OAuth2BindAuthenticationFilter(ClientRegistrationRepository clientRegistrationRepository,
                                          OAuth2AuthorizedClientRepository authorizedClientRepository,
										  OAuthUserSocialService socialUserService,
										  String filterProcessesUrl) {
		super(filterProcessesUrl);
		Assert.notNull(clientRegistrationRepository, "clientRegistrationRepository cannot be null");
		Assert.notNull(authorizedClientRepository, "authorizedClientRepository cannot be null");
		Assert.notNull(socialUserService, "socialUserService cannot be null");
		this.clientRegistrationRepository = clientRegistrationRepository;
		this.authorizedClientRepository = authorizedClientRepository;
		this.socialUserService = socialUserService;
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {
		MultiValueMap<String, String> params = OAuth2AuthorizationResponseUtils.toMultiMap(request.getParameterMap());
		if (!OAuth2AuthorizationResponseUtils.isAuthorizationResponse(params)) {
			OAuth2Error oauth2Error = new OAuth2Error(OAuth2ErrorCodes.INVALID_REQUEST);
			throw new OAuth2AuthenticationException(oauth2Error, oauth2Error.toString());
		}
		OAuth2AuthorizationRequest authorizationRequest = this.authorizationRequestRepository
				.removeAuthorizationRequest(request, response);
		if (authorizationRequest == null) {
			OAuth2Error oauth2Error = new OAuth2Error(AUTHORIZATION_REQUEST_NOT_FOUND_ERROR_CODE);
			throw new OAuth2AuthenticationException(oauth2Error, oauth2Error.toString());
		}
		String registrationId = authorizationRequest.getAttribute(OAuth2ParameterNames.REGISTRATION_ID);
		ClientRegistration clientRegistration = this.clientRegistrationRepository.findByRegistrationId(registrationId);
		if (clientRegistration == null) {
			OAuth2Error oauth2Error = new OAuth2Error(CLIENT_REGISTRATION_NOT_FOUND_ERROR_CODE,
					"Client Registration not found with Id: " + registrationId, null);
			throw new OAuth2AuthenticationException(oauth2Error, oauth2Error.toString());
		}
		// @formatter:off
		String redirectUri = UriComponentsBuilder.fromHttpUrl(UrlUtils.buildFullRequestUrl(request))
				.replaceQuery(null)
				.build()
				.toUriString();
		// @formatter:on
		OAuth2AuthorizationResponse authorizationResponse = OAuth2AuthorizationResponseUtils.convert(params,
				redirectUri);
		Object authenticationDetails = this.authenticationDetailsSource.buildDetails(request);
		OAuth2BindAuthenticationToken authenticationRequest = new OAuth2BindAuthenticationToken(clientRegistration,
				new OAuth2AuthorizationExchange(authorizationRequest, authorizationResponse));
		authenticationRequest.setDetails(authenticationDetails);
		OAuth2BindAuthenticationToken authenticationResult = (OAuth2BindAuthenticationToken) this
				.getAuthenticationManager().authenticate(authenticationRequest);
		OAuth2AuthenticationToken oauth2Authentication = this.authenticationResultConverter
				.convert(authenticationResult);
		Assert.notNull(oauth2Authentication, "authentication result cannot be null");
		oauth2Authentication.setDetails(authenticationDetails);
		OAuth2AuthorizedClient authorizedClient = new OAuth2AuthorizedClient(
				authenticationResult.getClientRegistration(), oauth2Authentication.getName(),
				authenticationResult.getAccessToken(), authenticationResult.getRefreshToken());

		this.authorizedClientRepository.saveAuthorizedClient(authorizedClient, oauth2Authentication, request, response);

		// 执行绑定工作
		Map<String, Object> attributes = oauth2Authentication.getPrincipal().getAttributes();
		try {
			String userNameAttributeName = clientRegistration.getProviderDetails().getUserInfoEndpoint().getUserNameAttributeName();

			OAuth2UserSocialDO oAuth2UserSocialDO = new OAuth2UserSocialDO();
			if (SocialType.GITHUB.getRegistrationId().equals(registrationId)) {            // github 用户
				oAuth2UserSocialDO
						.setIdentityType(SocialType.GITHUB.getName())
						.setName(attributes.getOrDefault("name", "").toString())
						.setAvatarUrl(attributes.getOrDefault("avatar_url", "").toString())
				;
			}  else {
				throw new IllegalArgumentException("暂不支持该 " + registrationId + " 类型账号绑定");
			}
			oAuth2UserSocialDO
					.setIdentifier(attributes.getOrDefault(userNameAttributeName, "").toString())
					.setOther(JacksonUtils.toJSONString(attributes));

			socialUserService.bindSocialUser(oAuth2UserSocialDO);
		} catch (Exception e) {
			e.printStackTrace();
			OAuth2Error oauth2Error = new OAuth2Error(OAuth2ErrorCodes.SERVER_ERROR, "用户绑定失败！", null);
			throw new OAuth2AuthenticationException(oauth2Error, oauth2Error.toString());
		}
		return oauth2Authentication;
	}

	public final void setAuthorizationRequestRepository(
			AuthorizationRequestRepository<OAuth2AuthorizationRequest> authorizationRequestRepository) {
		Assert.notNull(authorizationRequestRepository, "authorizationRequestRepository cannot be null");
		this.authorizationRequestRepository = authorizationRequestRepository;
	}

	public final void setAuthenticationResultConverter(
			Converter<OAuth2BindAuthenticationToken, OAuth2AuthenticationToken> authenticationResultConverter) {
		Assert.notNull(authenticationResultConverter, "authenticationResultConverter cannot be null");
		this.authenticationResultConverter = authenticationResultConverter;
	}

	private OAuth2AuthenticationToken createAuthenticationResult(OAuth2BindAuthenticationToken authenticationResult) {
		return new OAuth2AuthenticationToken(authenticationResult.getPrincipal(), authenticationResult.getAuthorities(),
				authenticationResult.getClientRegistration().getRegistrationId());
	}

	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
//		SecurityContext context = SecurityContextHolder.createEmptyContext();
//		context.setAuthentication(authResult);
//		SecurityContextHolder.setContext(context);

		// todo: 合并信息，更新 context
//		SecurityContext context = SecurityContextHolder.getContext();
//		Authentication currentAuthentication = context.getAuthentication();
//		SecurityContext newContext = SecurityContextHolder.createEmptyContext();
//		Object principal = authResult.getPrincipal();
//		newContext.setAuthentication(authResult);
//		SecurityContextHolder.setContext(newContext);

		if (this.logger.isDebugEnabled()) {
			this.logger.debug(LogMessage.format("Set SecurityContextHolder to %s", authResult));
		}
//		this.rememberMeServices.loginSuccess(request, response, authResult);
//		if (this.eventPublisher != null) {
//			this.eventPublisher.publishEvent(new InteractiveAuthenticationSuccessEvent(authResult, this.getClass()));
//		}
//		this.successHandler.onAuthenticationSuccess(request, response, authResult);

		// 跳转到绑定页面，携带成功绑定信息
		request.getSession().setAttribute(SUCCESS_MESSAGE_ATTRIBUTE, "账号绑定成功");
		response.sendRedirect("/account?action=binding");
	}

	@Override
	protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
		this.logger.trace("Failed to process authentication request", failed);
		// 跳转到绑定页面，携带绑定失败回复
		request.getSession().removeAttribute(SUCCESS_MESSAGE_ATTRIBUTE);
		request.getSession().setAttribute(WebAttributes.AUTHENTICATION_EXCEPTION, failed);
		response.sendRedirect("/account?action=binding");
	}
}
