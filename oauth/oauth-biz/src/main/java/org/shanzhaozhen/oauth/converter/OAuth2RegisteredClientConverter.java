package org.shanzhaozhen.oauth.converter;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.shanzhaozhen.oauth.pojo.dto.OAuth2ClientSettingsDTO;
import org.shanzhaozhen.oauth.pojo.dto.OAuth2RegisteredClientDTO;
import org.shanzhaozhen.oauth.pojo.dto.OAuth2TokenSettingsDTO;
import org.shanzhaozhen.oauth.pojo.entity.OAuth2RegisteredClientDO;
import org.shanzhaozhen.oauth.pojo.form.OAuth2ClientSettingsForm;
import org.shanzhaozhen.oauth.pojo.form.OAuth2RegisteredClientForm;
import org.shanzhaozhen.oauth.pojo.form.OAuth2TokenSettingsForm;
import org.shanzhaozhen.oauth.pojo.vo.OAuth2RegisteredClientVO;
import org.shanzhaozhen.uaa.pojo.dto.PermissionDTO;
import org.shanzhaozhen.uaa.pojo.dto.RoleDTO;
import org.shanzhaozhen.uaa.pojo.vo.RoleVO;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class OAuth2RegisteredClientConverter {

    public static OAuth2RegisteredClientDO toDO(OAuth2RegisteredClientDTO oauth2RegisteredClientDTO) {
        if (oauth2RegisteredClientDTO == null) {
            return null;
        }
        OAuth2RegisteredClientDO oauth2RegisteredClientDO = new OAuth2RegisteredClientDO();
        BeanUtils.copyProperties(oauth2RegisteredClientDTO, oauth2RegisteredClientDO);
        return oauth2RegisteredClientDO;
    }

    public static OAuth2RegisteredClientDTO toDTO(OAuth2RegisteredClientDO oauth2RegisteredClientDO) {
        if (oauth2RegisteredClientDO == null) {
            return null;
        }
        OAuth2RegisteredClientDTO oauth2RegisteredClientDTO = new OAuth2RegisteredClientDTO();
        BeanUtils.copyProperties(oauth2RegisteredClientDO, oauth2RegisteredClientDTO);
        return oauth2RegisteredClientDTO;
    }

    public static OAuth2RegisteredClientDTO toDTO(OAuth2RegisteredClientForm oauth2RegisteredClientForm) {
        if (oauth2RegisteredClientForm == null) {
            return null;
        }
        OAuth2RegisteredClientDTO oauth2RegisteredClientDTO = new OAuth2RegisteredClientDTO();
        BeanUtils.copyProperties(oauth2RegisteredClientForm, oauth2RegisteredClientDTO);

        oauth2RegisteredClientDTO.setClientSettings(OAuth2ClientSettingsConverter.toDTO(oauth2RegisteredClientForm.getClientSettings()));
        oauth2RegisteredClientDTO.setTokenSettings(OAuth2TokenSettingsConverter.toDTO(oauth2RegisteredClientForm.getTokenSettings()));

        return oauth2RegisteredClientDTO;
    }

    public static OAuth2RegisteredClientVO toVO(OAuth2RegisteredClientDTO oauth2RegisteredClientDTO) {
        if (oauth2RegisteredClientDTO == null) {
            return null;
        }
        OAuth2RegisteredClientVO oauth2RegisteredClientVO = new OAuth2RegisteredClientVO();
        BeanUtils.copyProperties(oauth2RegisteredClientDTO, oauth2RegisteredClientVO);

        OAuth2ClientSettingsDTO clientSettings = oauth2RegisteredClientDTO.getClientSettings();
        OAuth2TokenSettingsDTO tokenSettings = oauth2RegisteredClientDTO.getTokenSettings();

        oauth2RegisteredClientVO.setClientSettings(OAuth2ClientSettingsConverter.toVO(clientSettings));
        oauth2RegisteredClientVO.setTokenSettings(OAuth2TokenSettingsConverter.toVO(tokenSettings));

        return oauth2RegisteredClientVO;
    }

    public static List<OAuth2RegisteredClientVO> toVO(List<OAuth2RegisteredClientDTO> oauth2RegisteredClientDTOList) {
        List<OAuth2RegisteredClientVO> oauth2RegisteredClientVOList = new ArrayList<>();
        for (OAuth2RegisteredClientDTO oauth2RegisteredClientDTO : oauth2RegisteredClientDTOList) {
            oauth2RegisteredClientVOList.add(toVO(oauth2RegisteredClientDTO));
        }
        return oauth2RegisteredClientVOList;
    }

    public static Page<OAuth2RegisteredClientVO> toVO(Page<OAuth2RegisteredClientDTO> oauth2RegisteredClientDTOPage) {
        List<OAuth2RegisteredClientDTO> oauth2RegisteredClientDTOList = oauth2RegisteredClientDTOPage.getRecords();
        Page<OAuth2RegisteredClientVO> oauth2RegisteredClientVOPage = new Page<>();
        BeanUtils.copyProperties(oauth2RegisteredClientDTOPage, oauth2RegisteredClientVOPage);
        oauth2RegisteredClientVOPage.setRecords(toVO(oauth2RegisteredClientDTOList));
        return oauth2RegisteredClientVOPage;
    }
}
