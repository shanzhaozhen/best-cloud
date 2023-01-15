package org.shanzhaozhen.oauth.converter;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.shanzhaozhen.oauth.pojo.dto.OAuth2UserSocialDTO;
import org.shanzhaozhen.oauth.pojo.entity.OAuth2UserSocialDO;
import org.shanzhaozhen.oauth.pojo.vo.OAuth2UserSocialVO;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;

public class OAuth2UserSocialConverter {

    /**
     * OAuth2UserSocialDTO 转换 OAuth2UserSocialDO
     * @param oauth2UserSocialDTO
     * @return
     */
    public static OAuth2UserSocialDO toDO(OAuth2UserSocialDTO oauth2UserSocialDTO) {
        OAuth2UserSocialDO oauth2UserSocialDO = new OAuth2UserSocialDO();
        BeanUtils.copyProperties(oauth2UserSocialDTO, oauth2UserSocialDO);
        return oauth2UserSocialDO;
    }

    /**
     * OAuth2UserSocialVO 转换 OAuth2UserSocialDTO
     * @param oauth2UserSocialVO
     * @return
     */
    public static OAuth2UserSocialDTO toDTO(OAuth2UserSocialVO oauth2UserSocialVO) {
        OAuth2UserSocialDTO oauth2UserSocialDTO = new OAuth2UserSocialDTO();
        BeanUtils.copyProperties(oauth2UserSocialVO, oauth2UserSocialDTO);
        return oauth2UserSocialDTO;
    }

    /**
     * OAuth2UserSocialDO 转换 OAuth2UserSocialDTO
     * @param oauth2UserSocialDO
     * @return
     */
    public static OAuth2UserSocialDTO toDTO(OAuth2UserSocialDO oauth2UserSocialDO) {
        if (oauth2UserSocialDO == null) return null;
        OAuth2UserSocialDTO oauth2UserSocialDTO = new OAuth2UserSocialDTO();
        BeanUtils.copyProperties(oauth2UserSocialDO, oauth2UserSocialDTO);
        return oauth2UserSocialDTO;
    }

    /**
     * OAuth2UserSocialDTO 转换 OAuth2UserSocialVO
     * @param oauth2UserSocialDTO
     * @return
     */
    public static OAuth2UserSocialVO toVO(OAuth2UserSocialDTO oauth2UserSocialDTO) {
        if (oauth2UserSocialDTO == null) {
            return null;
        }
        OAuth2UserSocialVO oauth2UserSocialVO = new OAuth2UserSocialVO();
        BeanUtils.copyProperties(oauth2UserSocialDTO, oauth2UserSocialVO);
        return oauth2UserSocialVO;
    }

    /**
     * OAuth2UserSocialDO 转换 OAuth2UserSocialVO
     * @param oauth2UserSocialDO
     * @return
     */
    public static OAuth2UserSocialVO toVO(OAuth2UserSocialDO oauth2UserSocialDO) {
        if (oauth2UserSocialDO == null) {
            return null;
        }
        OAuth2UserSocialVO oauth2UserSocialVO = new OAuth2UserSocialVO();
        BeanUtils.copyProperties(oauth2UserSocialDO, oauth2UserSocialVO);
        return oauth2UserSocialVO;
    }

    /**
     * List<OAuth2UserSocialDTO> 转换 List<OAuth2UserSocialVO>
     * @param oauth2UserSocialDTOList
     * @return
     */
    public static List<OAuth2UserSocialVO> toVO(List<OAuth2UserSocialDTO> oauth2UserSocialDTOList) {
        List<OAuth2UserSocialVO> oauth2UserSocialVOList = new ArrayList<>();
        for (OAuth2UserSocialDTO oauth2UserSocialDTO : oauth2UserSocialDTOList) {
            oauth2UserSocialVOList.add(toVO(oauth2UserSocialDTO));
        }
        return oauth2UserSocialVOList;
    }

    /**
     * Page<OAuth2UserSocialDTO> 转换 Page<OAuth2UserSocialVO>
     * @param oauth2UserSocialDTOPage
     * @return
     */
    public static Page<OAuth2UserSocialVO> toVO(Page<OAuth2UserSocialDTO> oauth2UserSocialDTOPage) {
        List<OAuth2UserSocialDTO> oauth2UserSocialDTOList = oauth2UserSocialDTOPage.getRecords();
        Page<OAuth2UserSocialVO> oauth2UserSocialVOPage = new Page<>();
        BeanUtils.copyProperties(oauth2UserSocialDTOPage, oauth2UserSocialVOPage);
        oauth2UserSocialVOPage.setRecords(toVO(oauth2UserSocialDTOList));
        return oauth2UserSocialVOPage;
    }

}
