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
        OAuth2RegisteredClientDO oAuth2RegisteredClientDO = oauth2RegisteredClientMapper.selectById(id);

        if (oAuth2RegisteredClientDO != null) {
            OAuth2RegisteredClientDTO oAuth2RegisteredClientDTO = new OAuth2RegisteredClientDTO();
            BeanUtils.copyProperties(oAuth2RegisteredClientDO, oAuth2RegisteredClientDTO);
            return this.assembleOAuth2RegisteredClient(oAuth2RegisteredClientDTO);
        }
        return null;
    }

    @Override
    public OAuth2RegisteredClientDTO getOAuth2RegisteredClientByClientId(@RequestParam("clientId") String clientId) {
        OAuth2RegisteredClientDO oAuth2RegisteredClientDO = oauth2RegisteredClientMapper.getOAuth2RegisteredClientByClientId(clientId);
        if (oAuth2RegisteredClientDO != null) {
            OAuth2RegisteredClientDTO oAuth2RegisteredClientDTO = new OAuth2RegisteredClientDTO();
            BeanUtils.copyProperties(oAuth2RegisteredClientDO, oAuth2RegisteredClientDTO);
            return this.assembleOAuth2RegisteredClient(oAuth2RegisteredClientDTO);
        }
        return null;
    }

    @Override
    public OAuth2RegisteredClientDTO assembleOAuth2RegisteredClient(OAuth2RegisteredClientDTO oAuth2RegisteredClientDTO) {
        Assert.notNull(oAuth2RegisteredClientDTO, "客户端信息不能为空");
        Assert.hasText(oAuth2RegisteredClientDTO.getId(), "客户端id不能为空");
        Assert.hasText(oAuth2RegisteredClientDTO.getClientId(), "客户端id不能为空");

        OAuth2ClientSettingsDTO clientSettings =
                oauth2ClientSettingsService.getOAuth2ClientSettingsByRegisteredClientId(oAuth2RegisteredClientDTO.getId());
        OAuth2TokenSettingsDTO tokenSetting =
                oauth2TokenSettingsService.getOAuth2TokenSettingsByRegisteredClientId(oAuth2RegisteredClientDTO.getId());

        return oAuth2RegisteredClientDTO
                .setClientSettings(clientSettings)
                .setTokenSettings(tokenSetting);
    }

    @Override
    public void addOrUpdateOAuth2RegisteredClient(OAuth2RegisteredClientDTO oAuth2RegisteredClientDTO) {
        String clientId = oAuth2RegisteredClientDTO.getClientId();
        OAuth2RegisteredClientDO oAuth2RegisteredClient;
        if (StringUtils.hasText(clientId)) {
            oAuth2RegisteredClient = this.oauth2RegisteredClientMapper.getOAuth2RegisteredClientByClientId(clientId);
        } else {
            clientId = UUID.randomUUID().toString();
            oAuth2RegisteredClient = null;
        }

        if (oAuth2RegisteredClient == null) {
            oAuth2RegisteredClient = new OAuth2RegisteredClientDO();
            BeanUtils.copyProperties(oAuth2RegisteredClientDTO, oAuth2RegisteredClient);
            oAuth2RegisteredClient.setClientId(clientId);
            String encodePassword = passwordEncoder.encode(oAuth2RegisteredClientDTO.getClientSecret());
            oAuth2RegisteredClient.setClientSecret(encodePassword);
            this.oauth2RegisteredClientMapper.insert(oAuth2RegisteredClient);
        } else {
            if (StringUtils.hasText(oAuth2RegisteredClientDTO.getClientSecret())) {
                String encodePassword = passwordEncoder.encode(oAuth2RegisteredClientDTO.getClientSecret());
                CustomBeanUtils.copyPropertiesExcludeMetaAndNull(oAuth2RegisteredClientDTO, oAuth2RegisteredClient);
                oAuth2RegisteredClient.setClientSecret(encodePassword);
            } else {        // 为空不修改密码
                CustomBeanUtils.copyPropertiesExcludeMetaAndNull(oAuth2RegisteredClientDTO, oAuth2RegisteredClient);
            }
            this.oauth2RegisteredClientMapper.updateById(oAuth2RegisteredClient);
        }
        if (oAuth2RegisteredClientDTO.getClientSettings() != null) {
            oauth2ClientSettingsService.addOrUpdateOAuth2ClientSettings(oAuth2RegisteredClient.getId(), oAuth2RegisteredClientDTO.getClientSettings());
        }

        if (oAuth2RegisteredClientDTO.getTokenSettings() != null) {
            oauth2TokenSettingsService.addOrUpdateOAuth2TokenSettings(oAuth2RegisteredClient.getId(), oAuth2RegisteredClientDTO.getTokenSettings());
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
