package org.shanzhaozhen.authorize.service.impl;

import lombok.RequiredArgsConstructor;
import org.shanzhaozhen.authorize.converter.OAuth2AuthorizationConverter;
import org.shanzhaozhen.authorize.mapper.OAuth2AuthorizationMapper;
import org.shanzhaozhen.authorize.pojo.dto.OAuth2AuthorizationDTO;
import org.shanzhaozhen.authorize.pojo.entity.OAuth2AuthorizationDO;
import org.shanzhaozhen.common.core.utils.CustomBeanUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames;
import org.springframework.security.oauth2.server.authorization.OAuth2Authorization;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.OAuth2TokenType;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;


/**
 * @Author: shanzhaozhen
 * @Date: 2022-06-17
 * @Description:
 */
@Service
@RequiredArgsConstructor
public class OAuth2AuthorizationServiceImpl implements OAuth2AuthorizationService {

    private final RegisteredClientRepository registeredClientRepository;

    private final OAuth2AuthorizationMapper oauth2AuthorizationMapper;

    @Override
    public void save(OAuth2Authorization authorization) {
        OAuth2AuthorizationDTO oauth2AuthorizationDTO = OAuth2AuthorizationConverter.toDTO(authorization);

        OAuth2AuthorizationDO oauth2Authorization = this.oauth2AuthorizationMapper.selectById(authorization.getId());
        if (oauth2Authorization == null) {
            oauth2Authorization = new OAuth2AuthorizationDO();
            BeanUtils.copyProperties(oauth2AuthorizationDTO, oauth2Authorization);
            this.oauth2AuthorizationMapper.insert(oauth2Authorization);
        } else {
            CustomBeanUtils.copyPropertiesExcludeMetaAndNull(oauth2AuthorizationDTO, oauth2Authorization);
            this.oauth2AuthorizationMapper.updateById(oauth2Authorization);
        }
    }

    @Override
    public void remove(OAuth2Authorization authorization) {
        this.oauth2AuthorizationMapper.deleteById(authorization.getId());
    }

    @Override
    public OAuth2Authorization findById(String id) {
        OAuth2AuthorizationDTO oauth2AuthorizationDTO = oauth2AuthorizationMapper.getOAuth2AuthorizationById(id);
        Assert.notNull(oauth2AuthorizationDTO, "未能找到用户登陆信息！");

        String registeredClientId = oauth2AuthorizationDTO.getRegisteredClientId();
        RegisteredClient registeredClient = findRegisteredClientByIdAndCheck(registeredClientId);

        return OAuth2AuthorizationConverter.toOAuth2Authorization(oauth2AuthorizationDTO, registeredClient);
    }

    @Override
    public OAuth2Authorization findByToken(String token, OAuth2TokenType tokenType) {
        Assert.hasText(token, "token 不能为空！");

        OAuth2AuthorizationDTO oauth2AuthorizationDTO = null;
        if (tokenType == null) {
            oauth2AuthorizationDTO = oauth2AuthorizationMapper.getOAuth2AuthorizationByToken(token);
        } else if (OAuth2ParameterNames.STATE.equals(tokenType.getValue())) {
            oauth2AuthorizationDTO = oauth2AuthorizationMapper.getOAuth2AuthorizationByState(token);
        } else if (OAuth2ParameterNames.CODE.equals(tokenType.getValue())) {
            oauth2AuthorizationDTO = oauth2AuthorizationMapper.getOAuth2AuthorizationByAuthorizationCode(token);
        } else if (OAuth2ParameterNames.ACCESS_TOKEN.equals(tokenType.getValue())) {
            oauth2AuthorizationDTO = oauth2AuthorizationMapper.getOAuth2AuthorizationByAccessToken(token);
        } else if (OAuth2ParameterNames.REFRESH_TOKEN.equals(tokenType.getValue())) {
            oauth2AuthorizationDTO = oauth2AuthorizationMapper.getOAuth2AuthorizationByRefreshToken(token);
        }

        if (oauth2AuthorizationDTO == null) {
            return null;
        }

        RegisteredClient registeredClient = findRegisteredClientByIdAndCheck(oauth2AuthorizationDTO.getRegisteredClientId());

        return OAuth2AuthorizationConverter.toOAuth2Authorization(oauth2AuthorizationDTO, registeredClient);
    }

    public RegisteredClient findRegisteredClientByIdAndCheck(String registeredClientId) {
        RegisteredClient registeredClient = this.registeredClientRepository.findById(registeredClientId);
        if (registeredClient == null) {
            throw new DataRetrievalFailureException(
                    "The RegisteredClient with id '" + registeredClientId + "' was not found in the RegisteredClientRepository.");
        }
        return registeredClient;
    }

}
