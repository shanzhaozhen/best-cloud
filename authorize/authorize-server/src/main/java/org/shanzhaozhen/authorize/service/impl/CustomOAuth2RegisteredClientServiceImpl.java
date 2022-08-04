package org.shanzhaozhen.authorize.service.impl;

import cn.hutool.core.lang.Assert;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.RequiredArgsConstructor;
import org.shanzhaozhen.authorize.converter.OAuth2RegisteredClientConverter;
import org.shanzhaozhen.authorize.mapper.Oauth2RegisteredClientMapper;
import org.shanzhaozhen.authorize.pojo.dto.*;
import org.shanzhaozhen.authorize.pojo.entity.Oauth2RegisteredClientDO;
import org.shanzhaozhen.authorize.service.*;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;

import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author: shanzhaozhen
 * @Date: 2022-06-17
 * @Description:
 */
@RequiredArgsConstructor
public class CustomOAuth2RegisteredClientServiceImpl implements CustomOAuth2RegisteredClientService {

    private final Oauth2RegisteredClientMapper oauth2RegisteredClientMapper;
    private final OAuth2ClientAuthenticationMethodService oAuth2ClientAuthenticationMethodService;
    private final OAuth2AuthorizationGrantTypeService oAuth2AuthorizationGrantTypeService;
    private final Oauth2RedirectUriService oauth2RedirectUriService;
    private final Oauth2ScopeService oauth2ScopeService;
    private final Oauth2ClientSettingsService oauth2ClientSettingsService;
    private final Oauth2TokenSettingsService oauth2TokenSettingsService;


    @Override
    public Page<Oauth2RegisteredClientDTO> getOauth2RegisteredClientPage(Page<Oauth2RegisteredClientDTO> page, String keyword) {
        return oauth2RegisteredClientMapper.getOauth2RegisteredClientPage(page, keyword);
    }

    @Override
    public Oauth2RegisteredClientDTO getOauth2RegisteredClientById(String id) {
        return oauth2RegisteredClientMapper.getOauth2RegisteredClientById(id);
    }

    @Override
    public Oauth2RegisteredClientDTO getOauth2RegisteredClientByClientId(String clientId) {
        return oauth2RegisteredClientMapper.getOauth2RegisteredClientByClientId(clientId);
    }

    @Override
    public Oauth2RegisteredClientDTO assembleOauth2RegisteredClient(Oauth2RegisteredClientDTO oauth2RegisteredClientDTO) {
        Assert.notNull(oauth2RegisteredClientDTO, "客户端信息不能为空");
        Assert.notEmpty(oauth2RegisteredClientDTO.getId(), "客户端id不能为空");
        Assert.notEmpty(oauth2RegisteredClientDTO.getClientId(), "客户端id不能为空");

        List<OAuth2ClientAuthenticationMethodDTO> clientAuthenticationMethod =
                oAuth2ClientAuthenticationMethodService.getOAuth2ClientAuthenticationMethodByClientId(oauth2RegisteredClientDTO.getId());
        List<OAuth2AuthorizationGrantTypeDTO> authorizationGrantTypes =
                oAuth2AuthorizationGrantTypeService.getOAuth2AuthorizationGrantTypeByClientId(oauth2RegisteredClientDTO.getId());
        List<OAuth2RedirectUriDTO> redirectUris =
                oauth2RedirectUriService.getOauth2RedirectUriByClientId(oauth2RegisteredClientDTO.getId());
        List<OAuth2ScopeDTO> scopes =
                oauth2ScopeService.getOauth2ScopeByClientId(oauth2RegisteredClientDTO.getId());
        OAuth2ClientSettingsDTO clientSettings =
                oauth2ClientSettingsService.getOauth2ClientSettingsByClientId(oauth2RegisteredClientDTO.getId());
        OAuth2TokenSettingsDTO tokenSetting =
                oauth2TokenSettingsService.getOauth2TokenSettingByClientId(oauth2RegisteredClientDTO.getId());

        oauth2RegisteredClientDTO
                .setClientAuthenticationMethods(new HashSet<>(clientAuthenticationMethod))
                .setAuthorizationGrantTypes(new HashSet<>(authorizationGrantTypes))
                .setRedirectUris(new HashSet<>(redirectUris))
                .setScopes(new HashSet<>(scopes))
                .setClientSettings(clientSettings)
                .setTokenSettings(tokenSetting);

        return oauth2RegisteredClientDTO;
    }

    @Override
    public String addOrUpdateOauth2RegisteredClient(Oauth2RegisteredClientDTO oauth2RegisteredClientDTO) {
        return null;
    }

    @Override
    public String deleteOauth2RegisteredClientById(String id) {
        return null;
    }

    @Override
    public String deleteOauth2RegisteredClientByClientId(String id) {
        return null;
    }

    @Override
    public void save(RegisteredClient registeredClient) {
        addOrUpdateOauth2RegisteredClient(OAuth2RegisteredClientConverter.toDTO(registeredClient));
    }

    @Override
    public RegisteredClient findById(String id) {
        Oauth2RegisteredClientDTO oauth2RegisteredClient = this.getOauth2RegisteredClientById(id);
        this.assembleOauth2RegisteredClient(oauth2RegisteredClient);
        return OAuth2RegisteredClientConverter.toRegisteredClient(oauth2RegisteredClient);
    }

    @Override
    public RegisteredClient findByClientId(String clientId) {
        Oauth2RegisteredClientDTO oauth2RegisteredClient = this.getOauth2RegisteredClientByClientId(clientId);
        this.assembleOauth2RegisteredClient(oauth2RegisteredClient);
        return OAuth2RegisteredClientConverter.toRegisteredClient(oauth2RegisteredClient);
    }
}
