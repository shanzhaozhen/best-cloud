package org.shanzhaozhen.authorize.service;

import org.shanzhaozhen.authorize.pojo.dto.OAuth2AuthorizationGrantTypeDTO;
import org.shanzhaozhen.authorize.pojo.dto.OAuth2AuthorizationGrantTypeDTO;

import java.util.List;
import java.util.Set;

/**
 * <p>
 * oauth2客户端授权方式表 服务类
 * </p>
 *
 * @author shanzhaozhen
 * @since 2022-06-17
 */
public interface OAuth2AuthorizationGrantTypeService {

    List<OAuth2AuthorizationGrantTypeDTO> getOAuth2AuthorizationGrantTypesByClientId(String clientId);

    void addOAuth2AuthorizationGrantTypes(String clientId, Set<OAuth2AuthorizationGrantTypeDTO> authorizationGrantTypes);

    void updateOAuth2AuthorizationGrantTypes(String clientId, Set<OAuth2AuthorizationGrantTypeDTO> authorizationGrantTypes);

    void deleteOAuth2AuthorizationGrantTypesByClientId(String clientId);

}
