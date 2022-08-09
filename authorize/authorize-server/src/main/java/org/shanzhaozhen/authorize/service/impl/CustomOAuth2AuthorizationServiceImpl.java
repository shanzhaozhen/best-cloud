package org.shanzhaozhen.authorize.service.impl;

import lombok.RequiredArgsConstructor;
import org.shanzhaozhen.authorize.mapper.OAuth2AuthorizationMapper;
import org.shanzhaozhen.authorize.service.CustomOAuth2AuthorizationService;
import org.springframework.security.oauth2.core.OAuth2TokenType;
import org.springframework.security.oauth2.server.authorization.OAuth2Authorization;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Author: shanzhaozhen
 * @Date: 2022-06-17
 * @Description:
 */
//@Service
    @RequiredArgsConstructor
public class CustomOAuth2AuthorizationServiceImpl implements CustomOAuth2AuthorizationService {

    private final OAuth2AuthorizationMapper oAuth2AuthorizationMapper;

    @Override
    @Transactional
    public void save(OAuth2Authorization authorization) {

    }

    @Override
    @Transactional
    public void remove(OAuth2Authorization authorization) {

    }

    @Override
    public OAuth2Authorization findById(String id) {
        return null;
    }

    @Override
    public OAuth2Authorization findByToken(String token, OAuth2TokenType tokenType) {
        return null;
    }

}
