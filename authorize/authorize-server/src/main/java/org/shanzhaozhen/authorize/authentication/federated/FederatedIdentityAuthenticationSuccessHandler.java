package org.shanzhaozhen.authorize.authentication.federated;

import feign.FeignException;
import org.shanzhaozhen.common.core.result.R;
import org.shanzhaozhen.common.core.utils.JacksonUtils;
import org.shanzhaozhen.uaa.constant.SocialType;
import org.shanzhaozhen.uaa.feign.SocialUserFeignClient;
import org.shanzhaozhen.uaa.feign.UserFeignClient;
import org.shanzhaozhen.uaa.pojo.dto.AuthUser;
import org.shanzhaozhen.uaa.pojo.dto.UserDTO;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.Collection;
import java.util.function.Consumer;

/**
 * An {@link AuthenticationSuccessHandler} for capturing the {@link OidcUser} or
 * {@link OAuth2User} for Federated Account Linking or JIT Account Provisioning.
 *
 * @author Steve Riesenberg
 * @since 0.2.3
 */
public final class FederatedIdentityAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

	private final AuthenticationSuccessHandler delegate = new SavedRequestAwareAuthenticationSuccessHandler();

	private final SocialUserFeignClient socialUserFeignClient;

	private Consumer<OAuth2User> oauth2UserHandler = (user) -> {};

	private Consumer<OidcUser> oidcUserHandler = (user) -> this.oauth2UserHandler.accept(user);

	public FederatedIdentityAuthenticationSuccessHandler(SocialUserFeignClient socialUserFeignClient) {
		this.socialUserFeignClient = socialUserFeignClient;
	}

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
		if (authentication instanceof OAuth2AuthenticationToken) {
			if (authentication.getPrincipal() instanceof OidcUser) {
				this.oidcUserHandler.accept((OidcUser) authentication.getPrincipal());
			} else if (authentication.getPrincipal() instanceof DefaultOAuth2User) {
				DefaultOAuth2User principal = (DefaultOAuth2User) authentication.getPrincipal();
				String registrationId = ((OAuth2AuthenticationToken) authentication).getAuthorizedClientRegistrationId();
				String username = principal.getName();

				UserDTO user;
				SocialType socialType = null;
				if (SocialType.GITHUB.getRegistrationId().equals(registrationId)) {
					socialType = SocialType.GITHUB;
				}
				if (socialType == null) {
					SecurityContextHolder.clearContext();
					String redirectUrl = response.encodeRedirectURL("/login?error&msg=不支持该类型登陆");
					response.sendRedirect(redirectUrl);
					return;
				}
				try {
					R<UserDTO> result = socialUserFeignClient.loadUserBySocial(username, socialType.getName());
					user = result.getData();
				} catch (FeignException e) {
					e.printStackTrace();
					R<?> result = JacksonUtils.toPojo(e.contentUTF8(), R.class);
					throw new BadCredentialsException(result.getMessage());
				} catch (Exception e) {
					e.printStackTrace();
					throw new BadCredentialsException("用户获取过程出现异常!");
				}

				if (user == null) {
					// 没有绑定的用户跳转到绑定页面
					response.sendRedirect("/register-social");
					return;
				} else {
					AuthUser authUser = new AuthUser(user);
					UsernamePasswordAuthenticationToken newAuthentication = new UsernamePasswordAuthenticationToken(authUser, null,
							authUser.getAuthorities());
					newAuthentication.setDetails(authentication.getDetails());
					SecurityContext context = SecurityContextHolder.createEmptyContext();
					context.setAuthentication(newAuthentication);
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
