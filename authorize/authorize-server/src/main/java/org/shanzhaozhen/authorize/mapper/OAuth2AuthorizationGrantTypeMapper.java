package org.shanzhaozhen.authorize.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.shanzhaozhen.authorize.pojo.dto.OAuth2AuthorizationGrantTypeDTO;
import org.shanzhaozhen.authorize.pojo.dto.OAuth2ScopeDTO;
import org.shanzhaozhen.authorize.pojo.entity.OAuth2AuthorizationGrantTypeDO;

import java.util.List;

/**
 * @Author: shanzhaozhen
 * @Date: 2022-06-17
 * @Description: oauth2客户端授权方式表 Mapper 接口
 */
public interface OAuth2AuthorizationGrantTypeMapper extends BaseMapper<OAuth2AuthorizationGrantTypeDO> {

    List<OAuth2AuthorizationGrantTypeDTO> getOAuth2AuthorizationGrantTypesByClientId(@Param("clientId") String clientId);

    void deleteOAuth2AuthorizationGrantTypesByClientId(@Param("clientId") String clientId);


}
