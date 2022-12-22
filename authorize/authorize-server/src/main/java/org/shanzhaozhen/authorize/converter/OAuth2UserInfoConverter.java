package org.shanzhaozhen.authorize.converter;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.shanzhaozhen.authorize.pojo.dto.OAuth2UserInfoDTO;
import org.shanzhaozhen.authorize.pojo.entity.OAuth2UserInfoDO;
import org.shanzhaozhen.authorize.pojo.form.OAuth2UserInfoForm;
import org.shanzhaozhen.authorize.pojo.vo.OAuth2UserInfoVO;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;

public class OAuth2UserInfoConverter {

    /**
     * OAuth2UserInfoDTO 转换 OAuth2UserInfoDO
     * @param oauth2UserInfoDTO
     * @return
     */
    public static OAuth2UserInfoDO toDO(OAuth2UserInfoDTO oauth2UserInfoDTO) {
        OAuth2UserInfoDO oauth2UserInfoDO = new OAuth2UserInfoDO();
        BeanUtils.copyProperties(oauth2UserInfoDTO, oauth2UserInfoDO);
        return oauth2UserInfoDO;
    }

    /**
     * OAuth2UserInfoForm 转换 OAuth2UserInfoDTO
     * @param oauth2UserInfoForm
     * @return
     */
    public static OAuth2UserInfoDTO toDTO(OAuth2UserInfoForm oauth2UserInfoForm) {
        OAuth2UserInfoDTO oauth2UserInfoDTO = new OAuth2UserInfoDTO();
        BeanUtils.copyProperties(oauth2UserInfoForm, oauth2UserInfoDTO);
        return oauth2UserInfoDTO;
    }

    /**
     * OAuth2UserInfoVO 转换 OAuth2UserInfoDTO
     * @param oauth2UserInfoVO
     * @return
     */
    public static OAuth2UserInfoDTO toDTO(OAuth2UserInfoVO oauth2UserInfoVO) {
        OAuth2UserInfoDTO oauth2UserInfoDTO = new OAuth2UserInfoDTO();
        BeanUtils.copyProperties(oauth2UserInfoVO, oauth2UserInfoDTO);
        return oauth2UserInfoDTO;
    }

    /**
     * OAuth2UserInfoDO 转换 OAuth2UserInfoDTO
     * @param oauth2UserInfoDO
     * @return
     */
    public static OAuth2UserInfoDTO toDTO(OAuth2UserInfoDO oauth2UserInfoDO) {
        if (oauth2UserInfoDO == null) return null;
        OAuth2UserInfoDTO oauth2UserInfoDTO = new OAuth2UserInfoDTO();
        BeanUtils.copyProperties(oauth2UserInfoDO, oauth2UserInfoDTO);
        return oauth2UserInfoDTO;
    }

    /**
     * OAuth2UserInfoDTO 转换 OAuth2UserInfoVO
     * @param oauth2UserInfoDTO
     * @return
     */
    public static OAuth2UserInfoVO toVO(OAuth2UserInfoDTO oauth2UserInfoDTO) {
        if (oauth2UserInfoDTO == null) {
            return null;
        }
        OAuth2UserInfoVO oauth2UserInfoVO = new OAuth2UserInfoVO();
        BeanUtils.copyProperties(oauth2UserInfoDTO, oauth2UserInfoVO);
        return oauth2UserInfoVO;
    }

    /**
     * List<OAuth2UserInfoDTO> 转换 List<OAuth2UserInfoVO>
     * @param oauth2UserInfoDTOList
     * @return
     */
    public static List<OAuth2UserInfoVO> toVO(List<OAuth2UserInfoDTO> oauth2UserInfoDTOList) {
        List<OAuth2UserInfoVO> oauth2UserInfoVOList = new ArrayList<>();
        for (OAuth2UserInfoDTO oauth2UserInfoDTO : oauth2UserInfoDTOList) {
            oauth2UserInfoVOList.add(toVO(oauth2UserInfoDTO));
        }
        return oauth2UserInfoVOList;
    }

    /**
     * Page<OAuth2UserInfoDTO> 转换 Page<OAuth2UserInfoVO>
     * @param oauth2UserInfoDTOPage
     * @return
     */
    public static Page<OAuth2UserInfoVO> toVO(Page<OAuth2UserInfoDTO> oauth2UserInfoDTOPage) {
        List<OAuth2UserInfoDTO> oauth2UserInfoDTOList = oauth2UserInfoDTOPage.getRecords();
        Page<OAuth2UserInfoVO> oauth2UserInfoVOPage = new Page<>();
        BeanUtils.copyProperties(oauth2UserInfoDTOPage, oauth2UserInfoVOPage);
        oauth2UserInfoVOPage.setRecords(toVO(oauth2UserInfoDTOList));
        return oauth2UserInfoVOPage;
    }

}
