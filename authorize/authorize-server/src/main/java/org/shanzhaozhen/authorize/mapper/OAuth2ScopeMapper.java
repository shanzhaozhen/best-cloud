package org.shanzhaozhen.authorize.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.shanzhaozhen.authorize.pojo.dto.OAuth2ClientAuthenticationMethodDTO;
import org.shanzhaozhen.authorize.pojo.dto.OAuth2ScopeDTO;
import org.shanzhaozhen.authorize.pojo.entity.OAuth2ScopeDO;

import java.util.List;

/**
 * @Author: shanzhaozhen
 * @Date: 2022-06-17
 * @Description: oauth2客户端授权范围 Mapper 接口
 */
public interface OAuth2ScopeMapper extends BaseMapper<OAuth2ScopeDO> {

    List<OAuth2ScopeDTO> getOAuth2ScopesByRegisteredClientId(@Param("registeredClientId") String registeredClientId);

    void deleteOAuth2ScopesByRegisteredClientId(@Param("registeredClientId") String registeredClientId);

}
