package org.shanzhaozhen.oauth.converter;


import org.shanzhaozhen.oauth.pojo.dto.OAuth2ClientSettingsDTO;
import org.shanzhaozhen.oauth.pojo.entity.OAuth2ClientSettingsDO;
import org.shanzhaozhen.oauth.pojo.form.OAuth2ClientSettingsForm;
import org.shanzhaozhen.oauth.pojo.vo.OAuth2ClientSettingsVO;
import org.springframework.beans.BeanUtils;

public class OAuth2ClientSettingsConverter {

    public static OAuth2ClientSettingsDO toDO(OAuth2ClientSettingsDTO oauth2ClientSettingsDTO) {
        if (oauth2ClientSettingsDTO == null) {
            return null;
        }
        OAuth2ClientSettingsDO oauth2ClientSettingsDO = new OAuth2ClientSettingsDO();
        BeanUtils.copyProperties(oauth2ClientSettingsDTO, oauth2ClientSettingsDO);
        return oauth2ClientSettingsDO;
    }

    public static OAuth2ClientSettingsDTO toDTO(OAuth2ClientSettingsForm oauth2ClientSettingsForm) {
        if (oauth2ClientSettingsForm == null) {
            return null;
        }
        OAuth2ClientSettingsDTO oauth2ClientSettingsDTO = new OAuth2ClientSettingsDTO();
        BeanUtils.copyProperties(oauth2ClientSettingsForm, oauth2ClientSettingsDTO);
        return oauth2ClientSettingsDTO;
    }

    public static OAuth2ClientSettingsDTO toDTO(OAuth2ClientSettingsVO oauth2ClientSettingsVO) {
        if (oauth2ClientSettingsVO == null) {
            return null;
        }
        OAuth2ClientSettingsDTO oauth2ClientSettingsDTO = new OAuth2ClientSettingsDTO();
        BeanUtils.copyProperties(oauth2ClientSettingsVO, oauth2ClientSettingsDTO);
        return oauth2ClientSettingsDTO;
    }

    public static OAuth2ClientSettingsVO toVO(OAuth2ClientSettingsDTO oauth2ClientSettingsDTO) {
        if (oauth2ClientSettingsDTO == null) {
            return null;
        }
        OAuth2ClientSettingsVO oauth2ClientSettingsVO = new OAuth2ClientSettingsVO();
        BeanUtils.copyProperties(oauth2ClientSettingsDTO, oauth2ClientSettingsVO);
        return oauth2ClientSettingsVO;
    }

}
