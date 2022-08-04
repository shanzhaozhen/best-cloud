package org.shanzhaozhen.authorize.service;

import org.shanzhaozhen.authorize.pojo.dto.OAuth2AuthorizationGrantTypeDTO;

import java.util.List;

/**
 * <p>
 * oauth2客户端授权方式表 服务类
 * </p>
 *
 * @author shanzhaozhen
 * @since 2022-06-17
 */
public interface OAuth2AuthorizationGrantTypeService {

    List<OAuth2AuthorizationGrantTypeDTO> getOAuth2AuthorizationGrantTypeByClientId(String clientId);

}
