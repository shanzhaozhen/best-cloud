package org.shanzhaozhen.uaa.service.impl;

import lombok.RequiredArgsConstructor;
import org.shanzhaozhen.uaa.mapper.OAuth2ClientSettingsMapper;
import org.shanzhaozhen.uaa.pojo.dto.OAuth2ClientSettingsDTO;
import org.shanzhaozhen.uaa.pojo.entity.OAuth2ClientSettingsDO;
import org.shanzhaozhen.common.core.utils.CustomBeanUtils;
import org.shanzhaozhen.uaa.service.OAuth2ClientSettingsService;
import org.springframework.beans.BeanUtils;
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
    public OAuth2ClientSettingsDTO getOAuth2ClientSettingsByRegisteredClientId(String registeredClientId) {
        OAuth2ClientSettingsDO oAuth2ClientSettingsDO = oAuth2ClientSettingsMapper.getOAuth2ClientSettingsByRegisteredClientId(registeredClientId);
        OAuth2ClientSettingsDTO oAuth2ClientSettingsDTO = new OAuth2ClientSettingsDTO();
        BeanUtils.copyProperties(oAuth2ClientSettingsDO, oAuth2ClientSettingsDTO);
        return oAuth2ClientSettingsDTO;
    }

    @Override
    @Transactional
    public void addOrUpdateOAuth2ClientSettings(String registeredClientId, OAuth2ClientSettingsDTO oAuth2ClientSettingsDTO) {
        OAuth2ClientSettingsDO oAuth2ClientSettings = oAuth2ClientSettingsMapper.getOAuth2ClientSettingsByRegisteredClientId(registeredClientId);
        if (oAuth2ClientSettings == null) {
            oAuth2ClientSettings = new OAuth2ClientSettingsDO();
            BeanUtils.copyProperties(oAuth2ClientSettingsDTO, oAuth2ClientSettings);
            oAuth2ClientSettings.setRegisteredClientId(registeredClientId);
            this.oAuth2ClientSettingsMapper.insert(oAuth2ClientSettings);
        } else {
            CustomBeanUtils.copyPropertiesExcludeMetaAndNull(oAuth2ClientSettingsDTO, oAuth2ClientSettings);
            this.oAuth2ClientSettingsMapper.updateById(oAuth2ClientSettings);
        }
    }

    @Override
    @Transactional
    public void deleteOAuth2ClientSettingsByRegisteredClientId(String registeredClientId) {
        this.oAuth2ClientSettingsMapper.deleteOAuth2ClientSettingsByRegisteredClientId(registeredClientId);
    }

}
