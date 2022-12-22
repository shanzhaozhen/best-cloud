package org.shanzhaozhen.uaa.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.shanzhaozhen.common.core.result.R;
import org.shanzhaozhen.uaa.pojo.dto.OAuth2AuthorizationConsentDTO;
import org.shanzhaozhen.uaa.service.OAuth2AuthorizationConsentService;
import org.springframework.web.bind.annotation.*;

@Tag(name = "OAuth2AuthorizationConsent", description = "oauth2客户端信息接口")
@RestController
@RequiredArgsConstructor
public class OAuth2AuthorizationConsentController {

    private static final String GET_OAUTH2_AUTHORIZATION_CONSENT = "/oauth2/authorization-consent";
    private static final String SAVE_OAUTH2_AUTHORIZATION_CONSENT = "/oauth2/authorization-consent";
    private static final String DELETE_OAUTH2_AUTHORIZATION_CONSENT = "/oauth2/authorization-consent";

    private final OAuth2AuthorizationConsentService oauth2AuthorizationConsentService;

    @Operation(summary = "获取用户授权范围信息")
    @GetMapping(GET_OAUTH2_AUTHORIZATION_CONSENT)
    public OAuth2AuthorizationConsentDTO getOAuth2AuthorizationConsent(@RequestParam("registeredClientId") String registeredClientId, @RequestParam("principalName") String principalName) {
        return oauth2AuthorizationConsentService.getOAuth2AuthorizationConsent(registeredClientId, principalName);
    }

    @Operation(summary = "保存用户授权范围信息")
    @PostMapping(SAVE_OAUTH2_AUTHORIZATION_CONSENT)
    public R<?> saveOAuth2AuthorizationConsent(@RequestBody OAuth2AuthorizationConsentDTO oauth2AuthorizationConsentDTO) {
        oauth2AuthorizationConsentService.addOrUpdateOAuth2AuthorizationConsent(oauth2AuthorizationConsentDTO);
        return R.ok();
    }

    @Operation(summary = "删除用户授权范围信息")
    @DeleteMapping(DELETE_OAUTH2_AUTHORIZATION_CONSENT)
    public R<?> deleteOAuth2AuthorizationConsent(@RequestParam("registeredClientId") String registeredClientId, @RequestParam("principalName") String principalName) {
        oauth2AuthorizationConsentService.deleteOAuth2AuthorizationConsent(registeredClientId, principalName);
        return R.ok();
    }

}
