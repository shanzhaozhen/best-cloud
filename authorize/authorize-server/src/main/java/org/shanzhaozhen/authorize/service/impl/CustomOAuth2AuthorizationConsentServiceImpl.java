package org.shanzhaozhen.authorize.service.impl;

import lombok.RequiredArgsConstructor;
import org.shanzhaozhen.authorize.converter.OAuth2AuthorizationConsentConverter;
import org.shanzhaozhen.authorize.mapper.OAuth2AuthorizationConsentMapper;
import org.shanzhaozhen.authorize.pojo.dto.OAuth2AuthorizationConsentDTO;
import org.shanzhaozhen.authorize.pojo.entity.OAuth2AuthorizationConsentDO;
import org.shanzhaozhen.authorize.service.CustomOAuth2AuthorizationConsentService;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationConsent;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationConsentService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Author: shanzhaozhen
 * @Date: 2022-06-17
 * @Description:
 */
//@Service
@RequiredArgsConstructor
public class CustomOAuth2AuthorizationConsentServiceImpl implements CustomOAuth2AuthorizationConsentService {

    private final OAuth2AuthorizationConsentMapper oAuth2AuthorizationConsentMapper;

    @Override
    @Transactional
    public void addOAuth2AuthorizationConsent(OAuth2AuthorizationConsentDTO oAuth2AuthorizationConsentDTO) {
        // TODO: 2022/8/7 检查 registeredClientId、principalName 重复
        OAuth2AuthorizationConsentDO oAuth2AuthorizationConsentDO = OAuth2AuthorizationConsentConverter.toDO(oAuth2AuthorizationConsentDTO);
        oAuth2AuthorizationConsentMapper.insert(oAuth2AuthorizationConsentDO);
    }

    @Override
    @Transactional
    public void deleteOAuth2AuthorizationConsent(String registeredClientId, String principalName) {
        oAuth2AuthorizationConsentMapper.deleteOAuth2AuthorizationConsent(registeredClientId, principalName);
    }

    @Override
    public OAuth2AuthorizationConsentDTO findOAuth2AuthorizationConsent(String registeredClientId, String principalName) {
        OAuth2AuthorizationConsentDO oAuth2AuthorizationConsentDO = oAuth2AuthorizationConsentMapper.findOAuth2AuthorizationConsent(registeredClientId, principalName);
        return OAuth2AuthorizationConsentConverter.toDTO(oAuth2AuthorizationConsentDO);
    }


    @Override
    @Transactional
    public void save(OAuth2AuthorizationConsent authorizationConsent) {
        this.addOAuth2AuthorizationConsent(OAuth2AuthorizationConsentConverter.toDTO(authorizationConsent));
    }

    @Override
    @Transactional
    public void remove(OAuth2AuthorizationConsent authorizationConsent) {
        this.deleteOAuth2AuthorizationConsent(authorizationConsent.getRegisteredClientId(), authorizationConsent.getPrincipalName());
    }

    @Override
    public OAuth2AuthorizationConsent findById(String registeredClientId, String principalName) {
        OAuth2AuthorizationConsentDTO oAuth2AuthorizationConsent = this.findOAuth2AuthorizationConsent(registeredClientId, principalName);
        return OAuth2AuthorizationConsentConverter.toOAuth2AuthorizationConsent(oAuth2AuthorizationConsent);
    }

}
