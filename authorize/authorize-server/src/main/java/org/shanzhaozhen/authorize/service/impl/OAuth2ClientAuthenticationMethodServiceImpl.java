package org.shanzhaozhen.authorize.service.impl;

import lombok.RequiredArgsConstructor;
import org.shanzhaozhen.authorize.converter.OAuth2ClientAuthenticationMethodConverter;
import org.shanzhaozhen.authorize.mapper.OAuth2ClientAuthenticationMethodMapper;
import org.shanzhaozhen.authorize.pojo.dto.OAuth2ClientAuthenticationMethodDTO;
import org.shanzhaozhen.authorize.pojo.entity.OAuth2ClientAuthenticationMethodDO;
import org.shanzhaozhen.authorize.service.OAuth2ClientAuthenticationMethodService;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Set;

/**
 * @Author: shanzhaozhen
 * @Date: 2022-06-17
 * @Description: oauth2客户端认证方式表 服务实现类
 */
@Service
@RequiredArgsConstructor
public class OAuth2ClientAuthenticationMethodServiceImpl implements OAuth2ClientAuthenticationMethodService {

    private final OAuth2ClientAuthenticationMethodMapper oAuth2ClientAuthenticationMethodMapper;

    @Override
    public List<OAuth2ClientAuthenticationMethodDTO> getOAuth2ClientAuthenticationMethodsByRegisteredClientId(String registeredClientId) {
        return oAuth2ClientAuthenticationMethodMapper.getOAuth2ClientAuthenticationMethodByRegisteredClientId(registeredClientId);
    }

    @Override
    public void addOAuth2ClientAuthenticationMethods(String clientId, Set<OAuth2ClientAuthenticationMethodDTO> clientAuthenticationMethods) {
        if (CollectionUtils.isEmpty(clientAuthenticationMethods)) return;

        for (OAuth2ClientAuthenticationMethodDTO clientAuthenticationMethod : clientAuthenticationMethods) {
            OAuth2ClientAuthenticationMethodDO oAuth2ClientAuthenticationMethodDO = OAuth2ClientAuthenticationMethodConverter.toDO(clientAuthenticationMethod);
            this.oAuth2ClientAuthenticationMethodMapper.insert(oAuth2ClientAuthenticationMethodDO);
        }
    }

    @Override
    public void updateOAuth2ClientAuthenticationMethods(String registeredClientId, Set<OAuth2ClientAuthenticationMethodDTO> clientAuthenticationMethods) {
        this.deleteOAuth2ClientAuthenticationMethodsByRegisteredClientId(registeredClientId);
        this.addOAuth2ClientAuthenticationMethods(registeredClientId, clientAuthenticationMethods);
    }

    @Override
    public void deleteOAuth2ClientAuthenticationMethodsByRegisteredClientId(String registeredClientId) {
        oAuth2ClientAuthenticationMethodMapper.deleteOAuth2ClientAuthenticationMethodsByRegisteredClientId(registeredClientId);
    }

}
