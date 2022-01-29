package org.shanzhaozhen.uaa.service;

import org.shanzhaozhen.uaa.pojo.dto.MenuDTO;

import java.util.List;

public interface MenuService {

    /**
     * 通过 MenuType 类型获取所有的Menu（多对多含有角色信息）
     * @param type
     * @return
     */
    List<MenuDTO> getMenuRoleListByType(Integer type);

    /**
     * 通过当前用户的信息生成对应的前端菜单
     * @return
     */
    List<MenuDTO> getMenusByCurrentUser();

    /**
     * 通过用户ID获取前端菜单
     * @return
     */
    List<MenuDTO> getMenusByUserId(Long userId);


    /**
     * 获取所有菜单的树形结构
     * @return
     */
    List<MenuDTO> getAllMenus();
    
    /**
     * 获取所有菜单的树形结构
     * @return
     */
    List<MenuDTO> getMenuTree();

    /**
     * 通过菜单id获取菜单实体
     * @param menuId
     * @return
     */
    MenuDTO getMenuById(Long menuId);

    /**
     * 增加菜单
     * @param menuDTO
     * @return
     */
    Long addMenu(MenuDTO menuDTO);

    /**
     * 修改菜单
     * @param menuDTO
     * @return
     */
    Long updateMenu(MenuDTO menuDTO);

    /**
     * 删除菜单(通过菜单id删除)
     * @param menuId
     * @return
     */
    Long deleteMenu(Long menuId);

    /**
     * 批量删除菜单
     * @param menuIds
     * @return
     */
    List<Long> batchDeleteMenu(List<Long> menuIds);
}
