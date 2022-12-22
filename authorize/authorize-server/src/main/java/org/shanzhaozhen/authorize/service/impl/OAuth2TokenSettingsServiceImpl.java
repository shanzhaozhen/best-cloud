package org.shanzhaozhen.authorize.service.impl;

import lombok.RequiredArgsConstructor;
import org.shanzhaozhen.authorize.mapper.OAuth2TokenSettingsMapper;
import org.shanzhaozhen.authorize.service.OAuth2TokenSettingsService;
import org.shanzhaozhen.common.core.utils.CustomBeanUtils;
import org.shanzhaozhen.authorize.pojo.dto.OAuth2TokenSettingsDTO;
import org.shanzhaozhen.authorize.pojo.entity.OAuth2TokenSettingsDO;
import org.springframework.beans.BeanUtils;
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

    private final OAuth2TokenSettingsMapper oauth2TokenSettingsMapper;
    @Override
    public OAuth2TokenSettingsDTO getOAuth2TokenSettingsByRegisteredClientId(String registeredClientId) {
        return oauth2TokenSettingsMapper.getOAuth2TokenSettingsByRegisteredClientId(registeredClientId);
    }

    @Override
    @Transactional
    public void addOrUpdateOAuth2TokenSettings(String registeredClientId, OAuth2TokenSettingsDTO oauth2TokenSettingsDTO) {
        OAuth2TokenSettingsDTO oauth2TokenSettingsInDB = this.oauth2TokenSettingsMapper.getOAuth2TokenSettingsByRegisteredClientId(registeredClientId);
        if (oauth2TokenSettingsInDB == null) {
            OAuth2TokenSettingsDO oauth2TokenSettingsDO = new OAuth2TokenSettingsDO();
            BeanUtils.copyProperties(oauth2TokenSettingsDTO, oauth2TokenSettingsDO);
            oauth2TokenSettingsDO.setRegisteredClientId(registeredClientId);
            this.oauth2TokenSettingsMapper.insert(oauth2TokenSettingsDO);
        } else {
            OAuth2TokenSettingsDO oauth2TokenSettingsDO = oauth2TokenSettingsMapper.selectById(oauth2TokenSettingsInDB.getId());
            CustomBeanUtils.copyPropertiesExcludeMetaAndNull(oauth2TokenSettingsDTO, oauth2TokenSettingsDO);
            this.oauth2TokenSettingsMapper.updateById(oauth2TokenSettingsDO);
        }
    }

    @Override
    @Transactional
    public void deleteOAuth2TokenSettingsByRegisteredClientId(String registeredClientId) {
        this.oauth2TokenSettingsMapper.deleteOAuth2TokenSettingsByRegisteredClientId(registeredClientId);
    }

}
