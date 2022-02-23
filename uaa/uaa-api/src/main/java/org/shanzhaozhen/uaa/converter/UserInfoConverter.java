package org.shanzhaozhen.uaa.converter;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.shanzhaozhen.uaa.pojo.dto.UserInfoDTO;
import org.shanzhaozhen.uaa.pojo.entity.UserInfoDO;
import org.shanzhaozhen.uaa.pojo.form.UserInfoForm;
import org.shanzhaozhen.uaa.pojo.vo.UserInfoVO;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;

public class UserInfoConverter {

    /**
     * UserInfoDTO 转换 UserInfoDO
     * @param userInfoDTO
     * @return
     */
    public static UserInfoDO toDO(UserInfoDTO userInfoDTO) {
        UserInfoDO userInfoDO = new UserInfoDO();
        BeanUtils.copyProperties(userInfoDTO, userInfoDO);
        return userInfoDO;
    }

    /**
     * UserInfoForm 转换 UserInfoDTO
     * @param userInfoForm
     * @return
     */
    public static UserInfoDTO toDTO(UserInfoForm userInfoForm) {
        UserInfoDTO userInfoDTO = new UserInfoDTO();
        BeanUtils.copyProperties(userInfoForm, userInfoDTO);
        return userInfoDTO;
    }

    /**
     * UserInfoVO 转换 UserInfoDTO
     * @param userInfoVO
     * @return
     */
    public static UserInfoDTO toDTO(UserInfoVO userInfoVO) {
        UserInfoDTO userInfoDTO = new UserInfoDTO();
        BeanUtils.copyProperties(userInfoVO, userInfoDTO);
        return userInfoDTO;
    }

    /**
     * UserInfoDO 转换 UserInfoDTO
     * @param userInfoDO
     * @return
     */
    public static UserInfoDTO toDTO(UserInfoDO userInfoDO) {
        UserInfoDTO userInfoDTO = new UserInfoDTO();
        BeanUtils.copyProperties(userInfoDO, userInfoDTO);
        return userInfoDTO;
    }

    /**
     * UserInfoDTO 转换 UserInfoVO
     * @param userInfoDTO
     * @return
     */
    public static UserInfoVO toVO(UserInfoDTO userInfoDTO) {
        UserInfoVO userInfoVO = new UserInfoVO();
        BeanUtils.copyProperties(userInfoDTO, userInfoVO);
        return userInfoVO;
    }

    /**
     * List<UserInfoDTO> 转换 List<UserInfoVO>
     * @param userInfoDTOList
     * @return
     */
    public static List<UserInfoVO> toVO(List<UserInfoDTO> userInfoDTOList) {
        List<UserInfoVO> userInfoVOList = new ArrayList<>();
        for (UserInfoDTO userInfoDTO : userInfoDTOList) {
            userInfoVOList.add(toVO(userInfoDTO));
        }
        return userInfoVOList;
    }

    /**
     * Page<UserInfoDTO> 转换 Page<UserInfoVO>
     * @param userInfoDTOPage
     * @return
     */
    public static Page<UserInfoVO> toVO(Page<UserInfoDTO> userInfoDTOPage) {
        List<UserInfoDTO> userInfoDTOList = userInfoDTOPage.getRecords();
        Page<UserInfoVO> userInfoVOPage = new Page<>();
        BeanUtils.copyProperties(userInfoDTOPage, userInfoVOPage);
        userInfoVOPage.setRecords(toVO(userInfoDTOList));
        return userInfoVOPage;
    }

}
