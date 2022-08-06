package org.shanzhaozhen.authorize.converter;

import org.shanzhaozhen.authorize.pojo.dto.OAuth2ScopeDTO;
import org.shanzhaozhen.authorize.pojo.entity.OAuth2ScopeDO;
import org.springframework.beans.BeanUtils;

public class OAuth2ScopeConverter {

    public static OAuth2ScopeDO toDO(OAuth2ScopeDTO oAuth2ScopeDTO) {
        OAuth2ScopeDO oAuth2ScopeDO = new OAuth2ScopeDO();
        BeanUtils.copyProperties(oAuth2ScopeDTO, oAuth2ScopeDO);
        return oAuth2ScopeDO;
    }

}
