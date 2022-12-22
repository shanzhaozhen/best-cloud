package org.shanzhaozhen.uaa.service;

import org.shanzhaozhen.uaa.pojo.dto.OAuth2ClientSettingsDTO;

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
    OAuth2ClientSettingsDTO getOAuth2ClientSettingsByRegisteredClientId(String registeredClientId);

    /**
     * 添加或更新客户端信息 oauth2 客户端配置
     * @param clientId
     * @param oauth2ClientSettingsDTO
     */
    void addOrUpdateOAuth2ClientSettings(String clientId, OAuth2ClientSettingsDTO oauth2ClientSettingsDTO);

    /**
     * 通过客户端 id 删除 oauth2 客户端配置
     * @param registeredClientId
     */
    void deleteOAuth2ClientSettingsByRegisteredClientId(String registeredClientId);

}
