package org.shanzhaozhen.authorize.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.shanzhaozhen.authorize.pojo.vo.OAuth2RegisteredClientVO;
import org.shanzhaozhen.authorize.pojo.entity.OAuth2RegisteredClientDO;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;

/**
 * @Author: shanzhaozhen
 * @Date: 2022-06-17
 * @Description:
 */
public interface CustomOAuth2RegisteredClientService extends RegisteredClientRepository {

    /**
     * 通过关键字查找 oauth2 客户端信息分页结果
     * @param page
     * @param keyword
     * @return
     */
    Page<OAuth2RegisteredClientVO> getOAuth2RegisteredClientPage(Page<OAuth2RegisteredClientVO> page, String keyword);

    /**
     * 通过 id 获取 oauth2 客户端信息
     * @param id
     * @return
     */
    RegisteredClient getOAuth2RegisteredClientById(String id);

    /**
     * 通过客户端 id 获取 oauth2 客户端信息
     * @param clientId
     * @return
     */
    RegisteredClient getOAuth2RegisteredClientByClientId(String clientId);

    /**
     * 组装客户端信息（获得 clientSettings 及 tokenSettings）
     * @param oAuth2RegisteredClientDO
     * @return
     */
    RegisteredClient assembleOAuth2RegisteredClient(OAuth2RegisteredClientDO oAuth2RegisteredClientDO);

    /**
     * 通过id删除客户端信息
     * @param id
     */
    void deleteOAuth2RegisteredClientById(String id);

    /**
     * 通过客户端id删除客户端信息
     * @param clientId
     */
    void deleteOAuth2RegisteredClientByClientId(String clientId);

}
