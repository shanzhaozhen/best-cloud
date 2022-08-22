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
        return oAuth2TokenSettingsMapper.getOAuth2TokenSettingsByRegisteredClientId(registeredClientId);
    }

    @Override
    @Transactional
    public void addOrUpdateOAuth2TokenSettings(String registeredClientId, OAuth2TokenSettingsDTO oAuth2TokenSettingsDTO) {
        OAuth2TokenSettingsDTO oAuth2TokenSettingsInDB = this.oAuth2TokenSettingsMapper.getOAuth2TokenSettingsByRegisteredClientId(registeredClientId);
        if (oAuth2TokenSettingsInDB == null) {
            OAuth2TokenSettingsDO oAuth2TokenSettingsDO = new OAuth2TokenSettingsDO();
            BeanUtils.copyProperties(oAuth2TokenSettingsDTO, oAuth2TokenSettingsDO);
            oAuth2TokenSettingsDO.setRegisteredClientId(registeredClientId);
            this.oAuth2TokenSettingsMapper.insert(oAuth2TokenSettingsDO);
        } else {
            OAuth2TokenSettingsDO oAuth2TokenSettingsDO = oAuth2TokenSettingsMapper.selectById(oAuth2TokenSettingsInDB.getId());
            CustomBeanUtils.copyPropertiesExcludeMetaAndNull(oAuth2TokenSettingsDTO, oAuth2TokenSettingsDO);
            this.oAuth2TokenSettingsMapper.updateById(oAuth2TokenSettingsDO);
        }
    }

    @Override
    @Transactional
    public void deleteOAuth2TokenSettingsByRegisteredClientId(String registeredClientId) {
        this.oAuth2TokenSettingsMapper.deleteOAuth2TokenSettingsByRegisteredClientId(registeredClientId);
    }

}
