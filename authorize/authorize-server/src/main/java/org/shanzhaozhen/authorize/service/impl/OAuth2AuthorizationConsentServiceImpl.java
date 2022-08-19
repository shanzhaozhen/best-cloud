package org.shanzhaozhen.authorize.service.impl;

import lombok.RequiredArgsConstructor;
import org.shanzhaozhen.authorize.converter.OAuth2AuthorizationConsentConverter;
import org.shanzhaozhen.uaa.feign.OAuth2AuthorizationConsentClient;
import org.shanzhaozhen.uaa.pojo.dto.OAuth2AuthorizationConsentDTO;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationConsent;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationConsentService;
import org.springframework.stereotype.Service;


/**
 * @Author: shanzhaozhen
 * @Date: 2022-06-17
 * @Description: 如果是授权码的流程，可能客户端申请了多个权限，比如：获取用户信息、修改用户信息。
 *               此Service处理的是用户给这个客户端哪些权限，比如只给获取用户信息的权限
 */
@Service
@RequiredArgsConstructor
public class OAuth2AuthorizationConsentServiceImpl implements OAuth2AuthorizationConsentService {

    private final OAuth2AuthorizationConsentClient oAuth2AuthorizationConsentClient;


    @Override
    public void save(OAuth2AuthorizationConsent authorizationConsent) {
        oAuth2AuthorizationConsentClient.saveOAuth2AuthorizationConsent(OAuth2AuthorizationConsentConverter.toDTO(authorizationConsent));
    }

    @Override
    public void remove(OAuth2AuthorizationConsent authorizationConsent) {
        oAuth2AuthorizationConsentClient.deleteOAuth2AuthorizationConsent(authorizationConsent.getRegisteredClientId(), authorizationConsent.getPrincipalName());
    }

    @Override
    public OAuth2AuthorizationConsent findById(String registeredClientId, String principalName) {
        OAuth2AuthorizationConsentDTO oAuth2AuthorizationConsentDTO = oAuth2AuthorizationConsentClient.getOAuth2AuthorizationConsent(registeredClientId, principalName);

        if (oAuth2AuthorizationConsentDTO != null) {
            return OAuth2AuthorizationConsentConverter.toOAuth2AuthorizationConsent(oAuth2AuthorizationConsentDTO);
        }
        return null;
    }
}
