package org.shanzhaozhen.authorize.service;

import org.shanzhaozhen.authorize.pojo.dto.OAuth2ScopeDTO;
import org.shanzhaozhen.authorize.pojo.dto.OAuth2ScopeDTO;

import java.util.List;
import java.util.Set;

/**
 * <p>
 * oauth2客户端授权范围 服务类
 * </p>
 *
 * @author shanzhaozhen
 * @since 2022-06-17
 */
public interface OAuth2ScopeService {

    List<OAuth2ScopeDTO> getOAuth2ScopesByRegisteredClientId(String registeredClientId);

    void addOAuth2Scopes(String clientId, Set<OAuth2ScopeDTO> scopes);

    void updateOAuth2Scopes(String clientId, Set<OAuth2ScopeDTO> scopes);

    void deleteOAuth2ScopesByRegisteredClientId(String registeredClientId);

}
