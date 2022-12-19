package org.shanzhaozhen.authorize.controller;

import lombok.RequiredArgsConstructor;
import org.shanzhaozhen.common.core.utils.JacksonUtils;
import org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationConsent;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationConsentService;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.settings.AuthorizationServerSettings;
import org.springframework.security.oauth2.server.authorization.web.OAuth2AuthorizationEndpointFilter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.*;

/**
 * @Author: shanzhaozhen
 * @Date: 2022-06-17
 * @Description: 自定义用户确认页
 */
@Controller
@RequiredArgsConstructor
public class AuthorizationConsentController {
	private final RegisteredClientRepository registeredClientRepository;
	private final OAuth2AuthorizationConsentService authorizationConsentService;
	private final AuthorizationServerSettings authorizationServerSettings;

	/**
     * {@link OAuth2AuthorizationEndpointFilter} 会302重定向到{@code  /oauth2/consent}并携带入参
     *
     * @param principal 当前用户
     * @param model     视图模型
     * @param clientId  oauth2 client id
     * @param scope     请求授权的scope
     * @param state     state 值
     * @return 自定义授权确认页面 consent.html
     */
    @GetMapping(value = "/oauth2/consent")
    public String consent(Principal principal, Model model,
                          @RequestParam(OAuth2ParameterNames.CLIENT_ID) String clientId,
                          @RequestParam(OAuth2ParameterNames.SCOPE) String scope,
                          @RequestParam(OAuth2ParameterNames.STATE) String state) {

		// Remove scopes that were already approved
		Set<String> scopesToApprove = new HashSet<>();
		Set<String> previouslyApprovedScopes = new HashSet<>();
		RegisteredClient registeredClient = this.registeredClientRepository.findByClientId(clientId);

		Assert.notNull(registeredClient, "oauth2 客户端不存在");

		OAuth2AuthorizationConsent currentAuthorizationConsent = this.authorizationConsentService.findById(registeredClient.getId(), principal.getName());
		Set<String> authorizedScopes;
		if (currentAuthorizationConsent != null) {
			authorizedScopes = currentAuthorizationConsent.getScopes();
		} else {
			authorizedScopes = Collections.emptySet();
		}
		for (String requestedScope : StringUtils.delimitedListToStringArray(scope, " ")) {
			if (authorizedScopes.contains(requestedScope)) {
				previouslyApprovedScopes.add(requestedScope);
			} else {
				scopesToApprove.add(requestedScope);
			}
		}

		Map<String, Object> consentInfo = new HashMap<>();
		consentInfo.put("authorizationEndpoint", authorizationServerSettings.getAuthorizationEndpoint());
		consentInfo.put("clientId", clientId);
		consentInfo.put("clientName", registeredClient.getClientName());
		consentInfo.put("state", state);
		consentInfo.put("scopes", withDescription(scopesToApprove));
		consentInfo.put("previouslyApprovedScopes", withDescription(previouslyApprovedScopes));
		consentInfo.put("principalName", principal.getName());

		model.addAttribute("mvcModel", consentInfo);
		return "front/index";
	}

	/**
	 * 给 scops 增加描述
	 * @param scopes
	 * @return
	 */
	private static Set<ScopeWithDescription> withDescription(Set<String> scopes) {
		Set<ScopeWithDescription> scopeWithDescriptions = new HashSet<>();
		for (String scope : scopes) {
			scopeWithDescriptions.add(new ScopeWithDescription(scope));

		}
		return scopeWithDescriptions;
	}

	public static class ScopeWithDescription {
		private static final String DEFAULT_DESCRIPTION = "未知";
		private static final Map<String, String> scopeDescriptions = new HashMap<>();
		static {
			scopeDescriptions.put(
					"openid",
					"用户信息"
			);
			scopeDescriptions.put(
					"message.read",
					"信息读取"
			);
			scopeDescriptions.put(
					"message.write",
					"信息修改"
			);
			scopeDescriptions.put(
					"other.scope",
					"其他"
			);
		}

		public final String scope;
		public final String description;

		ScopeWithDescription(String scope) {
			this.scope = scope;
			this.description = scopeDescriptions.getOrDefault(scope, DEFAULT_DESCRIPTION);
		}
	}
}
