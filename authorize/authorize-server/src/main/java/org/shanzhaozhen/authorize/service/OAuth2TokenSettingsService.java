package org.shanzhaozhen.authorize.service;

import org.shanzhaozhen.oauth.pojo.dto.OAuth2TokenSettingsDTO;

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
    OAuth2TokenSettingsDTO getOAuth2TokenSettingsByRegisteredClientId(String registeredClientId);

    /**
     * 添加或更新客户端信息 oauth2 客户端配置
     * @param clientId
     * @param oauth2TokenSettingsDTO
     */
    void addOrUpdateOAuth2TokenSettings(String clientId, OAuth2TokenSettingsDTO oauth2TokenSettingsDTO);

    /**
     * 通过客户端 id 删除客户端信息 oauth2 客户端配置
     * @param registeredClientId
     */
    void deleteOAuth2TokenSettingsByRegisteredClientId(String registeredClientId);

}
