package org.shanzhaozhen.authorize.service;


import org.shanzhaozhen.oauth.pojo.dto.OAuth2AuthorizationDTO;

/**
 * @Author: shanzhaozhen
 * @Date: 2022-06-17
 * @Description:
 */
public interface OAuth2AuthorizationService {

    /**
     * 通过 id 获取用户授权信息
     * @param id
     * @return
     */
    OAuth2AuthorizationDTO getOAuth2AuthorizationById(String id);

    /**
     * 通过 token 及 tokenType 获取用户授权信息
     * @param token
     * @return
     */
    OAuth2AuthorizationDTO getOAuth2AuthorizationByToken(String token, String tokenType);

    /**
     * 保存用户授权信息
     * @param oauth2AuthorizationDTO
     * @return
     */
    void saveOAuth2Authorization(OAuth2AuthorizationDTO oauth2AuthorizationDTO);

    /**
     * 通过 id 删除用户授权信息
     * @param id
     * @return
     */
    void deleteOAuth2AuthorizationById(String id);

}
