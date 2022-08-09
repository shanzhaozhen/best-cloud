package org.shanzhaozhen.authorize.service;

import org.springframework.security.oauth2.server.authorization.config.ClientSettings;

/**
 * <p>
 * oauth2客户端配置 服务类
 * </p>
 *
 * @author shanzhaozhen
 * @since 2022-06-17
 */
public interface OAuth2ClientSettingsService {

    ClientSettings getOAuth2ClientSettingsByRegisteredClientId(String registeredClientId);

    void addOrUpdateOAuth2ClientSettings(String clientId, ClientSettings clientSettings);

    void deleteOAuth2ClientSettingsByRegisteredClientId(String registeredClientId);

}
