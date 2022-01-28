package org.shanzhaozhen.uaa.controller;

import org.shanzhaozhen.common.enums.PermissionType;
import org.shanzhaozhen.common.result.R;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.shanzhaozhen.uaa.converter.PermissionConverter;
import org.shanzhaozhen.uaa.form.PermissionForm;
import org.shanzhaozhen.uaa.service.PermissionService;
import org.shanzhaozhen.uaa.vo.PermissionVO;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "permission", description = "后台权限接口")
@RestController
@RequiredArgsConstructor
public class PermissionController {

    private static final String GET_ALL_RESOURCE_TREE = "/permission/tree";
    private static final String GET_ALL_RESOURCE_ROOT_TREE = "/permission/root-tree";
    private static final String GET_RESOURCE_BY_ID = "/permission/{permissionId}";
    private static final String ADD_RESOURCE = "/permission";
    private static final String UPDATE_RESOURCE = "/permission";
    private static final String DELETE_RESOURCE = "/permission/{permissionId}";
    private static final String BATCH_DELETE_RESOURCE = "/permission";

    private final PermissionService permissionService;

    @Operation(summary = "获取所有权限（树状结构）")
    @GetMapping(GET_ALL_RESOURCE_TREE)
    public R<List<PermissionVO>> getPermissionTree() {
        return R.build(() -> PermissionConverter.toVO(permissionService.getPermissionTreeByType(null)));
    }

    @Operation(summary = "获取所有父节点权限（树状结构）")
    @GetMapping(GET_ALL_RESOURCE_ROOT_TREE)
    public R<List<PermissionVO>> getAllPermissionRootTree() {
        return R.build(() -> PermissionConverter.toVO(permissionService.getPermissionTreeByType(PermissionType.KID.getValue())));
    }


    @Operation(summary = "获取权限（通过权限id）")
    @GetMapping(GET_RESOURCE_BY_ID)
    public R<PermissionVO> getPermissionById(@PathVariable("permissionId") @Parameter(description = "权限id", example = "1") Long permissionId) {
        return R.build(() -> PermissionConverter.toVO(permissionService.getPermissionById(permissionId)));
    }

    @Operation(summary = "权限添加接口")
    @PostMapping(ADD_RESOURCE)
    public R<Long> addPermission(@RequestBody @Validated PermissionForm permissionForm) {
        return R.build(() -> permissionService.addPermission(PermissionConverter.toDTO(permissionForm)));
    }

    @Operation(summary = "权限更新接口")
    @PutMapping(UPDATE_RESOURCE)
    public R<Long> updatePermission(@RequestBody @Validated PermissionForm permissionForm) {
        return R.build(() -> permissionService.updatePermission(PermissionConverter.toDTO(permissionForm)));
    }

    @Operation(summary = "权限删除接口")
    @DeleteMapping(DELETE_RESOURCE)
    public R<Long> deletePermission(@PathVariable("permissionId") @Parameter(description = "权限id", example = "1") Long permissionId) {
        return R.build(() -> permissionService.deletePermission(permissionId));
    }

    @Operation(summary = "批量权限删除接口")
    @DeleteMapping(BATCH_DELETE_RESOURCE)
    public R<List<Long>> batchDeletePermission(@Parameter(description = "权限id", example = "1") @RequestBody List<Long> permissionIds) {
        return R.build(() -> permissionService.batchDeletePermission(permissionIds));
    }

}
