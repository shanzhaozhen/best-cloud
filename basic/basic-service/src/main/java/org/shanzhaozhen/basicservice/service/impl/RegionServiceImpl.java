package org.shanzhaozhen.basicservice.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.RequiredArgsConstructor;
import org.shanzhaozhen.basiccommon.converter.RegionConverter;
import org.shanzhaozhen.basiccommon.domain.sys.RegionDO;
import org.shanzhaozhen.basiccommon.dto.RegionDTO;
import org.shanzhaozhen.basiccommon.form.BaseSearchForm;
import org.shanzhaozhen.basiccommon.utils.CustomBeanUtils;
import org.shanzhaozhen.basicrepo.mapper.RegionMapper;
import org.shanzhaozhen.basicservice.service.RegionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RegionServiceImpl implements RegionService {

    private final RegionMapper regionMapper;

    @Override
    public Page<RegionDTO> getRegionPage(BaseSearchForm<RegionDTO> baseSearchForm) {
        return regionMapper.getRegionPage(baseSearchForm.getPage(), baseSearchForm.getKeyword());
    }

    @Override
    public List<RegionDTO> getAllRegions() {
        return regionMapper.getAllRegions();
    }

    @Override
    public List<RegionDTO> getRegionTree() {
        List<RegionDTO> allRegions = this.getAllRegions();
        return RegionConverter.builtRegionTree(allRegions);
    }

    @Override
    public RegionDTO getRegionById(Long regionId) {
        RegionDTO regionDTO = regionMapper.getRegionByRegionId(regionId);
        Assert.notNull(regionDTO, "获取失败：没有找到该区域信息或已被删除");
        return regionDTO;
    }

    @Override
    @Transactional
    public Long addRegion(RegionDTO regionDTO) {
        RegionDTO regionInDB = regionMapper.getRegionByCode(regionDTO.getCode());
        Assert.isNull(regionInDB, "创建失败：区域编号已被占用");
        RegionDO regionDO = RegionConverter.toDO(regionDTO);
        regionMapper.insert(regionDO);
        return regionDO.getId();
    }

    @Override
    @Transactional
    public Long updateRegion(RegionDTO regionDTO) {
        Assert.notNull(regionDTO.getId(), "区域信息id不能为空");
        RegionDTO regionInDB = regionMapper.getRegionByIdNotEqualAndCodeEqual(regionDTO.getId(), regionDTO.getCode());
        Assert.isNull(regionInDB, "更新失败：标识名称已被占用");
        RegionDO regionDO = regionMapper.selectById(regionDTO.getId());
        Assert.notNull(regionDO, "更新失败：没有找到该区域信息或已被删除");
        Assert.isTrue(!regionDO.getId().equals(regionDTO.getPid()), "父级区域不能选择自己");
        CustomBeanUtils.copyPropertiesExcludeMeta(regionDTO, regionDO);
        regionMapper.updateById(regionDO);
        return regionDO.getId();
    }

    @Override
    @Transactional
    public Long deleteRegion(Long regionId) {
        regionMapper.deleteById(regionId);
        return regionId;
    }
}
