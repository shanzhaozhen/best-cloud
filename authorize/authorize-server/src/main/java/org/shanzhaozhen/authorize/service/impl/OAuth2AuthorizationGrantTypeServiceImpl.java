package org.shanzhaozhen.authorize.service.impl;

import lombok.RequiredArgsConstructor;
import org.shanzhaozhen.authorize.converter.OAuth2AuthorizationGrantTypeConverter;
import org.shanzhaozhen.authorize.mapper.OAuth2AuthorizationGrantTypeMapper;
import org.shanzhaozhen.authorize.pojo.dto.OAuth2AuthorizationGrantTypeDTO;
import org.shanzhaozhen.authorize.pojo.entity.OAuth2AuthorizationGrantTypeDO;
import org.shanzhaozhen.authorize.service.OAuth2AuthorizationGrantTypeService;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Set;

/**
 * @Author: shanzhaozhen
 * @Date: 2022-06-17
 * @Description: oauth2客户端授权方式表 服务实现类
 */
@Service
@RequiredArgsConstructor
public class OAuth2AuthorizationGrantTypeServiceImpl implements OAuth2AuthorizationGrantTypeService {

    private final OAuth2AuthorizationGrantTypeMapper oAuth2AuthorizationGrantTypeMapper;

    @Override
    public List<OAuth2AuthorizationGrantTypeDTO> getOAuth2AuthorizationGrantTypesByRegisteredClientId(String registeredClientId) {
        return oAuth2AuthorizationGrantTypeMapper.getOAuth2AuthorizationGrantTypesByRegisteredClientId(registeredClientId);
    }

    @Override
    public void addOAuth2AuthorizationGrantTypes(String clientId, Set<OAuth2AuthorizationGrantTypeDTO> authorizationGrantTypes) {
        if (CollectionUtils.isEmpty(authorizationGrantTypes)) return;

        for (OAuth2AuthorizationGrantTypeDTO authorizationGrantType : authorizationGrantTypes) {
            OAuth2AuthorizationGrantTypeDO oAuth2AuthorizationGrantTypeDO = OAuth2AuthorizationGrantTypeConverter.toDO(authorizationGrantType);
            this.oAuth2AuthorizationGrantTypeMapper.insert(oAuth2AuthorizationGrantTypeDO);
        }
    }

    @Override
    public void updateOAuth2AuthorizationGrantTypes(String registeredClientId, Set<OAuth2AuthorizationGrantTypeDTO> authorizationGrantTypes) {
        this.deleteOAuth2AuthorizationGrantTypesByRegisteredClientId(registeredClientId);
        this.addOAuth2AuthorizationGrantTypes(registeredClientId, authorizationGrantTypes);
    }

    @Override
    public void deleteOAuth2AuthorizationGrantTypesByRegisteredClientId(String registeredClientId) {
        oAuth2AuthorizationGrantTypeMapper.deleteOAuth2AuthorizationGrantTypesByRegisteredClientId(registeredClientId);
    }
}
