package org.shanzhaozhen.basiccommon.converter;

import org.shanzhaozhen.basiccommon.domain.sys.MenuDO;
import org.shanzhaozhen.basiccommon.dto.RoleDTO;
import org.shanzhaozhen.basiccommon.dto.MenuDTO;
import org.shanzhaozhen.basiccommon.form.MenuForm;
import org.shanzhaozhen.basiccommon.vo.MenuVO;
import org.springframework.beans.BeanUtils;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

public class                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                       MenuConverter {

    /**
     * MenuDTO 转换 MenuDO
     * @param menuDTO
     * @return
     */
    public static MenuDO toDO(MenuDTO menuDTO) {
        MenuDO menuDO = new MenuDO();
        BeanUtils.copyProperties(menuDTO, menuDO);
        return menuDO;
    }

    /**
     * MenuDO 转换 MenuDTO
     * @param menuDO
     * @return
     */
    public static MenuDTO toDTO(MenuDO menuDO) {
        MenuDTO menuDTO = new MenuDTO();
        BeanUtils.copyProperties(menuDO, menuDTO);
        return menuDTO;
    }

    /**
     * MenuForm 转换 MenuDTO
     * @param menuForm
     * @return
     */
    public static MenuDTO toDTO(MenuForm menuForm) {
        MenuDTO menuDTO = new MenuDTO();
        BeanUtils.copyProperties(menuForm, menuDTO);
        return menuDTO;
    }

    /**
     * MenuVO 转换 MenuDTO
     * @param menuVO
     * @return
     */
    public static MenuDTO toDTO(MenuVO menuVO) {
        MenuDTO menuDTO = new MenuDTO();
        BeanUtils.copyProperties(menuVO, menuDTO);
        return menuDTO;
    }

    /**
     * MenuDTO 转换 MenuVO
     * @param menuDTO
     * @return
     */
    public static MenuVO toVO(MenuDTO menuDTO) {
        MenuVO menuVO = new MenuVO();
        BeanUtils.copyProperties(menuDTO, menuVO);
        if (!CollectionUtils.isEmpty(menuDTO.getChildren())) {
            menuVO.setChildren(toVO(menuDTO.getChildren()));
        }
        return menuVO;
    }

    /**
     * List<MenuDTO> 转换 List<MenuVO>
     * @param menuDTOList
     * @return
     */
    public static List<MenuVO> toVO(List<MenuDTO> menuDTOList) {
        List<MenuVO> menuVOList = new ArrayList<>();
        for (MenuDTO menuDTO : menuDTOList) {
            menuVOList.add(toVO(menuDTO));
        }
        return menuVOList;
    }

    /**
     * 将MenuDTO转换成MenuVO供给前端渲染使用
     * @param menuDTO
     * @return
     */
    public static MenuVO toMenuVO(MenuDTO menuDTO) {
        MenuVO menuVO = new MenuVO();
        BeanUtils.copyProperties(menuDTO, menuVO);
        if (!CollectionUtils.isEmpty(menuDTO.getRoles())) {
            List<RoleDTO> roleDTOList = menuDTO.getRoles();
            List<String> roles = new ArrayList<>();
            for (RoleDTO roleDTO : roleDTOList) {
                roles.add(roleDTO.getCode());
            }
            menuVO.setAccess(roles);
        }
        if (!CollectionUtils.isEmpty(menuDTO.getChildren())) {
            menuVO.setChildren(toMenuVO(menuDTO.getChildren()));
        }
        return menuVO;
    }

    /**
     * 批量将MenuDTOList转换成MenuVO供给前端渲染使用
     * @param menuDTOList
     * @return
     */
    public static List<MenuVO> toMenuVO(List<MenuDTO> menuDTOList) {
        List<MenuVO> menus = new ArrayList<>();
        for (MenuDTO menuDTO : menuDTOList) {
            menus.add(toMenuVO(menuDTO));
        }
        return menus;
    }

}
