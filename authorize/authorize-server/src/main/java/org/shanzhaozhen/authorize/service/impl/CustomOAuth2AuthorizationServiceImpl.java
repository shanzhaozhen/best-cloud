package org.shanzhaozhen.authorize.service.impl;

import lombok.RequiredArgsConstructor;
import org.shanzhaozhen.authorize.converter.OAuth2AuthorizationConverter;
import org.shanzhaozhen.authorize.mapper.OAuth2AuthorizationMapper;
import org.shanzhaozhen.authorize.pojo.entity.OAuth2AuthorizationDO;
import org.shanzhaozhen.authorize.service.CustomOAuth2AuthorizationService;
import org.shanzhaozhen.common.core.utils.CustomBeanUtils;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.jdbc.core.SqlParameterValue;
import org.springframework.security.oauth2.core.OAuth2TokenType;
import org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames;
import org.springframework.security.oauth2.server.authorization.OAuth2Authorization;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.sql.Types;
import java.util.Optional;

/**
 * @Author: shanzhaozhen
 * @Date: 2022-06-17
 * @Description:
 */
@Service
@RequiredArgsConstructor
public class CustomOAuth2AuthorizationServiceImpl implements CustomOAuth2AuthorizationService {

    private final OAuth2AuthorizationMapper oAuth2AuthorizationMapper;

    private final RegisteredClientRepository registeredClientRepository;

    @Override
    public OAuth2Authorization assembleOAuth2Authorization(OAuth2AuthorizationDO oAuth2AuthorizationDO) {
        String registeredClientId = oAuth2AuthorizationDO.getRegisteredClientId();
        RegisteredClient registeredClient = this.registeredClientRepository.findById(registeredClientId);
        if (registeredClient == null) {
            throw new DataRetrievalFailureException(
                    "The RegisteredClient with id '" + registeredClientId + "' was not found in the RegisteredClientRepository.");
        }
        return OAuth2AuthorizationConverter.toOAuth2Authorization(oAuth2AuthorizationDO, registeredClient);
    }

    @Override
    @Transactional
    public void save(OAuth2Authorization authorization) {
        OAuth2AuthorizationDO oAuth2Authorization = this.oAuth2AuthorizationMapper.selectById(authorization.getId());
        if (oAuth2Authorization == null) {
            oAuth2Authorization = OAuth2AuthorizationConverter.toDO(authorization);
            this.oAuth2AuthorizationMapper.insert(oAuth2Authorization);
        } else {
            OAuth2AuthorizationDO oAuth2AuthorizationDO = OAuth2AuthorizationConverter.toDO(authorization);
            CustomBeanUtils.copyPropertiesExcludeMetaAndNull(oAuth2AuthorizationDO, oAuth2Authorization);
            this.oAuth2AuthorizationMapper.updateById(oAuth2Authorization);
        }
    }

    @Override
    @Transactional
    public void remove(OAuth2Authorization authorization) {
        Assert.notNull(authorization, "authorization cannot be null");
        this.oAuth2AuthorizationMapper.deleteById(authorization.getId());
    }

    @Override
    public OAuth2Authorization findById(String id) {
        OAuth2AuthorizationDO oAuth2AuthorizationDO = this.oAuth2AuthorizationMapper.selectById(id);

        return oAuth2AuthorizationDO == null ? null : this.assembleOAuth2Authorization(oAuth2AuthorizationDO);
    }

    @Override
    public OAuth2Authorization findByToken(String token, OAuth2TokenType tokenType) {
        Assert.hasText(token, "token cannot be empty");

        OAuth2AuthorizationDO oAuth2AuthorizationDO = null;

        if (tokenType == null) {
            oAuth2AuthorizationDO = this.oAuth2AuthorizationMapper.getOAuth2AuthorizationByToken(token);
        } else if (OAuth2ParameterNames.STATE.equals(tokenType.getValue())) {
            oAuth2AuthorizationDO = this.oAuth2AuthorizationMapper.getOAuth2AuthorizationByState(token);
        } else if (OAuth2ParameterNames.CODE.equals(tokenType.getValue())) {
            oAuth2AuthorizationDO = this.oAuth2AuthorizationMapper.getOAuth2AuthorizationByAuthorizationCode(token);
        } else if (OAuth2TokenType.ACCESS_TOKEN.equals(tokenType)) {
            oAuth2AuthorizationDO = this.oAuth2AuthorizationMapper.getOAuth2AuthorizationByAccessToken(token);
        } else if (OAuth2TokenType.REFRESH_TOKEN.equals(tokenType)) {
            oAuth2AuthorizationDO = this.oAuth2AuthorizationMapper.getOAuth2AuthorizationByRefreshToken(token);
        }

        return oAuth2AuthorizationDO == null ? null : this.assembleOAuth2Authorization(oAuth2AuthorizationDO);
    }

}
