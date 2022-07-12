package org.shanzhaozhen.uaa.converter;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.shanzhaozhen.uaa.pojo.dto.DepartmentDTO;
import org.shanzhaozhen.uaa.pojo.dto.DepartmentDTO;
import org.shanzhaozhen.uaa.pojo.entity.DepartmentDO;
import org.shanzhaozhen.uaa.pojo.form.DepartmentForm;
import org.shanzhaozhen.uaa.pojo.vo.DepartmentVO;
import org.shanzhaozhen.uaa.pojo.vo.DepartmentVO;
import org.springframework.beans.BeanUtils;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

public class DepartmentConverter {

    /**
     * DepartmentDTO 转换 DepartmentDO
     * @param departmentDTO
     * @return
     */
    public static DepartmentDO toDO(DepartmentDTO departmentDTO) {
        DepartmentDO departmentDO = new DepartmentDO();
        BeanUtils.copyProperties(departmentDTO, departmentDO);
        return departmentDO;
    }

    /**
     * DepartmentDO 转换 DepartmentDTO
     * @param departmentDO
     * @return
     */
    public static DepartmentDTO toDTO(DepartmentDO departmentDO) {
        DepartmentDTO departmentDTO = new DepartmentDTO();
        BeanUtils.copyProperties(departmentDO, departmentDTO);
        return departmentDTO;
    }

    /**
     * DepartmentForm 转换 DepartmentDTO
     * @param departmentForm
     * @return
     */
    public static DepartmentDTO toDTO(DepartmentForm departmentForm) {
        DepartmentDTO departmentDTO = new DepartmentDTO();
        BeanUtils.copyProperties(departmentForm, departmentDTO);
        return departmentDTO;
    }

    /**
     * DepartmentVO 转换 DepartmentDTO
     * @param departmentVO
     * @return
     */
    public static DepartmentDTO toDTO(DepartmentVO departmentVO) {
        DepartmentDTO departmentDTO = new DepartmentDTO();
        BeanUtils.copyProperties(departmentVO, departmentDTO);
        return departmentDTO;
    }

    /**
     * DepartmentDTO 转换 DepartmentVO
     * @param departmentDTO
     * @return
     */
    public static DepartmentVO toVO(DepartmentDTO departmentDTO) {
        DepartmentVO departmentVO = new DepartmentVO();
        BeanUtils.copyProperties(departmentDTO, departmentVO);
        if (!CollectionUtils.isEmpty(departmentDTO.getChildren())) {
            departmentVO.setChildren(toVO(departmentDTO.getChildren()));
        }
        return departmentVO;
    }

    /**
     * List<DepartmentDTO> 转换 List<DepartmentVO>
     * @param departmentDTOList
     * @return
     */
    public static List<DepartmentVO> toVO(List<DepartmentDTO> departmentDTOList) {
        List<DepartmentVO> departmentVOList = new ArrayList<>();
        for (DepartmentDTO departmentDTO : departmentDTOList) {
            departmentVOList.add(toVO(departmentDTO));
        }
        return departmentVOList;
    }

    /**
     * Page<DepartmentDTO> 转换 Page<DepartmentVO>
     * @param departmentDTOPage
     * @return
     */
    public static Page<DepartmentVO> toVO(Page<DepartmentDTO> departmentDTOPage) {
        List<DepartmentDTO> departmentDTOList = departmentDTOPage.getRecords();
        Page<DepartmentVO> departmentVOPage = new Page<>();
        BeanUtils.copyProperties(departmentDTOPage, departmentVOPage);
        departmentVOPage.setRecords(toVO(departmentDTOList));
        return departmentVOPage;
    }

}
