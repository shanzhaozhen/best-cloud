package org.shanzhaozhen.authorize.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.shanzhaozhen.authorize.pojo.dto.OAuth2RegisteredClientDTO;
import org.shanzhaozhen.authorize.pojo.entity.OAuth2RegisteredClientDO;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;

/**
 * @Author: shanzhaozhen
 * @Date: 2022-06-17
 * @Description:
 */
public interface OAuth2RegisteredClientMapper extends BaseMapper<OAuth2RegisteredClientDO> {

    Page<OAuth2RegisteredClientDTO> getOAuth2RegisteredClientPage(Page<OAuth2RegisteredClientDTO> page, @Param("keyword") String keyword);

    OAuth2RegisteredClientDTO getOAuth2RegisteredClientById(@Param("id") String id);

    OAuth2RegisteredClientDTO getOAuth2RegisteredClientByClientId(@Param("clientId") String clientId);

}
