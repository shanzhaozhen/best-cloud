package org.shanzhaozhen.authorize.service;

import org.shanzhaozhen.authorize.pojo.dto.OAuth2TokenSettingsDTO;

/**
 * <p>
 * oauth2客户端的token配置项 服务类
 * </p>
 *
 * @author shanzhaozhen
 * @since 2022-06-17
 */
public interface OAuth2TokenSettingsService {

    OAuth2TokenSettingsDTO getOAuth2TokenSettingsByRegisteredClientId(String registeredClientId);

    void addOrUpdateOAuth2TokenSettings(String clientId, OAuth2TokenSettingsDTO oAuth2TokenSettingsDTO);

    void deleteOAuth2TokenSettingsByRegisteredClientId(String registeredClientId);

}
