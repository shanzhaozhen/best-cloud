package org.shanzhaozhen.authorize.service.impl;

import lombok.RequiredArgsConstructor;
import org.shanzhaozhen.authorize.converter.OAuth2TokenSettingsConverter;
import org.shanzhaozhen.authorize.mapper.OAuth2TokenSettingsMapper;
import org.shanzhaozhen.authorize.pojo.entity.OAuth2TokenSettingsDO;
import org.shanzhaozhen.authorize.service.OAuth2TokenSettingsService;
import org.shanzhaozhen.common.core.utils.CustomBeanUtils;
import org.springframework.security.oauth2.server.authorization.config.TokenSettings;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Author: shanzhaozhen
 * @Date: 2022-06-17
 * @Description: oauth2客户端的token配置项 服务实现类
 */
@Service
@RequiredArgsConstructor
public class OAuth2TokenSettingsServiceImpl implements OAuth2TokenSettingsService {

    private final OAuth2TokenSettingsMapper oAuth2TokenSettingsMapper;
    @Override
    public TokenSettings getOAuth2TokenSettingsByRegisteredClientId(String registeredClientId) {
        OAuth2TokenSettingsDO oAuth2TokenSettings = oAuth2TokenSettingsMapper.getOAuth2TokenSettingsByRegisteredClientId(registeredClientId);
        return OAuth2TokenSettingsConverter.toTokenSettings(oAuth2TokenSettings);
    }

    @Override
    @Transactional
    public void addOrUpdateOAuth2TokenSettings(String registeredClientId, TokenSettings tokenSettings) {
        OAuth2TokenSettingsDO oAuth2TokenSettings = this.oAuth2TokenSettingsMapper.getOAuth2TokenSettingsByRegisteredClientId(registeredClientId);
        OAuth2TokenSettingsDO oAuth2TokenSettingsDO = OAuth2TokenSettingsConverter.toDO(tokenSettings);
        if (oAuth2TokenSettings == null) {
            oAuth2TokenSettingsDO.setRegisteredClientId(registeredClientId);
            this.oAuth2TokenSettingsMapper.insert(oAuth2TokenSettingsDO);
        } else {
            CustomBeanUtils.copyPropertiesExcludeMetaAndNull(oAuth2TokenSettingsDO, oAuth2TokenSettings);
            this.oAuth2TokenSettingsMapper.updateById(oAuth2TokenSettings);
        }
    }

    @Override
    @Transactional
    public void deleteOAuth2TokenSettingsByRegisteredClientId(String registeredClientId) {
        this.oAuth2TokenSettingsMapper.deleteOAuth2TokenSettingsByRegisteredClientId(registeredClientId);
    }

}
