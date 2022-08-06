package org.shanzhaozhen.authorize.converter;

import org.shanzhaozhen.authorize.pojo.dto.OAuth2ClientAuthenticationMethodDTO;
import org.shanzhaozhen.authorize.pojo.entity.OAuth2ClientAuthenticationMethodDO;
import org.springframework.beans.BeanUtils;

public class OAuth2ClientAuthenticationMethodConverter {

    public static OAuth2ClientAuthenticationMethodDO toDO(OAuth2ClientAuthenticationMethodDTO oAuth2ClientAuthenticationMethodDTO) {
        OAuth2ClientAuthenticationMethodDO oAuth2ClientAuthenticationMethodDO = new OAuth2ClientAuthenticationMethodDO();
        BeanUtils.copyProperties(oAuth2ClientAuthenticationMethodDTO, oAuth2ClientAuthenticationMethodDO);
        return oAuth2ClientAuthenticationMethodDO;
    }

}
