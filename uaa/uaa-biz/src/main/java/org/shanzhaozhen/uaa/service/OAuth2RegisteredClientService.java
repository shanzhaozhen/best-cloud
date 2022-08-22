package org.shanzhaozhen.uaa.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.shanzhaozhen.uaa.pojo.dto.OAuth2RegisteredClientDTO;

/**
 * @Author: shanzhaozhen
 * @Date: 2022-06-17
 * @Description:
 */
public interface OAuth2RegisteredClientService {

    /**
     * 通过关键字查找 oauth2 客户端信息分页结果
     * @param page
     * @param keyword
     * @return
     */
    Page<OAuth2RegisteredClientDTO> getOAuth2RegisteredClientPage(Page<OAuth2RegisteredClientDTO> page, String keyword);

    /**
     * 通过 id 获取 oauth2 客户端信息
     * @param id
     * @return
     */
    OAuth2RegisteredClientDTO getOAuth2RegisteredClientById(String id);

    /**
     * 通过客户端 id 获取 oauth2 客户端信息
     * @param clientId
     * @return
     */
    OAuth2RegisteredClientDTO getOAuth2RegisteredClientByClientId(String clientId);

    /**
     * 组装客户端信息（获得 clientSettings 及 tokenSettings）
     * @param oAuth2RegisteredClientDTO
     * @return
     */
    OAuth2RegisteredClientDTO assembleOAuth2RegisteredClient(OAuth2RegisteredClientDTO oAuth2RegisteredClientDTO);

    /**
     * 添加或更新 OAuth2 客户端信息
     * @param oAuth2RegisteredClientDTO
     */
    void addOrUpdateOAuth2RegisteredClient(OAuth2RegisteredClientDTO oAuth2RegisteredClientDTO);

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
