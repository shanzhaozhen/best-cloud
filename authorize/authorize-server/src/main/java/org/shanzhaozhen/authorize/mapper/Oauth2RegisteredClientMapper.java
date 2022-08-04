package org.shanzhaozhen.authorize.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.shanzhaozhen.authorize.pojo.dto.Oauth2RegisteredClientDTO;
import org.shanzhaozhen.authorize.pojo.entity.Oauth2RegisteredClientDO;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;

/**
 * @Author: shanzhaozhen
 * @Date: 2022-06-17
 * @Description:
 */
public interface Oauth2RegisteredClientMapper extends BaseMapper<Oauth2RegisteredClientDO> {

    Page<Oauth2RegisteredClientDTO> getOauth2RegisteredClientPage(Page<Oauth2RegisteredClientDTO> page, @Param("keyword") String keyword);

    Oauth2RegisteredClientDTO getOauth2RegisteredClientById(@Param("id") String id);

    Oauth2RegisteredClientDTO getOauth2RegisteredClientByClientId(@Param("clientId") String clientId);

}
