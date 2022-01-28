package org.shanzhaozhen.uaa.converter;

import org.shanzhaozhen.uaa.domain.PermissionDO;
import org.shanzhaozhen.uaa.dto.PermissionDTO;
import org.shanzhaozhen.uaa.form.PermissionForm;
import org.shanzhaozhen.uaa.vo.PermissionVO;
import org.springframework.beans.BeanUtils;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

public class PermissionConverter {

    /**
     * PermissionDTO 转换 PermissionDO
     * @param permissionDTO
     * @return
     */
    public static PermissionDO toDO(PermissionDTO permissionDTO) {
        PermissionDO permissionDO = new PermissionDO();
        BeanUtils.copyProperties(permissionDTO, permissionDO);
        return permissionDO;
    }

    /**
     * permissionForm 转换 PermissionDTO
     * @param permissionForm
     * @return
     */
    public static PermissionDTO toDTO(PermissionForm permissionForm) {
        PermissionDTO permissionDTO = new PermissionDTO();
        BeanUtils.copyProperties(permissionForm, permissionDTO);
        return permissionDTO;
    }

    /**
     * PermissionDO 转换 PermissionDTO
     * @param permissionDO
     * @return
     */
    public static PermissionDTO toDTO(PermissionDO permissionDO) {
        PermissionDTO permissionDTO = new PermissionDTO();
        BeanUtils.copyProperties(permissionDO, permissionDTO);
        return permissionDTO;
    }

    /**
     * PermissionDTO 转换 PermissionVO
     * @param permissionDTO
     * @return
     */
    public static PermissionVO toVO(PermissionDTO permissionDTO) {
        PermissionVO permissionVO = new PermissionVO();
        BeanUtils.copyProperties(permissionDTO, permissionVO);
        if (!CollectionUtils.isEmpty(permissionDTO.getChildren())) {
            permissionVO.setChildren(toVO(permissionDTO.getChildren()));
        }
        return permissionVO;
    }

    /**
     * List<PermissionDTO> 转换 List<PermissionVO>
     * @param permissionDTOList
     * @return
     */
    public static List<PermissionVO> toVO(List<PermissionDTO> permissionDTOList) {
        List<PermissionVO> permissionVOList = new ArrayList<>();
        for (PermissionDTO permissionDTO : permissionDTOList) {
            permissionVOList.add(toVO(permissionDTO));
        }
        return permissionVOList;
    }

}
