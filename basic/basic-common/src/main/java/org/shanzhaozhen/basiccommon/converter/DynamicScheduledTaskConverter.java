package org.shanzhaozhen.basiccommon.converter;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.shanzhaozhen.basiccommon.domain.sys.DynamicScheduledTaskDO;
import org.shanzhaozhen.basiccommon.dto.DynamicScheduledTaskDTO;
import org.shanzhaozhen.basiccommon.form.DynamicScheduledTaskForm;
import org.shanzhaozhen.basiccommon.vo.DynamicScheduledTaskVO;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;

public class DynamicScheduledTaskConverter {
    
    /**
     * DynamicScheduledTaskDTO 转换 DynamicScheduledTaskDO
     * @param dynamicScheduledTaskDTO
     * @return
     */
    public static DynamicScheduledTaskDO toDO(DynamicScheduledTaskDTO dynamicScheduledTaskDTO) {
        DynamicScheduledTaskDO dynamicScheduledTaskDO = new DynamicScheduledTaskDO();
        BeanUtils.copyProperties(dynamicScheduledTaskDTO, dynamicScheduledTaskDO);
        return dynamicScheduledTaskDO;
    }

    /**
     * DynamicScheduledTaskForm 转换 DynamicScheduledTaskDTO
     * @param dynamicScheduledTaskForm
     * @return
     */
    public static DynamicScheduledTaskDTO toDTO(DynamicScheduledTaskForm dynamicScheduledTaskForm) {
        DynamicScheduledTaskDTO dynamicScheduledTaskDTO = new DynamicScheduledTaskDTO();
        BeanUtils.copyProperties(dynamicScheduledTaskForm, dynamicScheduledTaskDTO);
        return dynamicScheduledTaskDTO;
    }

    /**
     * DynamicScheduledTaskVO 转换 DynamicScheduledTaskDTO
     * @param dynamicScheduledTaskVO
     * @return
     */
    public static DynamicScheduledTaskDTO toDTO(DynamicScheduledTaskVO dynamicScheduledTaskVO) {
        DynamicScheduledTaskDTO dynamicScheduledTaskDTO = new DynamicScheduledTaskDTO();
        BeanUtils.copyProperties(dynamicScheduledTaskVO, dynamicScheduledTaskDTO);
        return dynamicScheduledTaskDTO;
    }

    /**
     * DynamicScheduledTaskDO 转换 DynamicScheduledTaskDTO
     * @param dynamicScheduledTaskDO
     * @return
     */
    public static DynamicScheduledTaskDTO toDTO(DynamicScheduledTaskDO dynamicScheduledTaskDO) {
        DynamicScheduledTaskDTO dynamicScheduledTaskDTO = new DynamicScheduledTaskDTO();
        BeanUtils.copyProperties(dynamicScheduledTaskDO, dynamicScheduledTaskDTO);
        return dynamicScheduledTaskDTO;
    }

    /**
     * DynamicScheduledTaskDTO 转换 DynamicScheduledTaskVO
     * @param dynamicScheduledTaskDTO
     * @return
     */
    public static DynamicScheduledTaskVO toVO(DynamicScheduledTaskDTO dynamicScheduledTaskDTO) {
        DynamicScheduledTaskVO dynamicScheduledTaskVO = new DynamicScheduledTaskVO();
        BeanUtils.copyProperties(dynamicScheduledTaskDTO, dynamicScheduledTaskVO);
        return dynamicScheduledTaskVO;
    }

    /**
     * List<DynamicScheduledTaskDTO> 转换 List<DynamicScheduledTaskVO>
     * @param dynamicScheduledTaskDTOList
     * @return
     */
    public static List<DynamicScheduledTaskVO> toVO(List<DynamicScheduledTaskDTO> dynamicScheduledTaskDTOList) {
        List<DynamicScheduledTaskVO> dynamicScheduledTaskVOList = new ArrayList<>();
        for (DynamicScheduledTaskDTO dynamicScheduledTaskDTO : dynamicScheduledTaskDTOList) {
            dynamicScheduledTaskVOList.add(toVO(dynamicScheduledTaskDTO));
        }
        return dynamicScheduledTaskVOList;
    }

    /**
     * Page<DynamicScheduledTaskDTO> 转换 Page<DynamicScheduledTaskVO>
     * @param dynamicScheduledTaskDTOPage
     * @return
     */
    public static Page<DynamicScheduledTaskVO> toVO(Page<DynamicScheduledTaskDTO> dynamicScheduledTaskDTOPage) {
        List<DynamicScheduledTaskDTO> dynamicScheduledTaskDTOList = dynamicScheduledTaskDTOPage.getRecords();
        Page<DynamicScheduledTaskVO> dynamicScheduledTaskVOPage = new Page<>();
        BeanUtils.copyProperties(dynamicScheduledTaskDTOPage, dynamicScheduledTaskVOPage);
        dynamicScheduledTaskVOPage.setRecords(toVO(dynamicScheduledTaskDTOList));
        return dynamicScheduledTaskVOPage;
    }

}
