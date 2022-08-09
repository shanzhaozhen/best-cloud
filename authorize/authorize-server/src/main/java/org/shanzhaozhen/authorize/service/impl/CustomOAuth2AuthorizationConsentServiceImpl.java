package org.shanzhaozhen.authorize.service.impl;

import lombok.RequiredArgsConstructor;
import org.shanzhaozhen.authorize.converter.OAuth2AuthorizationConsentConverter;
import org.shanzhaozhen.authorize.mapper.OAuth2AuthorizationConsentMapper;
import org.shanzhaozhen.authorize.pojo.entity.OAuth2AuthorizationConsentDO;
import org.shanzhaozhen.authorize.service.CustomOAuth2AuthorizationConsentService;
import org.shanzhaozhen.common.core.utils.CustomBeanUtils;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationConsent;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;


/**
 * @Author: shanzhaozhen
 * @Date: 2022-06-17
 * @Description: 如果是授权码的流程，可能客户端申请了多个权限，比如：获取用户信息、修改用户信息。
 *               此Service处理的是用户给这个客户端哪些权限，比如只给获取用户信息的权限
 */
@Service
@RequiredArgsConstructor
public class CustomOAuth2AuthorizationConsentServiceImpl implements CustomOAuth2AuthorizationConsentService {

    private final OAuth2AuthorizationConsentMapper oAuth2AuthorizationConsentMapper;

    @Override
    @Transactional
    public void addOrUpdateOAuth2AuthorizationConsent(OAuth2AuthorizationConsent oAuth2AuthorizationConsent) {
        Assert.hasText(oAuth2AuthorizationConsent.getRegisteredClientId(), "客户端ID不能为空");
        Assert.hasText(oAuth2AuthorizationConsent.getPrincipalName(), "授权的用户名不能为空");
        OAuth2AuthorizationConsentDO oAuth2AuthorizationConsentDO =
                this.oAuth2AuthorizationConsentMapper.findOAuth2AuthorizationConsent(oAuth2AuthorizationConsent.getRegisteredClientId(), oAuth2AuthorizationConsent.getPrincipalName());
        if (oAuth2AuthorizationConsentDO == null) {
            oAuth2AuthorizationConsentDO = OAuth2AuthorizationConsentConverter.toDO(oAuth2AuthorizationConsent);
            oAuth2AuthorizationConsentMapper.insert(oAuth2AuthorizationConsentDO);
        } else {
            OAuth2AuthorizationConsentDO newOAuth2AuthorizationConsentDO = OAuth2AuthorizationConsentConverter.toDO(oAuth2AuthorizationConsent);
            CustomBeanUtils.copyPropertiesExcludeMetaAndNull(newOAuth2AuthorizationConsentDO, oAuth2AuthorizationConsentDO);
            oAuth2AuthorizationConsentMapper.updateById(oAuth2AuthorizationConsentDO);
        }
    }

    @Override
    @Transactional
    public void deleteOAuth2AuthorizationConsent(String registeredClientId, String principalName) {
        oAuth2AuthorizationConsentMapper.deleteOAuth2AuthorizationConsent(registeredClientId, principalName);
    }

    @Override
    @Transactional
    public void save(OAuth2AuthorizationConsent authorizationConsent) {
        this.addOrUpdateOAuth2AuthorizationConsent(authorizationConsent);
    }

    @Override
    @Transactional
    public void remove(OAuth2AuthorizationConsent authorizationConsent) {
        this.deleteOAuth2AuthorizationConsent(authorizationConsent.getRegisteredClientId(), authorizationConsent.getPrincipalName());
    }

    @Override
    public OAuth2AuthorizationConsent findById(String registeredClientId, String principalName) {
        OAuth2AuthorizationConsentDO oAuth2AuthorizationConsent = this.oAuth2AuthorizationConsentMapper.findOAuth2AuthorizationConsent(registeredClientId, principalName);
        if (oAuth2AuthorizationConsent != null) {
            return OAuth2AuthorizationConsentConverter.toOAuth2AuthorizationConsent(oAuth2AuthorizationConsent);
        }
        return null;
    }

}
