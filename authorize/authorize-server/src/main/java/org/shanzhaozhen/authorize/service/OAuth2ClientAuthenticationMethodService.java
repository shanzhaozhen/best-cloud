package org.shanzhaozhen.authorize.service;

import org.shanzhaozhen.authorize.pojo.dto.OAuth2ClientAuthenticationMethodDTO;

import java.util.List;
import java.util.Set;

/**
 * <p>
 * oauth2客户端认证方式表 服务类
 * </p>
 *
 * @author shanzhaozhen
 * @since 2022-06-17
 */
public interface OAuth2ClientAuthenticationMethodService {

    List<OAuth2ClientAuthenticationMethodDTO> getOAuth2ClientAuthenticationMethodsByRegisteredClientId(String registeredClientId);

    void addOAuth2ClientAuthenticationMethods(String clientId, Set<OAuth2ClientAuthenticationMethodDTO> clientAuthenticationMethods);

    void updateOAuth2ClientAuthenticationMethods(String clientId, Set<OAuth2ClientAuthenticationMethodDTO> clientAuthenticationMethods);

    void deleteOAuth2ClientAuthenticationMethodsByRegisteredClientId(String registeredClientId);

}
