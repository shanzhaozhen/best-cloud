package org.shanzhaozhen.oauth.converter;


import org.shanzhaozhen.oauth.pojo.dto.OAuth2TokenSettingsDTO;
import org.shanzhaozhen.oauth.pojo.entity.OAuth2TokenSettingsDO;
import org.shanzhaozhen.oauth.pojo.form.OAuth2TokenSettingsForm;
import org.shanzhaozhen.oauth.pojo.vo.OAuth2TokenSettingsVO;
import org.springframework.beans.BeanUtils;

public class OAuth2TokenSettingsConverter {

    public static OAuth2TokenSettingsDO toDO(OAuth2TokenSettingsDTO oauth2TokenSettingsDTO) {
        if (oauth2TokenSettingsDTO == null) {
            return null;
        }
        OAuth2TokenSettingsDO OAuth2TokenSettingsDO = new OAuth2TokenSettingsDO();
        BeanUtils.copyProperties(oauth2TokenSettingsDTO, OAuth2TokenSettingsDO);
        return OAuth2TokenSettingsDO;
    }

    public static OAuth2TokenSettingsDTO toDTO(OAuth2TokenSettingsForm oauth2TokenSettingsForm) {
        if (oauth2TokenSettingsForm == null) {
            return null;
        }
        OAuth2TokenSettingsDTO OAuth2TokenSettingsDTO = new OAuth2TokenSettingsDTO();
        BeanUtils.copyProperties(oauth2TokenSettingsForm, OAuth2TokenSettingsDTO);
        return OAuth2TokenSettingsDTO;
    }

    public static OAuth2TokenSettingsDTO toDTO(OAuth2TokenSettingsVO oauth2TokenSettingsVO) {
        if (oauth2TokenSettingsVO == null) {
            return null;
        }
        OAuth2TokenSettingsDTO OAuth2TokenSettingsDTO = new OAuth2TokenSettingsDTO();
        BeanUtils.copyProperties(oauth2TokenSettingsVO, OAuth2TokenSettingsDTO);
        return OAuth2TokenSettingsDTO;
    }

    public static OAuth2TokenSettingsVO toVO(OAuth2TokenSettingsDTO oauth2TokenSettingsDTO) {
        if (oauth2TokenSettingsDTO == null) {
            return null;
        }
        OAuth2TokenSettingsVO OAuth2TokenSettingsVO = new OAuth2TokenSettingsVO();
        BeanUtils.copyProperties(oauth2TokenSettingsDTO, OAuth2TokenSettingsVO);
        return OAuth2TokenSettingsVO;
    }
    
}
