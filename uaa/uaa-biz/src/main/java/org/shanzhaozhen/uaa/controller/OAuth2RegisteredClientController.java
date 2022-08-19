package org.shanzhaozhen.uaa.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.shanzhaozhen.common.core.result.R;
import org.shanzhaozhen.uaa.pojo.dto.OAuth2RegisteredClientDTO;
import org.shanzhaozhen.uaa.service.OAuth2RegisteredClientService;
import org.springframework.web.bind.annotation.*;

@Tag(name = "OAuth2RegisteredClient", description = "oauth2客户端信息接口")
@RestController
@RequiredArgsConstructor
public class OAuth2RegisteredClientController {

    private static final String GET_OAUTH2_REGISTERED_CLIENT_BY_ID = "/oauth2/registered-client";
    private static final String GET_OAUTH2_REGISTERED_CLIENT_BY_CLIENT_ID = "/oauth2/registered-client";
    private static final String SAVE_OAUTH2_REGISTERED_CLIENT = "/oauth2/registered-client";

    private final OAuth2RegisteredClientService oAuth2RegisteredClientService;

    @Operation(summary = "通过 id 获取 OAuth2 客户端信息")
    @GetMapping(value = GET_OAUTH2_REGISTERED_CLIENT_BY_ID, params = { "id" })
    public OAuth2RegisteredClientDTO getOAuth2RegisteredClientById(@RequestParam("id") String id) {
        return oAuth2RegisteredClientService.getOAuth2RegisteredClientById(id);
    }

    @Operation(summary = "通过 clientId 获取 OAuth2 客户端信息")
    @GetMapping(value = GET_OAUTH2_REGISTERED_CLIENT_BY_CLIENT_ID, params = { "clientId" })
    public OAuth2RegisteredClientDTO getOAuth2RegisteredClientByClientId(@RequestParam("clientId") String clientId) {
        return oAuth2RegisteredClientService.getOAuth2RegisteredClientByClientId(clientId);
    }

    @Operation(summary = "保存 OAuth2 客户端信息")
    @PostMapping( SAVE_OAUTH2_REGISTERED_CLIENT)
    public R<?> saveOAuth2RegisteredClient(@RequestBody OAuth2RegisteredClientDTO oAuth2RegisteredClientDTO) {
        oAuth2RegisteredClientService.addOrUpdateOAuth2RegisteredClient(oAuth2RegisteredClientDTO);
        return R.ok();
    }

}
