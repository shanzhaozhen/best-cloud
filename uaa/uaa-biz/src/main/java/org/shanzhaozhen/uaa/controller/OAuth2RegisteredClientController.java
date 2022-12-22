package org.shanzhaozhen.uaa.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.shanzhaozhen.common.core.entity.BasePageParams;
import org.shanzhaozhen.common.core.result.R;
import org.shanzhaozhen.uaa.pojo.dto.OAuth2RegisteredClientDTO;
import org.shanzhaozhen.uaa.pojo.form.OAuth2RegisteredClientForm;
import org.shanzhaozhen.uaa.service.OAuth2RegisteredClientService;
import org.springframework.web.bind.annotation.*;

@Tag(name = "OAuth2RegisteredClient", description = "oauth2客户端信息接口")
@RestController
@RequiredArgsConstructor
public class OAuth2RegisteredClientController {

    private static final String GET_OAUTH2_REGISTERED_CLIENT_PAGE = "/oauth2/registered-client/page";
    private static final String GET_OAUTH2_REGISTERED_CLIENT_BY_ID = "/oauth2/registered-client";
    private static final String GET_OAUTH2_REGISTERED_CLIENT_BY_CLIENT_ID = "/oauth2/registered-client";
    private static final String SAVE_OAUTH2_REGISTERED_CLIENT = "/oauth2/registered-client";

    private final OAuth2RegisteredClientService oauth2RegisteredClientService;

    @Operation(summary = "获取 OAuth2 客户端信息分页结果")
    @PostMapping(value = GET_OAUTH2_REGISTERED_CLIENT_PAGE)
    public R<Page<OAuth2RegisteredClientDTO>> getOAuth2RegisteredClientPage(@RequestBody BasePageParams<OAuth2RegisteredClientDTO> pageParams) {
        return R.build(() -> oauth2RegisteredClientService.getOAuth2RegisteredClientPage(pageParams.getPage(), pageParams.getKeyword()));
    }

    @Operation(summary = "通过 id 获取 OAuth2 客户端信息")
    @GetMapping(value = GET_OAUTH2_REGISTERED_CLIENT_BY_ID, params = { "id" })
    public R<OAuth2RegisteredClientDTO> getOAuth2RegisteredClientById(@RequestParam("id") String id) {
        return R.build(() -> oauth2RegisteredClientService.getOAuth2RegisteredClientById(id));
    }

    @Operation(summary = "通过 clientId 获取 OAuth2 客户端信息")
    @GetMapping(value = GET_OAUTH2_REGISTERED_CLIENT_BY_CLIENT_ID, params = { "clientId" })
    public R<OAuth2RegisteredClientDTO> getOAuth2RegisteredClientByClientId(@RequestParam("clientId") String clientId) {
        return R.build(() -> oauth2RegisteredClientService.getOAuth2RegisteredClientByClientId(clientId));
    }

    @Operation(summary = "保存 OAuth2 客户端信息")
    @PostMapping( SAVE_OAUTH2_REGISTERED_CLIENT)
    public R<?> addOAuth2RegisteredClient(@RequestBody OAuth2RegisteredClientForm oauth2RegisteredClientForm) {
        oauth2RegisteredClientService.addOrUpdateOAuth2RegisteredClient(oauth2RegisteredClientForm);
        return R.ok();
    }

    @Operation(summary = "保存 OAuth2 客户端信息")
    @PutMapping( SAVE_OAUTH2_REGISTERED_CLIENT)
    public R<?> updateOAuth2RegisteredClient(@RequestBody OAuth2RegisteredClientForm oauth2RegisteredClientForm) {
        oauth2RegisteredClientService.addOrUpdateOAuth2RegisteredClient(oauth2RegisteredClientForm);
        return R.ok();
    }

}
