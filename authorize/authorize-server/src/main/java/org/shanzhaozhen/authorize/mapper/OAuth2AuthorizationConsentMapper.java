package org.shanzhaozhen.authorize.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.shanzhaozhen.authorize.pojo.entity.OAuth2AuthorizationConsentDO;

/**
 * @Author: shanzhaozhen
 * @Date: 2022-06-17
 * @Description:
 */
public interface OAuth2AuthorizationConsentMapper extends BaseMapper<OAuth2AuthorizationConsentDO> {

    OAuth2AuthorizationConsentDO findOAuth2AuthorizationConsent(@Param("registeredClientId") String registeredClientId, @Param("principalName") String principalName);

    Integer deleteOAuth2AuthorizationConsent(@Param("registeredClientId") String registeredClientId, @Param("principalName") String principalName);

}
