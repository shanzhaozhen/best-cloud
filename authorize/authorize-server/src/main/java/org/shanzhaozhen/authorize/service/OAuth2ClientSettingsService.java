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

    /**
     * 通过客户端信息 id 获取 oauth2 客户端配置
     * @param registeredClientId
     * @return
     */
    ClientSettings getOAuth2ClientSettingsByRegisteredClientId(String registeredClientId);

    /**
     * 添加或更新客户端信息 oauth2 客户端配置
     * @param clientId
     * @param clientSettings
     */
    void addOrUpdateOAuth2ClientSettings(String clientId, ClientSettings clientSettings);

    /**
     * 通过客户端 id 删除 oauth2 客户端配置
     * @param registeredClientId
     */
    void deleteOAuth2ClientSettingsByRegisteredClientId(String registeredClientId);

}
