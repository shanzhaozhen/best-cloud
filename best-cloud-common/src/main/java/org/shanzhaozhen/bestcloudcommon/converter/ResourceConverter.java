package org.shanzhaozhen.bestcloudcommon.converter;

import org.shanzhaozhen.bestcommon.domain.sys.ResourceDO;
import org.shanzhaozhen.bestcommon.dto.ResourceDTO;
import org.shanzhaozhen.bestcommon.form.ResourceForm;
import org.shanzhaozhen.bestcommon.vo.ResourceVO;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class ResourceConverter {

    /**
     * ResourceDTO 转换 ResourceDO
     * @param resourceDTO
     * @return
     */
    public static ResourceDO toDO(ResourceDTO resourceDTO) {
        ResourceDO resourceDO = new ResourceDO();
        BeanUtils.copyProperties(resourceDTO, resourceDO);
        return resourceDO;
    }

    /**
     * resourceForm 转换 ResourceDTO
     * @param resourceForm
     * @return
     */
    public static ResourceDTO toDTO(ResourceForm resourceForm) {
        ResourceDTO resourceDTO = new ResourceDTO();
        BeanUtils.copyProperties(resourceForm, resourceDTO);
        return resourceDTO;
    }

    /**
     * ResourceDO 转换 ResourceDTO
     * @param resourceDO
     * @return
     */
    public static ResourceDTO toDTO(ResourceDO resourceDO) {
        ResourceDTO resourceDTO = new ResourceDTO();
        BeanUtils.copyProperties(resourceDO, resourceDTO);
        return resourceDTO;
    }

    /**
     * ResourceDTO 转换 ResourceVO
     * @param resourceDTO
     * @return
     */
    public static ResourceVO toVO(ResourceDTO resourceDTO) {
        ResourceVO resourceVO = new ResourceVO();
        BeanUtils.copyProperties(resourceDTO, resourceVO);
        if (resourceDTO.getChildren() != null && resourceDTO.getChildren().size() > 0) {
            resourceVO.setChildren(toVO(resourceDTO.getChildren()));
        }
        return resourceVO;
    }

    /**
     * List<ResourceDTO> 转换 List<ResourceVO>
     * @param resourceDTOList
     * @return
     */
    public static List<ResourceVO> toVO(List<ResourceDTO> resourceDTOList) {
        List<ResourceVO> resourceVOList = new ArrayList<>();
        for (ResourceDTO resourceDTO : resourceDTOList) {
            resourceVOList.add(toVO(resourceDTO));
        }
        return resourceVOList;
    }

    /**
     * 将资源list转成树状结构
     * @param resourceDTOList
     * @return
     */
    public static List<ResourceDTO> builtResourceTree(List<ResourceDTO> resourceDTOList) {
        List<ResourceDTO> rootList = new ArrayList<>();
        List<ResourceDTO> noRootList = new ArrayList<>();

        for (ResourceDTO resourceDTO : resourceDTOList) {
            if (resourceDTO.getPid() == null || resourceDTO.getPid() <= 0) {
                rootList.add(resourceDTO);
            } else {
                noRootList.add(resourceDTO);
            }
        }

        getResourceChildren(noRootList, resourceDTOList);

        rootList.sort((Comparator.comparing(ResourceDTO::getPriority)));

        return rootList;
    }

    /**
     * 对动态路由子节点进行递归查找
     * @param noRootList
     * @param children
     * @return
     */
    public static List<ResourceDTO> getResourceChildren(List<ResourceDTO> noRootList, List<ResourceDTO> children) {
        for (ResourceDTO child : children) {
            List<ResourceDTO> grandsons = new ArrayList<>();
            for (ResourceDTO noRoot : noRootList) {
                if (child.getId().equals(noRoot.getPid())) {
                    grandsons.add(noRoot);
                }
            }
            if (grandsons.size() > 0) {
                child.setChildren(getResourceChildren(noRootList, grandsons));
            }
        }
        children.sort((Comparator.comparing(ResourceDTO::getPriority)));

        return children;
    }

}
