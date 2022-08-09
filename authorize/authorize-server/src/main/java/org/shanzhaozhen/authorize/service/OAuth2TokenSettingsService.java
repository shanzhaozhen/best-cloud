package org.shanzhaozhen.authorize.service;

import org.springframework.security.oauth2.server.authorization.config.TokenSettings;

/**
 * <p>
 * oauth2客户端的token配置项 服务类
 * </p>
 *
 * @author shanzhaozhen
 * @since 2022-06-17
 */
public interface OAuth2TokenSettingsService {

    TokenSettings getOAuth2TokenSettingsByRegisteredClientId(String registeredClientId);

    void addOrUpdateOAuth2TokenSettings(String clientId, TokenSettings tokenSettings);

    void deleteOAuth2TokenSettingsByRegisteredClientId(String registeredClientId);

}
