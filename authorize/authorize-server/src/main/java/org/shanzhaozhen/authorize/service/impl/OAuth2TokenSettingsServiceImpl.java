package org.shanzhaozhen.authorize.service.impl;

import lombok.RequiredArgsConstructor;
import org.shanzhaozhen.authorize.converter.OAuth2TokenSettingsConverter;
import org.shanzhaozhen.authorize.mapper.OAuth2TokenSettingsMapper;
import org.shanzhaozhen.authorize.pojo.dto.OAuth2TokenSettingsDTO;
import org.shanzhaozhen.authorize.pojo.dto.OAuth2TokenSettingsDTO;
import org.shanzhaozhen.authorize.pojo.entity.OAuth2TokenSettingsDO;
import org.shanzhaozhen.authorize.service.OAuth2TokenSettingsService;
import org.shanzhaozhen.common.core.utils.CustomBeanUtils;
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
        OAuth2TokenSettingsDTO oAuth2TokenSettingsByClientIdInDB = this.getOAuth2TokenSettingsByRegisteredClientId(registeredClientId);
        if (oAuth2TokenSettingsByClientIdInDB == null) {
            OAuth2TokenSettingsDO oAuth2TokenSettingsDO = OAuth2TokenSettingsConverter.toDO(oAuth2TokenSettingsDTO);
            this.oAuth2TokenSettingsMapper.insert(oAuth2TokenSettingsDO);
        } else {
            OAuth2TokenSettingsDO oAuth2TokenSettingsDO = this.oAuth2TokenSettingsMapper.selectById(oAuth2TokenSettingsByClientIdInDB.getId());
            CustomBeanUtils.copyPropertiesExcludeMeta(oAuth2TokenSettingsDTO, oAuth2TokenSettingsDO);
            this.oAuth2TokenSettingsMapper.updateById(oAuth2TokenSettingsDO);
        }
    }

    @Override
    @Transactional
    public void deleteOAuth2TokenSettingsByRegisteredClientId(String registeredClientId) {
        this.oAuth2TokenSettingsMapper.deleteOAuth2TokenSettingsByRegisteredClientId(registeredClientId);
    }

}
