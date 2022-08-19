package org.shanzhaozhen.uaa.service.impl;

import lombok.RequiredArgsConstructor;
import org.shanzhaozhen.uaa.mapper.OAuth2TokenSettingsMapper;
import org.shanzhaozhen.uaa.pojo.dto.OAuth2TokenSettingsDTO;
import org.shanzhaozhen.uaa.pojo.entity.OAuth2TokenSettingsDO;
import org.shanzhaozhen.common.core.utils.CustomBeanUtils;
import org.shanzhaozhen.uaa.service.OAuth2TokenSettingsService;
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

    private final OAuth2TokenSettingsMapper oAuth2TokenSettingsMapper;
    @Override
    public OAuth2TokenSettingsDTO getOAuth2TokenSettingsByRegisteredClientId(String registeredClientId) {
        OAuth2TokenSettingsDO oAuth2TokenSettings = oAuth2TokenSettingsMapper.getOAuth2TokenSettingsByRegisteredClientId(registeredClientId);
        OAuth2TokenSettingsDTO oAuth2TokenSettingsDTO = new OAuth2TokenSettingsDTO();
        BeanUtils.copyProperties(oAuth2TokenSettings, oAuth2TokenSettingsDTO);
        return oAuth2TokenSettingsDTO;
    }

    @Override
    @Transactional
    public void addOrUpdateOAuth2TokenSettings(String registeredClientId, OAuth2TokenSettingsDTO oAuth2TokenSettingsDTO) {
        OAuth2TokenSettingsDO oAuth2TokenSettings = this.oAuth2TokenSettingsMapper.getOAuth2TokenSettingsByRegisteredClientId(registeredClientId);
        if (oAuth2TokenSettings == null) {
            oAuth2TokenSettings = new OAuth2TokenSettingsDO();
            BeanUtils.copyProperties(oAuth2TokenSettingsDTO, oAuth2TokenSettings);
            oAuth2TokenSettings.setRegisteredClientId(registeredClientId);
            this.oAuth2TokenSettingsMapper.insert(oAuth2TokenSettings);
        } else {
            CustomBeanUtils.copyPropertiesExcludeMetaAndNull(oAuth2TokenSettingsDTO, oAuth2TokenSettings);
            this.oAuth2TokenSettingsMapper.updateById(oAuth2TokenSettings);
        }
    }

    @Override
    @Transactional
    public void deleteOAuth2TokenSettingsByRegisteredClientId(String registeredClientId) {
        this.oAuth2TokenSettingsMapper.deleteOAuth2TokenSettingsByRegisteredClientId(registeredClientId);
    }

}
