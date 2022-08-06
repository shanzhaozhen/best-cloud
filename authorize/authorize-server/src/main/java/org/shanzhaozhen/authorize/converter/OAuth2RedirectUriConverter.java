package org.shanzhaozhen.authorize.converter;

import org.shanzhaozhen.authorize.pojo.dto.OAuth2RedirectUriDTO;
import org.shanzhaozhen.authorize.pojo.entity.OAuth2RedirectUriDO;
import org.springframework.beans.BeanUtils;

public class OAuth2RedirectUriConverter {

    public static OAuth2RedirectUriDO toDO(OAuth2RedirectUriDTO oAuth2RedirectUriDTO) {
        OAuth2RedirectUriDO oAuth2RedirectUriDO = new OAuth2RedirectUriDO();
        BeanUtils.copyProperties(oAuth2RedirectUriDTO, oAuth2RedirectUriDO);
        return oAuth2RedirectUriDO;
    }

}
