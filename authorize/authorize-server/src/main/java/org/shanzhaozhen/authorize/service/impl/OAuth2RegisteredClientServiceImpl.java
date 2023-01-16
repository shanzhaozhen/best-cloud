package org.shanzhaozhen.authorize.service.impl;

import lombok.RequiredArgsConstructor;
import org.shanzhaozhen.authorize.converter.OAuth2RegisteredClientConverter;
import org.shanzhaozhen.authorize.mapper.OAuth2RegisteredClientMapper;
import org.shanzhaozhen.authorize.service.OAuth2ClientSettingsService;
import org.shanzhaozhen.authorize.service.OAuth2RegisteredClientService;
import org.shanzhaozhen.authorize.service.OAuth2TokenSettingsService;
import org.shanzhaozhen.common.core.utils.CustomBeanUtils;
import org.shanzhaozhen.oauth.pojo.dto.OAuth2ClientSettingsDTO;
import org.shanzhaozhen.oauth.pojo.dto.OAuth2RegisteredClientDTO;
import org.shanzhaozhen.oauth.pojo.dto.OAuth2TokenSettingsDTO;
import org.shanzhaozhen.oauth.pojo.entity.OAuth2RegisteredClientDO;
import org.shanzhaozhen.oauth.pojo.form.OAuth2ClientSettingsForm;
import org.shanzhaozhen.oauth.pojo.form.OAuth2RegisteredClientForm;
import org.shanzhaozhen.oauth.pojo.form.OAuth2TokenSettingsForm;
import org.springframework.beans.BeanUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;
import java.util.UUID;


/**
 * @Author: shanzhaozhen
 * @Date: 2022-06-17
 * @Description:
 */
@Service
@RequiredArgsConstructor
public class OAuth2RegisteredClientServiceImpl implements OAuth2RegisteredClientService {

    private final OAuth2RegisteredClientMapper oauth2RegisteredClientMapper;
    private final OAuth2ClientSettingsService oauth2ClientSettingsService;
    private final OAuth2TokenSettingsService oauth2TokenSettingsService;
    private final PasswordEncoder passwordEncoder;


    @Override
    public void save(RegisteredClient registeredClient) {
        this.addOrUpdateOAuth2RegisteredClient(OAuth2RegisteredClientConverter.toDTO(registeredClient));
    }

    @Override
    public RegisteredClient findById(String id) {
        OAuth2RegisteredClientDTO oauth2RegisteredClientDTO = this.getOAuth2RegisteredClientById(id);
        return OAuth2RegisteredClientConverter.toRegisteredClient(oauth2RegisteredClientDTO);
    }

    @Override
    public RegisteredClient findByClientId(String clientId) {
        OAuth2RegisteredClientDTO oauth2RegisteredClientDTO = this.getOAuth2RegisteredClientByClientId(clientId);
        return OAuth2RegisteredClientConverter.toRegisteredClient(oauth2RegisteredClientDTO);
    }

    public OAuth2RegisteredClientDTO getOAuth2RegisteredClientById(@RequestParam("id") String id) {
        OAuth2RegisteredClientDO oauth2RegisteredClientDO = oauth2RegisteredClientMapper.selectById(id);

        if (oauth2RegisteredClientDO != null) {
            OAuth2RegisteredClientDTO oauth2RegisteredClientDTO = new OAuth2RegisteredClientDTO();
            BeanUtils.copyProperties(oauth2RegisteredClientDO, oauth2RegisteredClientDTO);
            return this.assembleOAuth2RegisteredClient(oauth2RegisteredClientDTO);
        }
        return null;
    }

    @Override
    public OAuth2RegisteredClientDTO getOAuth2RegisteredClientByClientId(@RequestParam("clientId") String clientId) {
        OAuth2RegisteredClientDO oauth2RegisteredClientDO = oauth2RegisteredClientMapper.getOAuth2RegisteredClientByClientId(clientId);
        if (oauth2RegisteredClientDO != null) {
            OAuth2RegisteredClientDTO oauth2RegisteredClientDTO = new OAuth2RegisteredClientDTO();
            BeanUtils.copyProperties(oauth2RegisteredClientDO, oauth2RegisteredClientDTO);
            return this.assembleOAuth2RegisteredClient(oauth2RegisteredClientDTO);
        }
        return null;
    }

    @Override
    public OAuth2RegisteredClientDTO assembleOAuth2RegisteredClient(OAuth2RegisteredClientDTO oauth2RegisteredClientDTO) {
        Assert.notNull(oauth2RegisteredClientDTO, "客户端信息不能为空");
        Assert.hasText(oauth2RegisteredClientDTO.getId(), "客户端id不能为空");
        Assert.hasText(oauth2RegisteredClientDTO.getClientId(), "客户端id不能为空");

        OAuth2ClientSettingsDTO clientSettings =
                oauth2ClientSettingsService.getOAuth2ClientSettingsByRegisteredClientId(oauth2RegisteredClientDTO.getId());
        OAuth2TokenSettingsDTO tokenSetting =
                oauth2TokenSettingsService.getOAuth2TokenSettingsByRegisteredClientId(oauth2RegisteredClientDTO.getId());

        return oauth2RegisteredClientDTO
                .setClientSettings(clientSettings)
                .setTokenSettings(tokenSetting);
    }

    @Override
    public void addOrUpdateOAuth2RegisteredClient(OAuth2RegisteredClientDTO oauth2RegisteredClientDTO) {
        String clientId = oauth2RegisteredClientDTO.getClientId();
        OAuth2RegisteredClientDO oauth2RegisteredClient;
        if (StringUtils.hasText(clientId)) {
            oauth2RegisteredClient = this.oauth2RegisteredClientMapper.getOAuth2RegisteredClientByClientId(clientId);
        } else {
            clientId = UUID.randomUUID().toString();
            oauth2RegisteredClient = null;
        }

        if (oauth2RegisteredClient == null) {
            oauth2RegisteredClient = new OAuth2RegisteredClientDO();
            BeanUtils.copyProperties(oauth2RegisteredClientDTO, oauth2RegisteredClient);
            oauth2RegisteredClient.setClientId(clientId);
            String encodePassword = passwordEncoder.encode(oauth2RegisteredClientDTO.getClientSecret());
            oauth2RegisteredClient.setClientSecret(encodePassword);
            this.oauth2RegisteredClientMapper.insert(oauth2RegisteredClient);
        } else {
            if (StringUtils.hasText(oauth2RegisteredClientDTO.getClientSecret())) {
                String encodePassword = passwordEncoder.encode(oauth2RegisteredClientDTO.getClientSecret());
                CustomBeanUtils.copyPropertiesExcludeMetaAndNull(oauth2RegisteredClientDTO, oauth2RegisteredClient);
                oauth2RegisteredClient.setClientSecret(encodePassword);
            } else {        // 为空不修改密码
                CustomBeanUtils.copyPropertiesExcludeMetaAndNull(oauth2RegisteredClientDTO, oauth2RegisteredClient);
            }
            this.oauth2RegisteredClientMapper.updateById(oauth2RegisteredClient);
        }
        if (oauth2RegisteredClientDTO.getClientSettings() != null) {
            oauth2ClientSettingsService.addOrUpdateOAuth2ClientSettings(oauth2RegisteredClient.getId(), oauth2RegisteredClientDTO.getClientSettings());
        }

        if (oauth2RegisteredClientDTO.getTokenSettings() != null) {
            oauth2TokenSettingsService.addOrUpdateOAuth2TokenSettings(oauth2RegisteredClient.getId(), oauth2RegisteredClientDTO.getTokenSettings());
        }
    }

    @Override
    @Transactional
    public void deleteOAuth2RegisteredClientById(String id) {
        this.oauth2RegisteredClientMapper.deleteOAuth2RegisteredClientById(id);
        oauth2ClientSettingsService.deleteOAuth2ClientSettingsByRegisteredClientId(id);
        oauth2TokenSettingsService.deleteOAuth2TokenSettingsByRegisteredClientId(id);
    }

}
