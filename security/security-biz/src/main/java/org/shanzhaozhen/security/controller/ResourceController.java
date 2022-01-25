package org.shanzhaozhen.security.controller;

import org.shanzhaozhen.common.enums.ResourceType;
import org.shanzhaozhen.common.result.R;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.shanzhaozhen.security.converter.ResourceConverter;
import org.shanzhaozhen.security.form.ResourceForm;
import org.shanzhaozhen.security.service.ResourceService;
import org.shanzhaozhen.security.vo.ResourceVO;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "resource", description = "后台资源接口")
@RestController
@RequiredArgsConstructor
public class ResourceController {

    private static final String GET_ALL_RESOURCE_TREE = "/resource/tree";
    private static final String GET_ALL_RESOURCE_ROOT_TREE = "/resource/root-tree";
    private static final String GET_RESOURCE_BY_ID = "/resource/{resourceId}";
    private static final String ADD_RESOURCE = "/resource";
    private static final String UPDATE_RESOURCE = "/resource";
    private static final String DELETE_RESOURCE = "/resource/{resourceId}";
    private static final String BATCH_DELETE_RESOURCE = "/resource";

    private final ResourceService resourceService;

    @Operation(summary = "获取所有资源（树状结构）")
    @GetMapping(GET_ALL_RESOURCE_TREE)
    public R<List<ResourceVO>> getResourceTree() {
        return R.build(() -> ResourceConverter.toVO(resourceService.getResourceTreeByType(null)));
    }

    @Operation(summary = "获取所有父节点资源（树状结构）")
    @GetMapping(GET_ALL_RESOURCE_ROOT_TREE)
    public R<List<ResourceVO>> getAllResourceRootTree() {
        return R.build(() -> ResourceConverter.toVO(resourceService.getResourceTreeByType(ResourceType.KID.getValue())));
    }


    @Operation(summary = "获取资源（通过资源id）")
    @GetMapping(GET_RESOURCE_BY_ID)
    public R<ResourceVO> getResourceById(@PathVariable("resourceId") @Parameter(description = "资源id", example = "1") Long resourceId) {
        return R.build(() -> ResourceConverter.toVO(resourceService.getResourceById(resourceId)));
    }

    @Operation(summary = "资源添加接口")
    @PostMapping(ADD_RESOURCE)
    public R<Long> addResource(@RequestBody @Validated ResourceForm resourceForm) {
        return R.build(() -> resourceService.addResource(ResourceConverter.toDTO(resourceForm)));
    }

    @Operation(summary = "资源更新接口")
    @PutMapping(UPDATE_RESOURCE)
    public R<Long> updateResource(@RequestBody @Validated ResourceForm resourceForm) {
        return R.build(() -> resourceService.updateResource(ResourceConverter.toDTO(resourceForm)));
    }

    @Operation(summary = "资源删除接口")
    @DeleteMapping(DELETE_RESOURCE)
    public R<Long> deleteResource(@PathVariable("resourceId") @Parameter(description = "资源id", example = "1") Long resourceId) {
        return R.build(() -> resourceService.deleteResource(resourceId));
    }

    @Operation(summary = "批量资源删除接口")
    @DeleteMapping(BATCH_DELETE_RESOURCE)
    public R<List<Long>> batchDeleteResource(@Parameter(description = "资源id", example = "1") @RequestBody List<Long> resourceIds) {
        return R.build(() -> resourceService.batchDeleteResource(resourceIds));
    }

}
