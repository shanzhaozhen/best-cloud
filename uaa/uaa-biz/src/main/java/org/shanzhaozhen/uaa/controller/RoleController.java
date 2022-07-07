package org.shanzhaozhen.uaa.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Update;
import org.shanzhaozhen.common.core.entity.BasePageParams;
import org.shanzhaozhen.common.core.result.R;
import org.shanzhaozhen.uaa.converter.RoleConverter;
import org.shanzhaozhen.uaa.pojo.dto.RoleDTO;
import org.shanzhaozhen.uaa.pojo.form.RoleForm;
import org.shanzhaozhen.uaa.service.RoleService;
import org.shanzhaozhen.uaa.pojo.vo.RoleVO;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "role", description = "用户角色接口")
@RestController
@RequiredArgsConstructor
public class RoleController {

    private static final String GET_ROLE_PAGE = "/role/page";
    private static final String GET_ROLE_ALL = "/role/all";
    private static final String GET_ROLE_BY_ID = "/role/{roleId}";
    private static final String ADD_ROLE = "/role";
    private static final String UPDATE_ROLE = "/role";
    private static final String DELETE_ROLE = "/role/{roleId}";
    private static final String BATCH_DELETE_ROLE = "/role";

    private final RoleService roleService;

    @Operation(summary = "获取角色信息（分页）")
    @PostMapping(GET_ROLE_PAGE)
    public R<Page<RoleVO>> getRolePage(@RequestBody BasePageParams<RoleDTO> pageParams) {
        return R.build(() -> RoleConverter.toVO(roleService.getRolePage(pageParams.getPage(), pageParams.getKeyword())));
    }

    @Operation(summary = "获取所有角色")
    @GetMapping(GET_ROLE_ALL)
    public R<List<RoleVO>> getAllRoles() {
        return R.build(() -> RoleConverter.toVO(roleService.getAllRoles()));
    }

    @Operation(summary = "获取角色信息（通过角色id）")
    @GetMapping(GET_ROLE_BY_ID)
    public R<RoleVO> getRoleById(@PathVariable("roleId") @Parameter(description = "角色id", example = "1") Long roleId) {
        return R.build(() -> RoleConverter.toVO(roleService.getRoleById(roleId)));
    }

    @Operation(summary = "添加角色接口")
    @PostMapping(ADD_ROLE)
    public R<Long> addRole(@RequestBody @Validated({Insert.class}) RoleForm roleForm) {
        return R.build(() -> roleService.addRole(RoleConverter.toDTO(roleForm)));
    }

    @Operation(summary = "更新角色接口")
    @PutMapping(UPDATE_ROLE)
    public R<Long> updateRole(@RequestBody @Validated({Update.class}) RoleForm roleForm) {
        return R.build(() -> roleService.updateRole(RoleConverter.toDTO(roleForm)));
    }

    @Operation(summary = "删除角色接口")
    @DeleteMapping(DELETE_ROLE)
    public R<Long> deleteRole(@Parameter(description = "角色id", example = "[1, 2]")  @PathVariable Long roleId) {
        return R.build(() -> roleService.deleteRole(roleId));
    }

    @Operation(summary = "批量删除角色接口")
    @DeleteMapping(BATCH_DELETE_ROLE)
    public R<List<Long>> batchDeleteRole(@Parameter(description = "角色id", example = "[1, 2]") @RequestBody List<Long> roleIds) {
        return R.build(() -> roleService.batchDeleteRole(roleIds));
    }

}
