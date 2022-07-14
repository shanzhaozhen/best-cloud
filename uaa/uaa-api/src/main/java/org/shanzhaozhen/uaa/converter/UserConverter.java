package org.shanzhaozhen.uaa.converter;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.shanzhaozhen.uaa.pojo.dto.DepartmentDTO;
import org.shanzhaozhen.uaa.pojo.dto.UserInfoDTO;
import org.shanzhaozhen.uaa.pojo.entity.UserDO;
import org.shanzhaozhen.uaa.pojo.dto.RoleDTO;
import org.shanzhaozhen.uaa.pojo.dto.UserDTO;
import org.shanzhaozhen.uaa.pojo.form.UserForm;
import org.shanzhaozhen.uaa.pojo.form.UserInfoForm;
import org.shanzhaozhen.uaa.pojo.form.UserLoginForm;
import org.shanzhaozhen.uaa.pojo.vo.UserVO;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;

public class UserConverter {

    /**
     * UserDTO 转换 UserDO
     * @param userDTO
     * @return
     */
    public static UserDO toDO(UserDTO userDTO) {
        UserDO userDO = new UserDO();
        BeanUtils.copyProperties(userDTO, userDO);
        return userDO;
    }

    /**
     * UserLoginForm 转换 UserDTO
     * @param userLoginForm
     * @return
     */
    public static UserDTO toDTO(UserLoginForm userLoginForm) {
        UserDTO userDTO = new UserDTO();
        BeanUtils.copyProperties(userLoginForm, userDTO);
        return userDTO;
    }

    /**
     * UserDO 转换 UserDTO
     * @param userDO
     * @return
     */
    public static UserDTO toDTO(UserDO userDO) {
        UserDTO userDTO = new UserDTO();
        BeanUtils.copyProperties(userDO, userDTO);
        return userDTO;
    }

    /**
     * UserVO 转换 UserDTO
     * @param userVO
     * @return
     */
    public static UserDTO toDTO(UserVO userVO) {
        UserDTO userDTO = new UserDTO();
        BeanUtils.copyProperties(userVO, userDTO);
        return userDTO;
    }

    /**
     * UserForm 转换 UserDTO
     * @param userForm
     * @return
     */
    public static UserDTO toDTO(UserForm userForm) {
        UserDTO userDTO = new UserDTO();
        BeanUtils.copyProperties(userForm, userDTO);

        UserInfoForm userInfoForm = userForm.getUserInfo();
        if (userInfoForm != null) {
            userDTO.setUserInfo(UserInfoConverter.toDTO(userInfoForm));
        }

        return userDTO;
    }

    /**
     * UserDTO 转换 UserVO
     * @param userDTO
     * @return
     */
    public static UserVO toVO(UserDTO userDTO) {
        UserVO userVO = new UserVO();
        BeanUtils.copyProperties(userDTO, userVO);
        UserInfoDTO userInfoDTO = userDTO.getUserInfo();
        if (userInfoDTO != null) {
            userVO.setUserInfo(UserInfoConverter.toVO(userInfoDTO));
        }
        DepartmentDTO departmentInfoDTO = userDTO.getDepartmentInfo();
        if (departmentInfoDTO != null) {
            userVO.setDepartmentInfo(DepartmentConverter.toVO(departmentInfoDTO));
        }
        List<RoleDTO> roles = userDTO.getRoles();
        if (roles != null && roles.size() > 0) {
            List<String> roleIds = new ArrayList<>();
            for (RoleDTO roleDTO : roles) {
                roleIds.add(roleDTO.getId());
            }
            userVO.setRoleIds(roleIds);
        }
        return userVO;
    }

    /**
     * List<UserDTO> 转换 List<UserVO>
     * @param userDTOList
     * @return
     */
    public static List<UserVO> toVO(List<UserDTO> userDTOList) {
        List<UserVO> userVOList = new ArrayList<>();
        for (UserDTO roleDTO : userDTOList) {
            userVOList.add(toVO(roleDTO));
        }
        return userVOList;
    }

    /**
     * Page<UserDTO> 转换 Page<UserVO>
     * @param userDTOPage
     * @return
     */
    public static Page<UserVO> toVO(Page<UserDTO> userDTOPage) {
        List<UserDTO> userDTOList = userDTOPage.getRecords();
        Page<UserVO> userVOPage = new Page<>();
        BeanUtils.copyProperties(userDTOPage, userVOPage);
        userVOPage.setRecords(toVO(userDTOList));
        return userVOPage;
    }
}
