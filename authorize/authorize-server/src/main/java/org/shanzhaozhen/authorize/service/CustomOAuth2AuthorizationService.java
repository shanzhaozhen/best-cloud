package org.shanzhaozhen.authorize.service;

import org.shanzhaozhen.authorize.pojo.entity.OAuth2AuthorizationDO;
import org.springframework.security.oauth2.server.authorization.OAuth2Authorization;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationService;

/**
 * @Author: shanzhaozhen
 * @Date: 2022-06-17
 * @Description:
 */
public interface CustomOAuth2AuthorizationService extends OAuth2AuthorizationService {

    OAuth2Authorization assembleOAuth2Authorization(OAuth2AuthorizationDO oAuth2AuthorizationDO);

}
