package org.shanzhaozhen.authorize.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.shanzhaozhen.authorize.pojo.dto.OAuth2ClientAuthenticationMethodDTO;
import org.shanzhaozhen.authorize.pojo.entity.OAuth2ClientAuthenticationMethodDO;

import java.util.List;

/**
 * @Author: shanzhaozhen
 * @Date: 2022-06-17
 * @Description: oauth2客户端认证方式表 Mapper 接口
 */
public interface OAuth2ClientAuthenticationMethodMapper extends BaseMapper<OAuth2ClientAuthenticationMethodDO> {

    List<OAuth2ClientAuthenticationMethodDTO> getOAuth2ClientAuthenticationMethodByRegisteredClientId(@Param("registeredClientId") String registeredClientId);

    void deleteOAuth2ClientAuthenticationMethodsByRegisteredClientId(@Param("registeredClientId") String registeredClientId);

}
