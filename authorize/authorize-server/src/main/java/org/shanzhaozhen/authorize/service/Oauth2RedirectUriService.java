package org.shanzhaozhen.authorize.service;

import org.shanzhaozhen.authorize.pojo.dto.OAuth2RedirectUriDTO;

import java.util.List;

/**
 * <p>
 * oauth2客户端重定向uri表 服务类
 * </p>
 *
 * @author shanzhaozhen
 * @since 2022-06-17
 */
public interface Oauth2RedirectUriService {

    List<OAuth2RedirectUriDTO> getOauth2RedirectUriByClientId(String clientId);

}
