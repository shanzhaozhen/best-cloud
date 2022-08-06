package org.shanzhaozhen.authorize.service;

import org.shanzhaozhen.authorize.pojo.dto.OAuth2RedirectUriDTO;
import org.shanzhaozhen.authorize.pojo.dto.OAuth2RedirectUriDTO;

import java.util.List;
import java.util.Set;

/**
 * <p>
 * oauth2客户端重定向uri表 服务类
 * </p>
 *
 * @author shanzhaozhen
 * @since 2022-06-17
 */
public interface OAuth2RedirectUriService {

    List<OAuth2RedirectUriDTO> getOAuth2RedirectUrisByRegisteredClientId(String registeredClientId);

    void addOAuth2RedirectUris(String clientId, Set<OAuth2RedirectUriDTO> redirectUris);

    void updateOAuth2RedirectUris(String clientId, Set<OAuth2RedirectUriDTO> redirectUris);

    void deleteOAuth2RedirectUrisByRegisteredClientId(String registeredClientId);

}
