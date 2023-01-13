package org.shanzhaozhen.uaa.controller;

import org.shanzhaozhen.common.core.result.R;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.shanzhaozhen.uaa.converter.MenuConverter;
import org.shanzhaozhen.uaa.pojo.form.MenuForm;
import org.shanzhaozhen.uaa.service.MenuService;
import org.shanzhaozhen.uaa.pojo.vo.MenuVO;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "menu", description = "用户菜单接口")
@RestController
@RequiredArgsConstructor
public class MenuController {

    private static final String GET_ALL_MENU = "/menu/all";
    private static final String GET_ALL_MENU_TREE = "/menu/tree";
    private static final String GET_MENU_BY_PID = "/menu/pid";
    private static final String GET_MENU_BY_ID = "/menu/{menuId}";
    private static final String ADD_MENU = "/menu";
    private static final String UPDATE_MENU = "/menu";
    private static final String DELETE_MENU = "/menu/{menuId}";
    private static final String BATCH_DELETE_MENU = "/menu";
    private static final String GET_MENU_CURRENT = "/menu/current";

    private final MenuService menuService;

    @Operation(summary = "获取所有菜单信息")
    @GetMapping(GET_ALL_MENU)
    public R<List<MenuVO>> getAllMenus() {
        return R.build(() -> MenuConverter.toVO(menuService.getAllMenus()));
    }

    @Operation(summary = "获取所有菜单信息（树状结构）")
    @GetMapping(GET_ALL_MENU_TREE)
    public R<List<MenuVO>> getMenuTree() {
        return R.build(() -> MenuConverter.toVO(menuService.getMenuTree()));
    }

    @Operation(summary = "通过父级ID获取菜单列表")
    @GetMapping(GET_MENU_BY_PID)
    public R<List<MenuVO>> getMenuByPid(@Parameter(description = "父级id", example = "1") String pid) {
        return R.build(() -> MenuConverter.toVO(menuService.getMenuByPid(pid)));
    }

    @Operation(summary = "获取菜单信息（通过菜单id）")
    @GetMapping(GET_MENU_BY_ID)
    public R<MenuVO> getMenuById(@PathVariable("menuId") @Parameter(description = "菜单id", example = "1") String menuId) {
        return R.build(() -> MenuConverter.toVO(menuService.getMenuById(menuId)));
    }

    @Operation(summary = "添加菜单接口")
    @PostMapping(ADD_MENU)
    public R<String> addMenu(@RequestBody @Validated MenuForm menuForm) {
        return R.build(() -> menuService.addMenu(MenuConverter.toDTO(menuForm)));
    }

    @Operation(summary = "更新菜单接口")
    @PutMapping(UPDATE_MENU)
    public R<String> updateMenu(@RequestBody @Validated MenuForm menuForm) {
        return R.build(() -> menuService.updateMenu(MenuConverter.toDTO(menuForm)));
    }

    @Operation(summary = "删除菜单接口")
    @DeleteMapping(DELETE_MENU)
    public R<String> deleteMenu(@PathVariable("menuId") @Parameter(description = "菜单id", example = "1") String menuId) {
        return R.build(() -> menuService.deleteMenu(menuId));
    }

    @Operation(summary = "批量删除菜单接口")
    @DeleteMapping(BATCH_DELETE_MENU)
    public R<List<String>> batchDeleteMenu(@Parameter(description = "菜单id", example = "[1, 2]") @RequestBody List<String> menuIds) {
        return R.build(() -> menuService.batchDeleteMenu(menuIds));
    }

    @Operation(summary = "获取当前用户允许访问菜单")
    @GetMapping(GET_MENU_CURRENT)
    public R<List<MenuVO>> getMenusByCurrentUser() {
        return R.build(() -> MenuConverter.toVO(menuService.getMenusByCurrentUser()));
    }

}
