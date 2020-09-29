package org.shanzhaozhen.basiccommon.converter;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.shanzhaozhen.basiccommon.domain.sys.RoleDO;
import org.shanzhaozhen.basiccommon.dto.ResourceDTO;
import org.shanzhaozhen.basiccommon.dto.RoleDTO;
import org.shanzhaozhen.basiccommon.dto.RouteDTO;
import org.shanzhaozhen.basiccommon.form.RoleForm;
import org.shanzhaozhen.basiccommon.vo.RoleBase;
import org.shanzhaozhen.basiccommon.vo.RoleVO;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;

public class RoleConverter {

    /**
     * RoleDTO 转换 RoleDO
     * @param roleDTO
     * @return
     */
    public static RoleDO toDO(RoleDTO roleDTO) {
        RoleDO roleDO = new RoleDO();
        BeanUtils.copyProperties(roleDTO, roleDO);
        return roleDO;
    }

    /**
     * RoleForm 转换 RoleDTO
     * @param roleForm
     * @return
     */
    public static RoleDTO toDTO(RoleForm roleForm) {
        RoleDTO roleDTO = new RoleDTO();
        BeanUtils.copyProperties(roleForm, roleDTO);
        return roleDTO;
    }

    /**
     * RoleVO 转换 RoleDTO
     * @param roleVO
     * @return
     */
    public static RoleDTO toDTO(RoleVO roleVO) {
        RoleDTO roleDTO = new RoleDTO();
        BeanUtils.copyProperties(roleVO, roleDTO);
        return roleDTO;
    }

    /**
     * RoleDO 转换 RoleDTO
     * @param roleDO
     * @return
     */
    public static RoleDTO toDTO(RoleDO roleDO) {
        RoleDTO roleDTO = new RoleDTO();
        BeanUtils.copyProperties(roleDO, roleDTO);
        return roleDTO;
    }

    /**
     * RoleDTO 转换 RoleVO
     * @param roleDTO
     * @return
     */
    public static RoleVO toVO(RoleDTO roleDTO) {
        RoleVO roleVO = new RoleVO();
        BeanUtils.copyProperties(roleDTO, roleVO);
        List<RouteDTO> routes = roleDTO.getRoutes();
        if (routes != null && routes.size() > 0) {
            List<Long> routeIds = new ArrayList<>();
            for (RouteDTO routeDTO : routes) {
                routeIds.add(routeDTO.getId());
            }
            roleVO.setRouteIds(routeIds);
        }
        List<ResourceDTO> resources = roleDTO.getResources();
        if (resources != null && resources.size() > 0) {
            List<Long> resourceIds = new ArrayList<>();
            for (ResourceDTO resourceDTO : resources) {
                resourceIds.add(resourceDTO.getId());
            }
            roleVO.setResourceIds(resourceIds);
        }
        return roleVO;
    }

    /**
     * List<RoleDTO> 转换 List<RoleVO>
     * @param roleDTOList
     * @return
     */
    public static List<RoleVO> toVO(List<RoleDTO> roleDTOList) {
        List<RoleVO> roleVOList = new ArrayList<>();
        for (RoleDTO roleDTO : roleDTOList) {
            roleVOList.add(toVO(roleDTO));
        }
        return roleVOList;
    }

    /**
     * Page<RoleDTO> 转换 Page<RoleVO>
     * @param roleDTOPage
     * @return
     */
    public static Page<RoleVO> toVO(Page<RoleDTO> roleDTOPage) {
        List<RoleDTO> roleDTOList = roleDTOPage.getRecords();
        Page<RoleVO> roleVOPage = new Page<>();
        BeanUtils.copyProperties(roleDTOPage, roleVOPage);
        roleVOPage.setRecords(toVO(roleDTOList));
        return roleVOPage;
    }

    /**
     * List<RoleDTO> 转换 List<RoleBase>
     * @param roleDTOList
     * @return
     */
    public static List<RoleBase> toBase(List<RoleDTO> roleDTOList) {
        List<RoleBase> roleVOList = new ArrayList<>();
        for (RoleDTO roleDTO : roleDTOList) {
            roleVOList.add(toBase(roleDTO));
        }
        return roleVOList;
    }

    /**
     * RoleDTO 转换 RoleBase
     * @param roleDTO
     * @return
     */
    public static RoleBase toBase(RoleDTO roleDTO) {
        RoleBase roleBase = new RoleBase();
        BeanUtils.copyProperties(roleDTO, roleBase);
        return roleBase;
    }

}
