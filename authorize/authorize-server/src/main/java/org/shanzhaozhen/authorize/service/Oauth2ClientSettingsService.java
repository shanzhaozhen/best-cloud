package org.shanzhaozhen.authorize.service;

import org.shanzhaozhen.authorize.pojo.dto.OAuth2ClientSettingsDTO;

/**
 * <p>
 * oauth2客户端配置 服务类
 * </p>
 *
 * @author shanzhaozhen
 * @since 2022-06-17
 */
public interface OAuth2ClientSettingsService {

    OAuth2ClientSettingsDTO getOAuth2ClientSettingsByClientId(String clientId);

    void addOrUpdateOAuth2ClientSettings(String clientId, OAuth2ClientSettingsDTO oAuth2ClientSettingsDTO);

}
