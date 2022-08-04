package org.shanzhaozhen.authorize.service;

import org.shanzhaozhen.authorize.pojo.dto.OAuth2ScopeDTO;

import java.util.List;

/**
 * <p>
 * oauth2客户端授权范围 服务类
 * </p>
 *
 * @author shanzhaozhen
 * @since 2022-06-17
 */
public interface Oauth2ScopeService {

    List<OAuth2ScopeDTO> getOauth2ScopeByClientId(String clientId);

}
