package org.shanzhaozhen.oauth.converter;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.shanzhaozhen.oauth.pojo.dto.OAuth2UserDTO;
import org.shanzhaozhen.oauth.pojo.dto.OAuth2UserInfoDTO;
import org.shanzhaozhen.oauth.pojo.entity.OAuth2UserDO;
import org.shanzhaozhen.oauth.pojo.form.OAuth2UserForm;
import org.shanzhaozhen.oauth.pojo.form.OAuth2UserInfoForm;
import org.shanzhaozhen.oauth.pojo.vo.OAuth2UserVO;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;

public class OAuth2UserConverter {

    /**
     * OAuth2UserDTO 转换 OAuth2UserDO
     * @param oauth2UserDTO
     * @return
     */
    public static OAuth2UserDO toDO(OAuth2UserDTO oauth2UserDTO) {
        OAuth2UserDO oauth2UserDO = new OAuth2UserDO();
        BeanUtils.copyProperties(oauth2UserDTO, oauth2UserDO);
        return oauth2UserDO;
    }

    /**
     * OAuth2UserDO 转换 OAuth2UserDTO
     * @param oauth2UserDO
     * @return
     */
    public static OAuth2UserDTO toDTO(OAuth2UserDO oauth2UserDO) {
        OAuth2UserDTO oauth2UserDTO = new OAuth2UserDTO();
        BeanUtils.copyProperties(oauth2UserDO, oauth2UserDTO);
        return oauth2UserDTO;
    }

    /**
     * OAuth2UserForm 转换 OAuth2UserDTO
     * @param oauth2UserForm
     * @return
     */
    public static OAuth2UserDTO toDTO(OAuth2UserForm oauth2UserForm) {
        OAuth2UserDTO oauth2UserDTO = new OAuth2UserDTO();
        BeanUtils.copyProperties(oauth2UserForm, oauth2UserDTO);

        OAuth2UserInfoForm oauth2UserInfoForm = oauth2UserForm.getUserInfo();
        if (oauth2UserInfoForm != null) {
            oauth2UserDTO.setUserInfo(OAuth2UserInfoConverter.toDTO(oauth2UserInfoForm));
        }
        return oauth2UserDTO;
    }

    /**
     * OAuth2UserDTO 转换 OAuth2UserVO
     * @param oauth2UserDTO
     * @return
     */
    public static OAuth2UserVO toVO(OAuth2UserDTO oauth2UserDTO) {
        OAuth2UserVO oauth2UserVO = new OAuth2UserVO();
        BeanUtils.copyProperties(oauth2UserDTO, oauth2UserVO);
        OAuth2UserInfoDTO oauth2UserInfoDTO = oauth2UserDTO.getUserInfo();
        if (oauth2UserInfoDTO != null) {
            oauth2UserVO.setUserInfo(OAuth2UserInfoConverter.toVO(oauth2UserInfoDTO));
        }
        return oauth2UserVO;
    }

    /**
     * List<OAuth2UserDTO> 转换 List<OAuth2UserVO>
     * @param oauth2UserDTOList
     * @return
     */
    public static List<OAuth2UserVO> toVO(List<OAuth2UserDTO> oauth2UserDTOList) {
        List<OAuth2UserVO> oauth2UserVOList = new ArrayList<>();
        for (OAuth2UserDTO roleDTO : oauth2UserDTOList) {
            oauth2UserVOList.add(toVO(roleDTO));
        }
        return oauth2UserVOList;
    }

    /**
     * Page<OAuth2UserDTO> 转换 Page<OAuth2UserVO>
     * @param oauth2UserDTOPage
     * @return
     */
    public static Page<OAuth2UserVO> toVO(Page<OAuth2UserDTO> oauth2UserDTOPage) {
        List<OAuth2UserDTO> oauth2UserDTOList = oauth2UserDTOPage.getRecords();
        Page<OAuth2UserVO> oauth2UserVOPage = new Page<>();
        BeanUtils.copyProperties(oauth2UserDTOPage, oauth2UserVOPage);
        oauth2UserVOPage.setRecords(toVO(oauth2UserDTOList));
        return oauth2UserVOPage;
    }
}
