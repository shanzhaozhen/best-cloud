package org.shanzhaozhen.uaa.controller;

import org.shanzhaozhen.common.core.enums.PermissionType;
import org.shanzhaozhen.common.core.result.R;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.shanzhaozhen.uaa.converter.PermissionConverter;
import org.shanzhaozhen.uaa.pojo.form.PermissionForm;
import org.shanzhaozhen.uaa.service.PermissionService;
import org.shanzhaozhen.uaa.pojo.vo.PermissionVO;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "permission", description = "后台权限接口")
@RestController
@RequiredArgsConstructor
public class PermissionController {

    private static final String GET_ALL_PERMISSION_TREE = "/permission/tree";
    private static final String GET_ALL_PERMISSION_ROOT_TREE = "/permission/root-tree";
    private static final String GET_PERMISSION_BY_PID = "/permission/pid";
    private static final String GET_PERMISSION_BY_ID = "/permission/{permissionId}";
    private static final String ADD_PERMISSION = "/permission";
    private static final String UPDATE_PERMISSION = "/permission";
    private static final String DELETE_PERMISSION = "/permission/{permissionId}";
    private static final String BATCH_DELETE_PERMISSION = "/permission";

    private final PermissionService permissionService;

    @Operation(summary = "获取所有权限（树状结构）")
    @GetMapping(GET_ALL_PERMISSION_TREE)
    public R<List<PermissionVO>> getPermissionTree() {
        return R.build(() -> PermissionConverter.toVO(permissionService.getPermissionTreeByType(null)));
    }

    @Operation(summary = "通过父级ID获取权限列表")
    @GetMapping(GET_PERMISSION_BY_PID)
    public R<List<PermissionVO>> getMenuByPid(@Parameter(description = "父级id", example = "1") String pid) {
        return R.build(() -> PermissionConverter.toVO(permissionService.getPermissionByPid(pid)));
    }

    @Operation(summary = "获取所有父节点权限（树状结构）")
    @GetMapping(GET_ALL_PERMISSION_ROOT_TREE)
    public R<List<PermissionVO>> getAllPermissionRootTree() {
        return R.build(() -> PermissionConverter.toVO(permissionService.getPermissionTreeByType(PermissionType.KID.getValue())));
    }


    @Operation(summary = "获取权限（通过权限id）")
    @GetMapping(GET_PERMISSION_BY_ID)
    public R<PermissionVO> getPermissionById(@PathVariable("permissionId") @Parameter(description = "权限id", example = "1") String permissionId) {
        return R.build(() -> PermissionConverter.toVO(permissionService.getPermissionById(permissionId)));
    }

    @Operation(summary = "权限添加接口")
    @PostMapping(ADD_PERMISSION)
    public R<String> addPermission(@RequestBody @Validated PermissionForm permissionForm) {
        return R.build(() -> permissionService.addPermission(PermissionConverter.toDTO(permissionForm)));
    }

    @Operation(summary = "权限更新接口")
    @PutMapping(UPDATE_PERMISSION)
    public R<String> updatePermission(@RequestBody @Validated PermissionForm permissionForm) {
        return R.build(() -> permissionService.updatePermission(PermissionConverter.toDTO(permissionForm)));
    }

    @Operation(summary = "权限删除接口")
    @DeleteMapping(DELETE_PERMISSION)
    public R<String> deletePermission(@PathVariable("permissionId") @Parameter(description = "权限id", example = "1") String permissionId) {
        return R.build(() -> permissionService.deletePermission(permissionId));
    }

    @Operation(summary = "批量权限删除接口")
    @DeleteMapping(BATCH_DELETE_PERMISSION)
    public R<List<String>> batchDeletePermission(@Parameter(description = "权限id", example = "1") @RequestBody List<String> permissionIds) {
        return R.build(() -> permissionService.batchDeletePermission(permissionIds));
    }

}
