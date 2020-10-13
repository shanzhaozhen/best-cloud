package org.shanzhaozhen.basiccommon.converter;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.shanzhaozhen.basiccommon.domain.sys.RegionDO;
import org.shanzhaozhen.basiccommon.dto.RegionDTO;
import org.shanzhaozhen.basiccommon.form.RegionForm;
import org.shanzhaozhen.basiccommon.vo.RegionVO;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;

public class RegionConverter {

    /**
     * RegionDTO 转换 RegionDO
     * @param regionDTO
     * @return
     */
    public static RegionDO toDO(RegionDTO regionDTO) {
        RegionDO regionDO = new RegionDO();
        BeanUtils.copyProperties(regionDTO, regionDO);
        return regionDO;
    }

    /**
     * RegionForm 转换 RegionDTO
     * @param regionForm
     * @return
     */
    public static RegionDTO toDTO(RegionForm regionForm) {
        RegionDTO regionDTO = new RegionDTO();
        BeanUtils.copyProperties(regionForm, regionDTO);
        return regionDTO;
    }
    
    /**
     * RegionVO 转换 RegionDTO
     * @param regionVO
     * @return
     */
    public static RegionDTO toDTO(RegionVO regionVO) {
        RegionDTO regionDTO = new RegionDTO();
        BeanUtils.copyProperties(regionVO, regionDTO);
        if (regionVO.getChildren() != null && regionVO.getChildren().size() > 0) {
            regionDTO.setChildren(toDTO(regionVO.getChildren()));
        }
        return regionDTO;
    }

    /**
     * List<RegionDTO> 转换 List<RegionVO>
     * @param regionVOList
     * @return
     */
    public static List<RegionDTO> toDTO(List<RegionVO> regionVOList) {
        List<RegionDTO> regionDTOList = new ArrayList<>();
        for (RegionVO regionVO : regionVOList) {
            regionDTOList.add(toDTO(regionVO));
        }
        return regionDTOList;
    }

    /**
     * RegionDO 转换 RegionDTO
     * @param regionDO
     * @return
     */
    public static RegionDTO toDTO(RegionDO regionDO) {
        RegionDTO regionDTO = new RegionDTO();
        BeanUtils.copyProperties(regionDO, regionDTO);
        return regionDTO;
    }

    /**
     * RegionDTO 转换 RegionVO
     * @param regionDTO
     * @return
     */
    public static RegionVO toVO(RegionDTO regionDTO) {
        RegionVO regionVO = new RegionVO();
        BeanUtils.copyProperties(regionDTO, regionVO);
        if (regionDTO.getChildren() != null && regionDTO.getChildren().size() > 0) {
            regionVO.setChildren(toVO(regionDTO.getChildren()));
        }
        return regionVO;
    }

    /**
     * List<RegionDTO> 转换 List<RegionVO>
     * @param regionDTOList
     * @return
     */
    public static List<RegionVO> toVO(List<RegionDTO> regionDTOList) {
        List<RegionVO> regionVOList = new ArrayList<>();
        for (RegionDTO regionDTO : regionDTOList) {
            regionVOList.add(toVO(regionDTO));
        }
        return regionVOList;
    }

    /**
     * Page<RegionDTO> 转换 Page<RegionVO>
     * @param regionDTOPage
     * @return
     */
    public static Page<RegionVO> toVO(Page<RegionDTO> regionDTOPage) {
        List<RegionDTO> regionDTOList = regionDTOPage.getRecords();
        Page<RegionVO> regionVOPage = new Page<>();
        BeanUtils.copyProperties(regionDTOPage, regionVOPage);
        regionVOPage.setRecords(toVO(regionDTOList));
        return regionVOPage;
    }

    /**
     * 将区域信息list转成树状结构
     * @param allRegionDTOs
     * @return
     */
    public static List<RegionDTO> builtRegionTree(List<RegionDTO> allRegionDTOs) {

        List<RegionDTO> rootList = new ArrayList<>();
        List<RegionDTO> noRootList = new ArrayList<>();

        for (RegionDTO regionDTO : allRegionDTOs) {
            if (regionDTO.getPid() == null || regionDTO.getPid() <= 0) {
                rootList.add(regionDTO);
            } else {
                noRootList.add(regionDTO);
            }
        }

        getRegionChildren(noRootList, allRegionDTOs);

        return rootList;
    }

    /**
     * 对区域信息子节点进行递归查找
     * @param noRootList
     * @param children
     * @return
     */
    public static List<RegionDTO> getRegionChildren(List<RegionDTO> noRootList, List<RegionDTO> children) {
        for (RegionDTO child : children) {
            List<RegionDTO> grandsons = new ArrayList<>();
            for (RegionDTO noRoot : noRootList) {
                if (child.getId().equals(noRoot.getPid())) {
                    grandsons.add(noRoot);
                }
            }
            if (grandsons.size() > 0) {
                child.setChildren(getRegionChildren(noRootList, grandsons));
            }
        }

        return children;
    }
}
