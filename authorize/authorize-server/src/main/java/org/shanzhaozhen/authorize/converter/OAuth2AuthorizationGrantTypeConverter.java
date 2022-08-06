package org.shanzhaozhen.authorize.converter;

import org.shanzhaozhen.authorize.pojo.dto.OAuth2AuthorizationGrantTypeDTO;
import org.shanzhaozhen.authorize.pojo.entity.OAuth2AuthorizationGrantTypeDO;
import org.springframework.beans.BeanUtils;

public class OAuth2AuthorizationGrantTypeConverter {

    public static OAuth2AuthorizationGrantTypeDO toDO(OAuth2AuthorizationGrantTypeDTO oAuth2AuthorizationGrantTypeDTO) {
        OAuth2AuthorizationGrantTypeDO oAuth2AuthorizationGrantTypeDO = new OAuth2AuthorizationGrantTypeDO();
        BeanUtils.copyProperties(oAuth2AuthorizationGrantTypeDTO, oAuth2AuthorizationGrantTypeDO);
        return oAuth2AuthorizationGrantTypeDO;
    }

}
