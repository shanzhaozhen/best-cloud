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
        return oAuth2ClientSettingsMapper.getOAuth2ClientSettingsByRegisteredClientId(registeredClientId);
    }

    @Override
    @Transactional
    public void addOrUpdateOAuth2ClientSettings(String registeredClientId, OAuth2ClientSettingsDTO oAuth2ClientSettingsDTO) {
        OAuth2ClientSettingsDTO oAuth2ClientSettingsInDB = oAuth2ClientSettingsMapper.getOAuth2ClientSettingsByRegisteredClientId(registeredClientId);
        if (oAuth2ClientSettingsInDB == null) {
            OAuth2ClientSettingsDO oAuth2ClientSettingsDO = new OAuth2ClientSettingsDO();
            BeanUtils.copyProperties(oAuth2ClientSettingsDTO, oAuth2ClientSettingsDO);
            oAuth2ClientSettingsDO.setRegisteredClientId(registeredClientId);
            this.oAuth2ClientSettingsMapper.insert(oAuth2ClientSettingsDO);
        } else {
            OAuth2ClientSettingsDO oAuth2ClientSettingsDO = oAuth2ClientSettingsMapper.selectById(oAuth2ClientSettingsInDB.getId());
            CustomBeanUtils.copyPropertiesExcludeMetaAndNull(oAuth2ClientSettingsDTO, oAuth2ClientSettingsDO);
            this.oAuth2ClientSettingsMapper.updateById(oAuth2ClientSettingsDO);
        }
    }

    @Override
    @Transactional
    public void deleteOAuth2ClientSettingsByRegisteredClientId(String registeredClientId) {
        this.oAuth2ClientSettingsMapper.deleteOAuth2ClientSettingsByRegisteredClientId(registeredClientId);
    }

}
