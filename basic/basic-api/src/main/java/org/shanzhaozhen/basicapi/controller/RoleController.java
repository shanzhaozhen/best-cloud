package org.shanzhaozhen.basicapi.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Update;
import org.shanzhaozhen.basiccommon.converter.RoleConverter;
import org.shanzhaozhen.basiccommon.dto.RoleDTO;
import org.shanzhaozhen.basiccommon.form.BaseSearchForm;
import org.shanzhaozhen.basiccommon.form.RoleForm;
import org.shanzhaozhen.basiccommon.vo.ResultObject;
import org.shanzhaozhen.basiccommon.vo.RoleVO;
import org.shanzhaozhen.basicservice.service.RoleService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api("用户角色接口")
@RestController
public class RoleController {

    private static final String GET_ROLE_PAGE = "/role/page";
    private static final String GET_ROLE_ALL = "/role/all";
    private static final String GET_ROLE_BY_ID = "/role/{roleId}";
    private static final String ADD_ROLE = "/role";
    private static final String UPDATE_ROLE = "/role";
    private static final String DELETE_ROLE = "/role/{roleId}";

    private final RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @PostMapping(GET_ROLE_PAGE)
    @ApiOperation("获取角色信息（分页）")
    public ResultObject<Page<RoleVO>> getRolePage(@RequestBody BaseSearchForm<RoleDTO> baseSearchForm) {
        return ResultObject.build(result -> RoleConverter.toVO(roleService.getRolePage(baseSearchForm)));
    }

    @GetMapping(GET_ROLE_ALL)
    @ApiOperation("获取所有角色")
    public ResultObject<List<RoleVO>> getAllRoles() {
        return ResultObject.build(result -> RoleConverter.toVO(roleService.getAllRoles()));
    }

    @GetMapping(GET_ROLE_BY_ID)
    @ApiOperation("获取角色信息（通过角色id）")
    public ResultObject<RoleVO> getRoleByRoleId(@PathVariable("roleId") @ApiParam(name = "角色id", example = "1") Long roleId) {
        return ResultObject.build(result -> RoleConverter.toVO(roleService.getRoleById(roleId)));
    }

    @PostMapping(ADD_ROLE)
    @ApiOperation("添加角色接口")
    public ResultObject<Long> addRole(@RequestBody @Validated({Insert.class}) RoleForm roleForm) {
        return ResultObject.build(result -> roleService.addRole(RoleConverter.toDTO(roleForm)));
    }

    @PutMapping(UPDATE_ROLE)
    @ApiOperation("更新角色接口")
    public ResultObject<Long> updateRole(@RequestBody @Validated({Update.class}) RoleForm roleForm) {
        return ResultObject.build(result -> roleService.updateRole(RoleConverter.toDTO(roleForm)));
    }

    @DeleteMapping(DELETE_ROLE)
    @ApiOperation("删除角色接口")
    public ResultObject<Long> deleteRole(@PathVariable("roleId") @ApiParam(name = "角色id", example = "1") Long roleId) {
        return ResultObject.build(result -> roleService.deleteRole(roleId));
    }

}
