package org.shanzhaozhen.authorize.service.impl;

import lombok.RequiredArgsConstructor;
import org.shanzhaozhen.authorize.converter.OAuth2ClientSettingsConverter;
import org.shanzhaozhen.authorize.mapper.OAuth2ClientSettingsMapper;
import org.shanzhaozhen.authorize.pojo.entity.OAuth2ClientSettingsDO;
import org.shanzhaozhen.authorize.service.OAuth2ClientSettingsService;
import org.shanzhaozhen.common.core.utils.CustomBeanUtils;
import org.springframework.security.oauth2.server.authorization.config.ClientSettings;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Author: shanzhaozhen
 * @Date: 2022-06-17
 * @Description: oauth2客户端配置 服务实现类
 */
@Service
@RequiredArgsConstructor
public class OAuth2ClientSettingsServiceImpl implements OAuth2ClientSettingsService {

    private final OAuth2ClientSettingsMapper oAuth2ClientSettingsMapper;

    @Override
    public ClientSettings getOAuth2ClientSettingsByRegisteredClientId(String registeredClientId) {
        OAuth2ClientSettingsDO oAuth2ClientSettings = oAuth2ClientSettingsMapper.getOAuth2ClientSettingsByRegisteredClientId(registeredClientId);
        return OAuth2ClientSettingsConverter.toClientSettings(oAuth2ClientSettings);
    }

    @Override
    @Transactional
    public void addOrUpdateOAuth2ClientSettings(String registeredClientId, ClientSettings clientSettings) {
        OAuth2ClientSettingsDO oAuth2ClientSettings = oAuth2ClientSettingsMapper.getOAuth2ClientSettingsByRegisteredClientId(registeredClientId);
        OAuth2ClientSettingsDO oAuth2ClientSettingsDO = OAuth2ClientSettingsConverter.toDO(clientSettings);
        if (oAuth2ClientSettings == null) {
            oAuth2ClientSettingsDO.setRegisteredClientId(registeredClientId);
            this.oAuth2ClientSettingsMapper.insert(oAuth2ClientSettingsDO);
        } else {
            CustomBeanUtils.copyPropertiesExcludeMetaAndNull(oAuth2ClientSettingsDO, oAuth2ClientSettings);
            this.oAuth2ClientSettingsMapper.updateById(oAuth2ClientSettings);
        }
    }

    @Override
    @Transactional
    public void deleteOAuth2ClientSettingsByRegisteredClientId(String registeredClientId) {
        this.oAuth2ClientSettingsMapper.deleteOAuth2ClientSettingsByRegisteredClientId(registeredClientId);
    }

}
