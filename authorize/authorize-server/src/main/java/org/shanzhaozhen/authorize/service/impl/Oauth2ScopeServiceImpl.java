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
    public List<OAuth2ScopeDTO> getOAuth2ScopesByClientId(String clientId) {
        return oAuth2ScopeMapper.getOAuth2ScopesByClientId(clientId);
    }

    @Override
    public void addOAuth2Scopes(String clientId, Set<OAuth2ScopeDTO> scopes) {
        if (CollectionUtils.isEmpty(scopes)) return;

        for (OAuth2ScopeDTO scope : scopes) {
            OAuth2ScopeDO oAuth2ScopeDO = OAuth2ScopeConverter.toDO(scope);
            this.oAuth2ScopeMapper.insert(oAuth2ScopeDO);
        }
    }

    @Override
    public void updateOAuth2Scopes(String clientId, Set<OAuth2ScopeDTO> clientAuthenticationMethods) {
        this.deleteOAuth2ScopesByClientId(clientId);
        this.addOAuth2Scopes(clientId, clientAuthenticationMethods);
    }

    @Override
    public void deleteOAuth2ScopesByClientId(String clientId) {
        oAuth2ScopeMapper.deleteOAuth2ScopesByClientId(clientId);
    }

}
