package org.shanzhaozhen.authorize.authentication.federated;

import org.shanzhaozhen.authorize.authentication.LoginSuccessHandler;
import org.shanzhaozhen.authorize.constant.SocialType;
import org.shanzhaozhen.authorize.pojo.dto.AuthUser;
import org.shanzhaozhen.authorize.service.OAuthUserSocialService;
import org.shanzhaozhen.oauth.pojo.dto.OAuth2UserDTO;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.function.Consumer;


public final class FederatedIdentityAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

	private final AuthenticationSuccessHandler delegate = new LoginSuccessHandler();

	private final OAuthUserSocialService socialUserService;

	private Consumer<OAuth2User> oauth2UserHandler = (user) -> {};

	private Consumer<OidcUser> oidcUserHandler = (user) -> this.oauth2UserHandler.accept(user);

	public FederatedIdentityAuthenticationSuccessHandler(OAuthUserSocialService socialUserService) {
		this.socialUserService = socialUserService;
	}

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
		if (authentication instanceof OAuth2AuthenticationToken) {
			if (authentication.getPrincipal() instanceof OidcUser) {
				this.oidcUserHandler.accept((OidcUser) authentication.getPrincipal());
			} else if (authentication.getPrincipal() instanceof DefaultOAuth2User) {
				DefaultOAuth2User principal = (DefaultOAuth2User) authentication.getPrincipal();
				String registrationId = ((OAuth2AuthenticationToken) authentication).getAuthorizedClientRegistrationId();
				String identifier = principal.getName();

				OAuth2UserDTO user;
				SocialType socialType = null;
				if (SocialType.GITHUB.getRegistrationId().equals(registrationId)) {
					socialType = SocialType.GITHUB;
				}
				if (socialType == null) {
					SecurityContextHolder.clearContext();
					throw new BadCredentialsException("不支持该类型登陆!");
//					String redirectUrl = response.encodeRedirectURL("/login?error&msg=不支持该类型登陆");
//					response.sendRedirect(redirectUrl);
//					return;
				}
				try {
					user = socialUserService.loadUserBySocial(socialType.getName(), identifier);
				} catch (Exception e) {
					e.printStackTrace();
					throw new BadCredentialsException("用户获取过程出现异常!");
				}

				if (user == null) {
					// 没有绑定的用户跳转到绑定页面
					response.sendRedirect("/social-bind");
					return;
				} else {
					AuthUser authUser = new AuthUser(user);
					FederatedIdentityAuthenticationToken federatedIdentityAuthenticationToken = new FederatedIdentityAuthenticationToken(authUser, authUser.getAuthorities(), registrationId);
					federatedIdentityAuthenticationToken.setDetails(authentication.getDetails());
					SecurityContext context = SecurityContextHolder.createEmptyContext();
					context.setAuthentication(federatedIdentityAuthenticationToken);
					SecurityContextHolder.setContext(context);
				}
			}
		}

		this.delegate.onAuthenticationSuccess(request, response, authentication);
	}

	public void setOAuth2UserHandler(Consumer<OAuth2User> oauth2UserHandler) {
		this.oauth2UserHandler = oauth2UserHandler;
	}

	public void setOidcUserHandler(Consumer<OidcUser> oidcUserHandler) {
		this.oidcUserHandler = oidcUserHandler;
	}

}
