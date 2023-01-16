package org.shanzhaozhen.oauth.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Update;
import org.shanzhaozhen.common.core.entity.BasePageParams;
import org.shanzhaozhen.common.core.result.R;
import org.shanzhaozhen.oauth.converter.OAuth2RegisteredClientConverter;
import org.shanzhaozhen.oauth.pojo.dto.OAuth2RegisteredClientDTO;
import org.shanzhaozhen.oauth.pojo.form.OAuth2RegisteredClientForm;
import org.shanzhaozhen.oauth.pojo.vo.OAuth2RegisteredClientVO;
import org.shanzhaozhen.oauth.service.OAuth2RegisteredClientService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Tag(name = "OAuth2RegisteredClientController", description = "oauth2客户端信息接口")
@RestController
@RequiredArgsConstructor
public class OAuth2RegisteredClientController {

    private static final String GET_OAUTH2_REGISTERED_CLIENT_PAGE = "/registered-client/page";
    private static final String GET_OAUTH2_REGISTERED_CLIENT_BY_ID = "/registered-client/{registeredClientId}";
    private static final String ADD_OAUTH2_REGISTERED_CLIENT = "/registered-client";
    private static final String UPDATE_OAUTH2_REGISTERED_CLIENT = "/registered-client";
    private static final String DELETE_OAUTH2_REGISTERED_CLIENT = "/registered-client/{registeredClientId}";

    private final OAuth2RegisteredClientService oauth2RegisteredClientService;

    @Operation(summary = "获取 OAuth2 客户端信息分页结果")
    @PostMapping(value = GET_OAUTH2_REGISTERED_CLIENT_PAGE)
    public R<Page<OAuth2RegisteredClientVO>> getOAuth2RegisteredClientPage(@RequestBody BasePageParams<OAuth2RegisteredClientDTO> pageParams) {
        return R.build(() -> OAuth2RegisteredClientConverter.toVO(oauth2RegisteredClientService.getOAuth2RegisteredClientPage(pageParams.getPage(), pageParams.getKeyword())));
    }

    @Operation(summary = "通过 id 获取 OAuth2 客户端信息")
    @GetMapping(value = GET_OAUTH2_REGISTERED_CLIENT_BY_ID)
    public R<OAuth2RegisteredClientVO> getOAuth2RegisteredClientById(@PathVariable("registeredClientId") String registeredClientId) {
        return R.build(() -> OAuth2RegisteredClientConverter.toVO(oauth2RegisteredClientService.getOAuth2RegisteredClientByRegisteredClientId(registeredClientId)));
    }

    @Operation(summary = "添加 OAuth2 客户端信息")
    @PostMapping( ADD_OAUTH2_REGISTERED_CLIENT)
    public R<?> addOAuth2RegisteredClient(@RequestBody @Validated({Insert.class}) OAuth2RegisteredClientForm oauth2RegisteredClientForm) {
        oauth2RegisteredClientService.addOAuth2RegisteredClient(OAuth2RegisteredClientConverter.toDTO(oauth2RegisteredClientForm));
        return R.ok();
    }

    @Operation(summary = "更新 OAuth2 客户端信息")
    @PutMapping( UPDATE_OAUTH2_REGISTERED_CLIENT)
    public R<?> updateOAuth2RegisteredClient(@RequestBody @Validated({Update.class}) OAuth2RegisteredClientForm oauth2RegisteredClientForm) {
        oauth2RegisteredClientService.updateOAuth2RegisteredClient(OAuth2RegisteredClientConverter.toDTO(oauth2RegisteredClientForm));
        return R.ok();
    }

    @Operation(summary = "删除 OAuth2 客户端信息")
    @PutMapping( DELETE_OAUTH2_REGISTERED_CLIENT)
    public R<?> deleteOAuth2RegisteredClient(@PathVariable("registeredClientId") String registeredClientId) {
        oauth2RegisteredClientService.deleteOAuth2RegisteredClientByRegisteredClientId(registeredClientId);
        return R.ok();
    }

}
