package org.shanzhaozhen.basicapi.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.shanzhaozhen.basiccommon.converter.ResourceConverter;
import org.shanzhaozhen.basiccommon.enums.sys.ResourceType;
import org.shanzhaozhen.basiccommon.form.ResourceForm;
import org.shanzhaozhen.basiccommon.vo.ResourceVO;
import org.shanzhaozhen.basiccommon.vo.ResultObject;
import org.shanzhaozhen.basicservice.service.ResourceService;
import org.shanzhaozhen.common.enums.ResourceType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api("后台资源接口")
@RestController
@RequiredArgsConstructor
public class ResourceController {

    private static final String GET_ALL_RESOURCE_TREE = "/resource/tree";
    private static final String GET_ALL_RESOURCE_ROOT_TREE = "/resource/root-tree";
    private static final String GET_RESOURCE_BY_ID = "/resource/{resourceId}";
    private static final String ADD_RESOURCE = "/resource";
    private static final String UPDATE_RESOURCE = "/resource";
    private static final String DELETE_RESOURCE = "/resource/{resourceId}";

    private final ResourceService resourceService;

    @GetMapping(GET_ALL_RESOURCE_TREE)
    @ApiOperation("获取所有资源（树状结构）")
    public ResultObject<List<ResourceVO>> getAllResourceTree() {
        return ResultObject.build(result -> ResourceConverter.toVO(resourceService.getAllResourceTreeByType(null)));
    }

    @GetMapping(GET_ALL_RESOURCE_ROOT_TREE)
    @ApiOperation("获取所有根部资源（树状结构）")
    public ResultObject<List<ResourceVO>> getAllResourceRootTree() {
        return ResultObject.build(result -> ResourceConverter.toVO(resourceService.getAllResourceTreeByType(ResourceType.KID.getValue())));
    }


    @GetMapping(GET_RESOURCE_BY_ID)
    @ApiOperation("获取资源（通过资源id）")
    public ResultObject<ResourceVO> getResourceByResourceId(@PathVariable("resourceId") @ApiParam(name = "资源id", example = "1") Long resourceId) {
        return ResultObject.build(result -> ResourceConverter.toVO(resourceService.getResourceById(resourceId)));
    }

    @PostMapping(ADD_RESOURCE)
    @ApiOperation("资源添加接口")
    public ResultObject<Long> addResource(@RequestBody @Validated ResourceForm resourceForm) {
        return ResultObject.build(result -> resourceService.addResource(ResourceConverter.toDTO(resourceForm)));
    }

    @PutMapping(UPDATE_RESOURCE)
    @ApiOperation("资源更新接口")
    public ResultObject<Long> updateResource(@RequestBody @Validated ResourceForm resourceForm) {
        return ResultObject.build(result -> resourceService.updateResource(ResourceConverter.toDTO(resourceForm)));
    }

    @DeleteMapping(DELETE_RESOURCE)
    @ApiOperation("资源删除接口")
    public ResultObject<Long> deleteResource(@PathVariable("resourceId") @ApiParam(name = "资源id", example = "1") Long resourceId) {
        return ResultObject.build(result -> resourceService.deleteResource(resourceId));
    }

}
