package org.shanzhaozhen.oauth.converter;

import org.shanzhaozhen.oauth.pojo.dto.OAuth2ClientSettingsDTO;
import org.shanzhaozhen.oauth.pojo.dto.OAuth2RegisteredClientDTO;
import org.shanzhaozhen.oauth.pojo.dto.OAuth2TokenSettingsDTO;
import org.shanzhaozhen.oauth.pojo.form.OAuth2ClientSettingsForm;
import org.shanzhaozhen.oauth.pojo.form.OAuth2RegisteredClientForm;
import org.shanzhaozhen.oauth.pojo.form.OAuth2TokenSettingsForm;
import org.springframework.beans.BeanUtils;

import java.util.Optional;

public class OAuth2RegisteredClientConverter {

    public static OAuth2RegisteredClientDTO toDTO(OAuth2RegisteredClientForm oAuth2RegisteredClientForm) {
        OAuth2RegisteredClientDTO oAuth2RegisteredClientDTO = new OAuth2RegisteredClientDTO();
        BeanUtils.copyProperties(oAuth2RegisteredClientForm, oAuth2RegisteredClientDTO);

        OAuth2ClientSettingsForm clientSettings = oAuth2RegisteredClientForm.getClientSettings();
        OAuth2TokenSettingsForm tokenSettings = oAuth2RegisteredClientForm.getTokenSettings();

        oAuth2RegisteredClientDTO.setClientSettings(Optional.ofNullable(clientSettings).map(o -> {
            OAuth2ClientSettingsDTO oAuth2ClientSettingsDTO = new OAuth2ClientSettingsDTO();
            BeanUtils.copyProperties(clientSettings, oAuth2ClientSettingsDTO);
            return oAuth2ClientSettingsDTO;
        }).orElse(null));

        oAuth2RegisteredClientDTO.setTokenSettings(Optional.ofNullable(tokenSettings).map(o -> {
            OAuth2TokenSettingsDTO oAuth2TokenSettingsDTO = new OAuth2TokenSettingsDTO();
            BeanUtils.copyProperties(tokenSettings, oAuth2TokenSettingsDTO);
            return oAuth2TokenSettingsDTO;
        }).orElse(null));

        return oAuth2RegisteredClientDTO;
    }


}
