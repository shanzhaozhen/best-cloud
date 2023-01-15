package org.shanzhaozhen.oauth.converter;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.shanzhaozhen.oauth.pojo.dto.OAuth2UserDTO;
import org.shanzhaozhen.oauth.pojo.entity.OAuth2UserDO;
import org.shanzhaozhen.oauth.pojo.form.OAuth2UserForm;
import org.shanzhaozhen.oauth.pojo.vo.OAuth2UserVO;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;

public class OAuth2UserConverter {

    /**
     * OAuth2UserDTO 转换 OAuth2UserDO
     * @param userDTO
     * @return
     */
    public static OAuth2UserDO toDO(OAuth2UserDTO userDTO) {
        OAuth2UserDO oauth2UserDO = new OAuth2UserDO();
        BeanUtils.copyProperties(userDTO, oauth2UserDO);
        return oauth2UserDO;
    }

    /**
     * OAuth2UserDO 转换 OAuth2UserDTO
     * @param oauth2UserDO
     * @return
     */
    public static OAuth2UserDTO toDTO(OAuth2UserDO oauth2UserDO) {
        OAuth2UserDTO userDTO = new OAuth2UserDTO();
        BeanUtils.copyProperties(oauth2UserDO, userDTO);
        return userDTO;
    }

    /**
     * OAuth2UserVO 转换 OAuth2UserDTO
     * @param userVO
     * @return
     */
    public static OAuth2UserDTO toDTO(OAuth2UserVO userVO) {
        OAuth2UserDTO userDTO = new OAuth2UserDTO();
        BeanUtils.copyProperties(userVO, userDTO);
        return userDTO;
    }

    /**
     * OAuth2UserForm 转换 OAuth2UserDTO
     * @param oauth2UserForm
     * @return
     */
    public static OAuth2UserDTO toDTO(OAuth2UserForm oauth2UserForm) {
        OAuth2UserDTO userDTO = new OAuth2UserDTO();
        BeanUtils.copyProperties(oauth2UserForm, userDTO);
        return userDTO;
    }

    /**
     * OAuth2UserDTO 转换 OAuth2UserVO
     * @param userDTO
     * @return
     */
    public static OAuth2UserVO toVO(OAuth2UserDTO userDTO) {
        OAuth2UserVO userVO = new OAuth2UserVO();
        BeanUtils.copyProperties(userDTO, userVO);
        return userVO;
    }

    /**
     * List<OAuth2UserDTO> 转换 List<OAuth2UserVO>
     * @param userDTOList
     * @return
     */
    public static List<OAuth2UserVO> toVO(List<OAuth2UserDTO> userDTOList) {
        List<OAuth2UserVO> userVOList = new ArrayList<>();
        for (OAuth2UserDTO roleDTO : userDTOList) {
            userVOList.add(toVO(roleDTO));
        }
        return userVOList;
    }

    /**
     * Page<OAuth2UserDTO> 转换 Page<OAuth2UserVO>
     * @param userDTOPage
     * @return
     */
    public static Page<OAuth2UserVO> toVO(Page<OAuth2UserDTO> userDTOPage) {
        List<OAuth2UserDTO> userDTOList = userDTOPage.getRecords();
        Page<OAuth2UserVO> userVOPage = new Page<>();
        BeanUtils.copyProperties(userDTOPage, userVOPage);
        userVOPage.setRecords(toVO(userDTOList));
        return userVOPage;
    }
}
