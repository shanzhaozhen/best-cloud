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

    /**
     * 通过客户端 id 获得 oauth2 客户端配置
     * @param registeredClientId
     * @return
     */
    TokenSettings getOAuth2TokenSettingsByRegisteredClientId(String registeredClientId);

    /**
     * 添加或更新客户端信息 oauth2 客户端配置
     * @param clientId
     * @param tokenSettings
     */
    void addOrUpdateOAuth2TokenSettings(String clientId, TokenSettings tokenSettings);

    /**
     * 通过客户端 id 删除客户端信息 oauth2 客户端配置
     * @param registeredClientId
     */
    void deleteOAuth2TokenSettingsByRegisteredClientId(String registeredClientId);

}
