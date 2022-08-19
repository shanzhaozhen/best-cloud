package org.shanzhaozhen.uaa.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.shanzhaozhen.uaa.pojo.dto.OAuth2AuthorizationDTO;
import org.shanzhaozhen.uaa.pojo.entity.OAuth2AuthorizationDO;

/**
 * @Author: shanzhaozhen
 * @Date: 2022-06-17
 * @Description:
 */
public interface OAuth2AuthorizationMapper extends BaseMapper<OAuth2AuthorizationDO> {

    OAuth2AuthorizationDTO getOAuth2AuthorizationById(@Param("token") String id);

    OAuth2AuthorizationDTO getOAuth2AuthorizationByToken(@Param("token") String token);

    OAuth2AuthorizationDTO getOAuth2AuthorizationByState(@Param("state") String state);

    OAuth2AuthorizationDTO getOAuth2AuthorizationByAuthorizationCode(@Param("authorizationCode") String authorizationCode);

    OAuth2AuthorizationDTO getOAuth2AuthorizationByAccessToken(@Param("accessToken") String accessToken);

    OAuth2AuthorizationDTO getOAuth2AuthorizationByRefreshToken(@Param("refreshToken") String refreshToken);

}
