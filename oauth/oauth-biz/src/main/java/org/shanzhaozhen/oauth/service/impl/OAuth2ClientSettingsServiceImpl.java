package org.shanzhaozhen.oauth.service.impl;

import lombok.RequiredArgsConstructor;
import org.shanzhaozhen.common.core.utils.CustomBeanUtils;
import org.shanzhaozhen.oauth.mapper.OAuth2ClientSettingsMapper;
import org.shanzhaozhen.oauth.pojo.dto.OAuth2ClientSettingsDTO;
import org.shanzhaozhen.oauth.pojo.entity.OAuth2ClientSettingsDO;
import org.shanzhaozhen.oauth.service.OAuth2ClientSettingsService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Author: shanzhaozhen
 * @Date: 2023-01-16
 * @Description: oauth2客户端配置 服务实现类
 */
@Service
@RequiredArgsConstructor
public class OAuth2ClientSettingsServiceImpl implements OAuth2ClientSettingsService {

    private final OAuth2ClientSettingsMapper oauth2ClientSettingsMapper;

    @Override
    public OAuth2ClientSettingsDTO getOAuth2ClientSettingsByRegisteredClientId(String registeredClientId) {
        return oauth2ClientSettingsMapper.getOAuth2ClientSettingsByRegisteredClientId(registeredClientId);
    }

    @Override
    @Transactional
    public void addOrUpdateOAuth2ClientSettings(String registeredClientId, OAuth2ClientSettingsDTO oauth2ClientSettingsDTO) {
        OAuth2ClientSettingsDTO oauth2ClientSettingsInDB = oauth2ClientSettingsMapper.getOAuth2ClientSettingsByRegisteredClientId(registeredClientId);
        if (oauth2ClientSettingsInDB == null) {
            OAuth2ClientSettingsDO oauth2ClientSettingsDO = new OAuth2ClientSettingsDO();
            BeanUtils.copyProperties(oauth2ClientSettingsDTO, oauth2ClientSettingsDO);
            oauth2ClientSettingsDO.setRegisteredClientId(registeredClientId);
            this.oauth2ClientSettingsMapper.insert(oauth2ClientSettingsDO);
        } else {
            OAuth2ClientSettingsDO oauth2ClientSettingsDO = oauth2ClientSettingsMapper.selectById(oauth2ClientSettingsInDB.getId());
            CustomBeanUtils.copyPropertiesExcludeMetaAndNull(oauth2ClientSettingsDTO, oauth2ClientSettingsDO);
            this.oauth2ClientSettingsMapper.updateById(oauth2ClientSettingsDO);
        }
    }

    @Override
    @Transactional
    public void deleteOAuth2ClientSettingsByRegisteredClientId(String registeredClientId) {
        this.oauth2ClientSettingsMapper.deleteOAuth2ClientSettingsByRegisteredClientId(registeredClientId);
    }

}
