package org.shanzhaozhen.authorize.service.impl;

import org.shanzhaozhen.basiccommon.converter.MenuConverter;
import org.shanzhaozhen.basiccommon.domain.sys.MenuDO;
import org.shanzhaozhen.basiccommon.dto.MenuDTO;
import org.shanzhaozhen.basiccommon.utils.CustomBeanUtils;
import org.shanzhaozhen.basiccommon.utils.TreeUtils;
import org.shanzhaozhen.basiccommon.utils.UserDetailsUtils;
import org.shanzhaozhen.authorize.mapper.MenuMapper;
import org.shanzhaozhen.authorize.service.MenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.validation.constraints.NotEmpty;
import java.util.List;

@Service
@RequiredArgsConstructor
@CacheConfig(cacheNames = "menu")
public class MenuServiceImpl implements MenuService {

    private final MenuMapper menuMapper;

    @Override
    public List<MenuDTO> getMenuRoleListByType(Integer type) {
        return menuMapper.getMenuRoleListByUserId(null);
    }

    @Override
    public List<MenuDTO> getMenusByCurrentUser() {
        Long userId = UserDetailsUtils.getUserId();
        Assert.notNull(userId, "没有获取到当前的登录状态或为匿名用户");
        return this.getMenusByUserId(userId);
    }

    @Override
    @Cacheable(key = "#root.methodName + ':' + #userId")
    public List<MenuDTO> getMenusByUserId(Long userId) {
        List<MenuDTO> menuDTOList = menuMapper.getMenuRoleListByUserId(userId);
        return TreeUtils.builtTree(menuDTOList, MenuDTO.class);
    }

    @Override
    @Cacheable(key = "#root.methodName")
    public List<MenuDTO> getAllMenus() {
        return menuMapper.getAllMenus();
    }


    @Override
    @Cacheable(key = "#root.methodName")
    public List<MenuDTO> getMenuTree() {
        List<MenuDTO> menuDTOList = this.getAllMenus();
        return TreeUtils.builtTree(menuDTOList, MenuDTO.class);
    }

    @Override
    @Cacheable(key = "#root.methodName + ':' + #menuId")
    public MenuDTO getMenuById(Long menuId) {
        MenuDO menuDO = menuMapper.selectById(menuId);
        Assert.notNull(menuDO, "获取失败：没有找到该菜单或已被删除");
        return MenuConverter.toDTO(menuDO);
    }

    @Override
    @Transactional
    @CacheEvict(allEntries = true)
    public Long addMenu(MenuDTO menuDTO) {
        MenuDO menuDO = MenuConverter.toDO(menuDTO);
        menuMapper.insert(menuDO);
        return menuDO.getId();
    }

    @Override
    @Transactional
    @CacheEvict(allEntries = true)
    public Long updateMenu(MenuDTO menuDTO) {
        Assert.notNull(menuDTO.getId(), "更新失败：菜单id不能为空");
        Assert.isTrue(!menuDTO.getId().equals(menuDTO.getPid()), "更新失败：上级菜单不能选择自己");
        if (menuDTO.getPid() != null) {
            MenuDO menuPNode = menuMapper.selectById(menuDTO.getPid());
            Assert.notNull(menuPNode, "更新失败：没有找到该菜单的上级菜单或已被删除");
            Assert.isTrue(!menuDTO.getId().equals(menuPNode.getPid()), "更新失败：菜单之间不能互相引用");
        }
        MenuDO menuDO = menuMapper.selectById(menuDTO.getId());
        Assert.notNull(menuDO, "更新失败：没有找到该菜单或已被删除");
        CustomBeanUtils.copyPropertiesExcludeMeta(menuDTO, menuDO);
        menuMapper.updateById(menuDO);
        try {
            this.getMenuTree();
        } catch (StackOverflowError e) {
            throw new IllegalArgumentException("更新失败：请检查菜单节点间设置是否有问题");
        }
        return menuDO.getId();
    }

    @Override
    @Transactional
    @CacheEvict(allEntries = true)
    public Long deleteMenu(Long menuId) {
        menuMapper.deleteById(menuId);
        return menuId;
    }

    @Override
    @Transactional
    @CacheEvict(allEntries = true)
    public List<Long> batchDeleteMenu(@NotEmpty(message = "没有需要删除的菜单") List<Long> menuIds) {
        for (Long menuId : menuIds) {
            this.deleteMenu(menuId);
        }
        return menuIds;
    }

}
