package org.shanzhaozhen.basicapi.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Update;
import org.shanzhaozhen.basiccommon.converter.RegionConverter;
import org.shanzhaozhen.basiccommon.dto.RegionDTO;
import org.shanzhaozhen.basiccommon.form.BaseSearchForm;
import org.shanzhaozhen.basiccommon.form.RegionForm;
import org.shanzhaozhen.basiccommon.vo.RegionVO;
import org.shanzhaozhen.basiccommon.vo.ResultObject;
import org.shanzhaozhen.basicservice.service.RegionService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api("区域信息接口")
@RestController
@RequiredArgsConstructor
public class RegionController {

    private static final String GET_REGION_PAGE = "/region/page";
    private static final String GET_REGION_ALL = "/region/all";
    private static final String GET_REGION_TREE = "/region/tree";
    private static final String GET_REGION_BY_ID = "/region/{regionId}";
    private static final String ADD_REGION = "/region";
    private static final String UPDATE_REGION = "/region";
    private static final String DELETE_REGION = "/region/{regionId}";

    private final RegionService regionService;

    @PostMapping(GET_REGION_PAGE)
    @ApiOperation("获取区域信息信息（分页）")
    public ResultObject<Page<RegionVO>> getRegionPage(@RequestBody BaseSearchForm<RegionDTO> baseSearchForm) {
        return ResultObject.build(result -> RegionConverter.toVO(regionService.getRegionPage(baseSearchForm)));
    }

    @GetMapping(GET_REGION_ALL)
    @ApiOperation("获取所有区域信息")
    public ResultObject<List<RegionVO>> getAllRegions() {
        return ResultObject.build(result -> RegionConverter.toVO(regionService.getAllRegions()));
    }

    @GetMapping(GET_REGION_TREE)
    @ApiOperation("获取所有区域信息")
    public ResultObject<List<RegionVO>> getRegionTree() {
        return ResultObject.build(result -> RegionConverter.toVO(regionService.getRegionTree()));
    }

    @GetMapping(GET_REGION_BY_ID)
    @ApiOperation("获取区域信息信息（通过区域信息id）")
    public ResultObject<RegionVO> getRegionByRegionId(@PathVariable("regionId") @ApiParam(name = "区域信息id", example = "1") Long regionId) {
        return ResultObject.build(result -> RegionConverter.toVO(regionService.getRegionById(regionId)));
    }

    @PostMapping(ADD_REGION)
    @ApiOperation("添加区域信息接口")
    public ResultObject<Long> addRegion(@RequestBody @Validated({Insert.class}) RegionForm regionForm) {
        return ResultObject.build(result -> regionService.addRegion(RegionConverter.toDTO(regionForm)));
    }

    @PutMapping(UPDATE_REGION)
    @ApiOperation("更新区域信息接口")
    public ResultObject<Long> updateRegion(@RequestBody @Validated({Update.class}) RegionForm regionForm) {
        return ResultObject.build(result -> regionService.updateRegion(RegionConverter.toDTO(regionForm)));
    }

    @DeleteMapping(DELETE_REGION)
    @ApiOperation("删除区域信息接口")
    public ResultObject<Long> deleteRegion(@PathVariable("regionId") @ApiParam(name = "区域信息id", example = "1") Long regionId) {
        return ResultObject.build(result -> regionService.deleteRegion(regionId));
    }

}
