package org.shanzhaozhen.authorize.service.impl;


import lombok.RequiredArgsConstructor;
import org.shanzhaozhen.authorize.converter.OAuth2ScopeConverter;
import org.shanzhaozhen.authorize.mapper.OAuth2ScopeMapper;
import org.shanzhaozhen.authorize.pojo.dto.OAuth2ScopeDTO;
import org.shanzhaozhen.authorize.pojo.dto.OAuth2ScopeDTO;
import org.shanzhaozhen.authorize.pojo.entity.OAuth2ScopeDO;
import org.shanzhaozhen.authorize.service.OAuth2ScopeService;
import org.shanzhaozhen.authorize.service.OAuth2ScopeService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Set;

/**
 * @Author: shanzhaozhen
 * @Date: 2022-06-17
 * @Description: oauth2客户端授权范围 服务实现类
 */
@Service
@RequiredArgsConstructor
public class OAuth2ScopeServiceImpl implements OAuth2ScopeService {

    private final OAuth2ScopeMapper oAuth2ScopeMapper;

    @Override
    public List<OAuth2ScopeDTO> getOAuth2ScopesByRegisteredClientId(String registeredClientId) {
        return oAuth2ScopeMapper.getOAuth2ScopesByRegisteredClientId(registeredClientId);
    }

    @Override
    @Transactional
    public void addOAuth2Scopes(String clientId, Set<OAuth2ScopeDTO> scopes) {
        if (CollectionUtils.isEmpty(scopes)) return;

        for (OAuth2ScopeDTO scope : scopes) {
            OAuth2ScopeDO oAuth2ScopeDO = OAuth2ScopeConverter.toDO(scope);
            this.oAuth2ScopeMapper.insert(oAuth2ScopeDO);
        }
    }

    @Override
    @Transactional
    public void updateOAuth2Scopes(String registeredClientId, Set<OAuth2ScopeDTO> clientAuthenticationMethods) {
        this.deleteOAuth2ScopesByRegisteredClientId(registeredClientId);
        this.addOAuth2Scopes(registeredClientId, clientAuthenticationMethods);
    }

    @Override
    @Transactional
    public void deleteOAuth2ScopesByRegisteredClientId(String registeredClientId) {
        oAuth2ScopeMapper.deleteOAuth2ScopesByRegisteredClientId(registeredClientId);
    }

}
