package org.shanzhaozhen.authorize.service.impl;

import lombok.RequiredArgsConstructor;
import org.shanzhaozhen.authorize.converter.OAuth2AuthorizationConverter;
import org.shanzhaozhen.uaa.feign.OAuth2AuthorizationClient;
import org.shanzhaozhen.uaa.pojo.dto.OAuth2AuthorizationDTO;
import org.springframework.dao.DataRetrievalFailureException;
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

    private final OAuth2AuthorizationClient oAuth2AuthorizationClient;

    @Override
    public void save(OAuth2Authorization authorization) {
        oAuth2AuthorizationClient.saveOAuth2Authorization(OAuth2AuthorizationConverter.toDTO(authorization));
    }

    @Override
    public void remove(OAuth2Authorization authorization) {
        oAuth2AuthorizationClient.deleteOAuth2AuthorizationById(authorization.getId());
    }

    @Override
    public OAuth2Authorization findById(String id) {
        OAuth2AuthorizationDTO oAuth2AuthorizationDTO = oAuth2AuthorizationClient.getOAuth2AuthorizationById(id);
        Assert.notNull(oAuth2AuthorizationDTO, "未能找到用户登陆信息！");

        String registeredClientId = oAuth2AuthorizationDTO.getRegisteredClientId();
        RegisteredClient registeredClient = findRegisteredClientByIdAndCheck(registeredClientId);

        return OAuth2AuthorizationConverter.toOAuth2Authorization(oAuth2AuthorizationDTO, registeredClient);
    }

    @Override
    public OAuth2Authorization findByToken(String token, OAuth2TokenType tokenType) {
        Assert.hasText(token, "token 不能为空！");

        OAuth2AuthorizationDTO oAuth2AuthorizationDTO =
                oAuth2AuthorizationClient.getOAuth2AuthorizationByToken(token, tokenType == null ? null : tokenType.getValue());
        if (oAuth2AuthorizationDTO == null) {
            return null;
        }

        RegisteredClient registeredClient = findRegisteredClientByIdAndCheck(oAuth2AuthorizationDTO.getRegisteredClientId());

        return OAuth2AuthorizationConverter.toOAuth2Authorization(oAuth2AuthorizationDTO, registeredClient);
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
