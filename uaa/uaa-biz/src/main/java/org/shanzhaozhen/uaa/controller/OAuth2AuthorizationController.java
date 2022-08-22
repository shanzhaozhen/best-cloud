package org.shanzhaozhen.uaa.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.shanzhaozhen.common.core.result.R;
import org.shanzhaozhen.uaa.pojo.dto.OAuth2AuthorizationDTO;
import org.shanzhaozhen.uaa.service.OAuth2AuthorizationService;
import org.springframework.web.bind.annotation.*;

@Tag(name = "OAuth2Authorization", description = "oauth2客户端信息接口")
@RestController
@RequiredArgsConstructor
public class OAuth2AuthorizationController {

    private static final String GET_OAUTH2_AUTHORIZATION_BY_ID = "/oauth2/authorization";
    private static final String GET_OAUTH2_AUTHORIZATION_BY_TOKEN = "/oauth2/authorization";
    private static final String SAVE_OAUTH2_AUTHORIZATION = "/oauth2/authorization";
    private static final String DELETE_OAUTH2_AUTHORIZATION_BY_ID = "/oauth2/authorization/{id}";

    private final OAuth2AuthorizationService oAuth2AuthorizationService;

    @Operation(summary = "通过 token 获取用户授权信息")
    @GetMapping(value = GET_OAUTH2_AUTHORIZATION_BY_ID, params = { "id" })
    public OAuth2AuthorizationDTO getOAuth2AuthorizationById(@RequestParam("id") String id) {
        return oAuth2AuthorizationService.getOAuth2AuthorizationById(id);
    }

    @Operation(summary = "通过 token 获取用户授权信息")
    @GetMapping(value = GET_OAUTH2_AUTHORIZATION_BY_TOKEN, params = { "token" })
    public OAuth2AuthorizationDTO getOAuth2AuthorizationByToken(@RequestParam("token") String token, @RequestParam("tokenType") String tokenType) {
        return oAuth2AuthorizationService.getOAuth2AuthorizationByToken(token, tokenType);
    }

    @Operation(summary = "保存用户授权信息")
    @PostMapping(value = SAVE_OAUTH2_AUTHORIZATION)
    public R<?> saveOAuth2Authorization(@RequestBody OAuth2AuthorizationDTO oAuth2AuthorizationDTO) {
        oAuth2AuthorizationService.saveOAuth2Authorization(oAuth2AuthorizationDTO);
        return R.ok();
    }

    @Operation(summary = "通过 id 删除用户授权信息")
    @DeleteMapping(value = DELETE_OAUTH2_AUTHORIZATION_BY_ID)
    public R<?> deleteOAuth2AuthorizationById(@PathVariable("id") String id) {
        oAuth2AuthorizationService.deleteOAuth2AuthorizationById(id);
        return R.ok();
    }

}
