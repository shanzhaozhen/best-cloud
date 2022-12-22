package org.shanzhaozhen.authorize.service.impl;

import lombok.RequiredArgsConstructor;
import org.shanzhaozhen.authorize.converter.OAuth2AuthorizationConsentConverter;
import org.shanzhaozhen.authorize.mapper.OAuth2AuthorizationConsentMapper;
import org.shanzhaozhen.authorize.pojo.dto.OAuth2AuthorizationConsentDTO;
import org.shanzhaozhen.authorize.pojo.entity.OAuth2AuthorizationConsentDO;
import org.shanzhaozhen.common.core.utils.CustomBeanUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationConsent;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationConsentService;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;


/**
 * @Author: shanzhaozhen
 * @Date: 2022-06-17
 * @Description: 如果是授权码的流程，可能客户端申请了多个权限，比如：获取用户信息、修改用户信息。
 *               此Service处理的是用户给这个客户端哪些权限，比如只给获取用户信息的权限
 */
@Service
@RequiredArgsConstructor
public class OAuth2AuthorizationConsentServiceImpl implements OAuth2AuthorizationConsentService {

    private final OAuth2AuthorizationConsentMapper oauth2AuthorizationConsentMapper;

    @Override
    public void save(OAuth2AuthorizationConsent authorizationConsent) {
        OAuth2AuthorizationConsentDTO oauth2AuthorizationConsentDTO = OAuth2AuthorizationConsentConverter.toDTO(authorizationConsent);

        Assert.hasText(oauth2AuthorizationConsentDTO.getRegisteredClientId(), "客户端ID不能为空");
        Assert.hasText(oauth2AuthorizationConsentDTO.getPrincipalName(), "授权的用户名不能为空");
        OAuth2AuthorizationConsentDO oauth2AuthorizationConsentDO = this.oauth2AuthorizationConsentMapper.findOAuth2AuthorizationConsent(oauth2AuthorizationConsentDTO.getRegisteredClientId(), oauth2AuthorizationConsentDTO.getPrincipalName());
        if (oauth2AuthorizationConsentDO == null) {
            oauth2AuthorizationConsentDO = new OAuth2AuthorizationConsentDO();
            BeanUtils.copyProperties(oauth2AuthorizationConsentDTO, oauth2AuthorizationConsentDO);
            oauth2AuthorizationConsentMapper.insert(oauth2AuthorizationConsentDO);
        } else {
            CustomBeanUtils.copyPropertiesExcludeMetaAndNull(oauth2AuthorizationConsentDTO, oauth2AuthorizationConsentDO);
            oauth2AuthorizationConsentMapper.updateById(oauth2AuthorizationConsentDO);
        }
    }

    @Override
    public void remove(OAuth2AuthorizationConsent authorizationConsent) {
        oauth2AuthorizationConsentMapper.deleteOAuth2AuthorizationConsent(authorizationConsent.getRegisteredClientId(), authorizationConsent.getPrincipalName());
    }

    @Override
    public OAuth2AuthorizationConsent findById(String registeredClientId, String principalName) {
        OAuth2AuthorizationConsentDO oauth2AuthorizationConsentDO = this.oauth2AuthorizationConsentMapper.findOAuth2AuthorizationConsent(registeredClientId, principalName);
        if (oauth2AuthorizationConsentDO != null) {
            OAuth2AuthorizationConsentDTO oauth2AuthorizationConsentDTO = new OAuth2AuthorizationConsentDTO();
            BeanUtils.copyProperties(oauth2AuthorizationConsentDO, oauth2AuthorizationConsentDTO);
            return OAuth2AuthorizationConsentConverter.toOAuth2AuthorizationConsent(oauth2AuthorizationConsentDTO);
        }
        return null;
    }
}
