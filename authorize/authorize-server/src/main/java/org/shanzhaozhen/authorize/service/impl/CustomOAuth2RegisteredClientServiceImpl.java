package org.shanzhaozhen.authorize.service.impl;

import cn.hutool.core.lang.Assert;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.RequiredArgsConstructor;
import org.shanzhaozhen.authorize.converter.OAuth2RegisteredClientConverter;
import org.shanzhaozhen.authorize.mapper.OAuth2RegisteredClientMapper;
import org.shanzhaozhen.authorize.pojo.dto.*;
import org.shanzhaozhen.authorize.pojo.entity.OAuth2RegisteredClientDO;
import org.shanzhaozhen.authorize.service.*;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;

import java.util.HashSet;
import java.util.List;

/**
 * @Author: shanzhaozhen
 * @Date: 2022-06-17
 * @Description:
 */
@RequiredArgsConstructor
public class CustomOAuth2RegisteredClientServiceImpl implements CustomOAuth2RegisteredClientService {

    private final OAuth2RegisteredClientMapper oAuth2RegisteredClientMapper;
    private final OAuth2ClientAuthenticationMethodService oAuth2ClientAuthenticationMethodService;
    private final OAuth2AuthorizationGrantTypeService oAuth2AuthorizationGrantTypeService;
    private final OAuth2RedirectUriService oAuth2RedirectUriService;
    private final OAuth2ScopeService oAuth2ScopeService;
    private final OAuth2ClientSettingsService oAuth2ClientSettingsService;
    private final OAuth2TokenSettingsService oAuth2TokenSettingsService;


    @Override
    public Page<OAuth2RegisteredClientDTO> getOAuth2RegisteredClientPage(Page<OAuth2RegisteredClientDTO> page, String keyword) {
        return oAuth2RegisteredClientMapper.getOAuth2RegisteredClientPage(page, keyword);
    }

    @Override
    public OAuth2RegisteredClientDTO getOAuth2RegisteredClientById(String id) {
        OAuth2RegisteredClientDTO oAuth2RegisteredClient = oAuth2RegisteredClientMapper.getOAuth2RegisteredClientById(id);
        if (oAuth2RegisteredClient != null) {
            this.assembleOAuth2RegisteredClient(oAuth2RegisteredClient);
        }
        return oAuth2RegisteredClient;
    }

    @Override
    public OAuth2RegisteredClientDTO getOAuth2RegisteredClientByClientId(String clientId) {
        OAuth2RegisteredClientDTO oAuth2RegisteredClient = oAuth2RegisteredClientMapper.getOAuth2RegisteredClientByClientId(clientId);
        if (oAuth2RegisteredClient != null) {
            this.assembleOAuth2RegisteredClient(oAuth2RegisteredClient);
        }
        return oAuth2RegisteredClient;
    }

    @Override
    public OAuth2RegisteredClientDTO assembleOAuth2RegisteredClient(OAuth2RegisteredClientDTO oAuth2RegisteredClientDTO) {
        Assert.notNull(oAuth2RegisteredClientDTO, "客户端信息不能为空");
        Assert.notEmpty(oAuth2RegisteredClientDTO.getId(), "客户端id不能为空");
        Assert.notEmpty(oAuth2RegisteredClientDTO.getClientId(), "客户端id不能为空");

        List<OAuth2ClientAuthenticationMethodDTO> clientAuthenticationMethod =
                oAuth2ClientAuthenticationMethodService.getOAuth2ClientAuthenticationMethodByClientId(oAuth2RegisteredClientDTO.getId());
        List<OAuth2AuthorizationGrantTypeDTO> authorizationGrantTypes =
                oAuth2AuthorizationGrantTypeService.getOAuth2AuthorizationGrantTypesByClientId(oAuth2RegisteredClientDTO.getId());
        List<OAuth2RedirectUriDTO> redirectUris =
                oAuth2RedirectUriService.getOAuth2RedirectUrisByClientId(oAuth2RegisteredClientDTO.getId());
        List<OAuth2ScopeDTO> scopes =
                oAuth2ScopeService.getOAuth2ScopesByClientId(oAuth2RegisteredClientDTO.getId());
        OAuth2ClientSettingsDTO clientSettings =
                oAuth2ClientSettingsService.getOAuth2ClientSettingsByClientId(oAuth2RegisteredClientDTO.getId());
        OAuth2TokenSettingsDTO tokenSetting =
                oAuth2TokenSettingsService.getOAuth2TokenSettingsByClientId(oAuth2RegisteredClientDTO.getId());

        oAuth2RegisteredClientDTO
                .setClientAuthenticationMethods(new HashSet<>(clientAuthenticationMethod))
                .setAuthorizationGrantTypes(new HashSet<>(authorizationGrantTypes))
                .setRedirectUris(new HashSet<>(redirectUris))
                .setScopes(new HashSet<>(scopes))
                .setClientSettings(clientSettings)
                .setTokenSettings(tokenSetting);

        return oAuth2RegisteredClientDTO;
    }

    @Override
    public String addOrUpdateOAuth2RegisteredClient(OAuth2RegisteredClientDTO oAuth2RegisteredClientDTO) {
        String clientId = oAuth2RegisteredClientDTO.getClientId();
        OAuth2RegisteredClientDTO oAuth2RegisteredInDB = this.oAuth2RegisteredClientMapper.getOAuth2RegisteredClientByClientId(clientId);
        if (oAuth2RegisteredInDB == null) {
            OAuth2RegisteredClientDO oAuth2RegisteredClientDO = OAuth2RegisteredClientConverter.toDO(oAuth2RegisteredClientDTO);
            this.oAuth2RegisteredClientMapper.insert(oAuth2RegisteredClientDO);
            String id = oAuth2RegisteredClientDO.getId();
            oAuth2ClientAuthenticationMethodService.addOAuth2ClientAuthenticationMethods(id, oAuth2RegisteredClientDTO.getClientAuthenticationMethods());
            oAuth2AuthorizationGrantTypeService.addOAuth2AuthorizationGrantTypes(id, oAuth2RegisteredClientDTO.getAuthorizationGrantTypes());
            oAuth2RedirectUriService.addOAuth2RedirectUris(id, oAuth2RegisteredClientDTO.getRedirectUris());
            oAuth2ScopeService.addOAuth2Scopes(id, oAuth2RegisteredClientDTO.getScopes());
            oAuth2ClientSettingsService.addOrUpdateOAuth2ClientSettings(id, oAuth2RegisteredClientDTO.getClientSettings());
            oAuth2TokenSettingsService.addOrUpdateOAuth2TokenSettings(id, oAuth2RegisteredClientDTO.getTokenSettings());
        } else {

        }

        return null;
    }

    @Override
    public String deleteOAuth2RegisteredClientById(String id) {
        return null;
    }

    @Override
    public String deleteOAuth2RegisteredClientByClientId(String id) {
        return null;
    }

    @Override
    public void save(RegisteredClient registeredClient) {
        addOrUpdateOAuth2RegisteredClient(OAuth2RegisteredClientConverter.toDTO(registeredClient));
    }

    @Override
    public RegisteredClient findById(String id) {
        OAuth2RegisteredClientDTO oAuth2RegisteredClient = this.getOAuth2RegisteredClientById(id);
        return OAuth2RegisteredClientConverter.toRegisteredClient(oAuth2RegisteredClient);
    }

    @Override
    public RegisteredClient findByClientId(String clientId) {
        OAuth2RegisteredClientDTO oAuth2RegisteredClient = this.getOAuth2RegisteredClientByClientId(clientId);
        return OAuth2RegisteredClientConverter.toRegisteredClient(oAuth2RegisteredClient);
    }
}
