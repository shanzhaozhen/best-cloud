package org.shanzhaozhen.authorize.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.shanzhaozhen.authorize.pojo.entity.OAuth2AuthorizationDO;

/**
 * @Author: shanzhaozhen
 * @Date: 2022-06-17
 * @Description:
 */
public interface OAuth2AuthorizationMapper extends BaseMapper<OAuth2AuthorizationDO> {

    OAuth2AuthorizationDO getOAuth2AuthorizationByToken(@Param("token") String token);

    OAuth2AuthorizationDO getOAuth2AuthorizationByState(@Param("state") String state);

    OAuth2AuthorizationDO getOAuth2AuthorizationByAuthorizationCode(@Param("authorizationCode") String authorizationCode);

    OAuth2AuthorizationDO getOAuth2AuthorizationByAccessToken(@Param("accessToken") String accessToken);

    OAuth2AuthorizationDO getOAuth2AuthorizationByRefreshToken(@Param("refreshToken") String refreshToken);

}
