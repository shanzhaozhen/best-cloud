package org.shanzhaozhen.basicservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.shanzhaozhen.basiccommon.converter.ResourceConverter;
import org.shanzhaozhen.basiccommon.domain.sys.ResourceDO;
import org.shanzhaozhen.basiccommon.dto.ResourceDTO;
import org.shanzhaozhen.basiccommon.utils.CustomBeanUtils;
import org.shanzhaozhen.basicrepo.mapper.ResourceMapper;
import org.shanzhaozhen.basicservice.service.ResourceService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ResourceServiceImpl implements ResourceService {

    private final ResourceMapper resourceMapper;

    @Override
    public List<ResourceDTO> getResourceRoleListByType(Integer type) {
        return resourceMapper.getResourceRoleListByTypeAndUserId(type, null);
    }

    @Override
    public List<ResourceDTO> getAllResourceTreeByType(Integer type) {
        List<ResourceDTO> resourceDTOList = this.getResourceRoleListByType(type);
        return ResourceConverter.builtResourceTree(resourceDTOList);
    }

    @Override
    public ResourceDTO getResourceById(Long resourceId) {
        ResourceDO resourceDO = resourceMapper.selectById(resourceId);
        Assert.notNull(resourceDO, "获取失败：没有找到该资源或已被删除");
        return ResourceConverter.toDTO(resourceDO);
    }

    @Override
    @Transactional
    public Long addResource(ResourceDTO resourceDTO) {
        ResourceDO resourceDO = ResourceConverter.toDO(resourceDTO);
        resourceMapper.insert(resourceDO);
        return resourceDO.getId();
    }

    @Override
    @Transactional
    public Long updateResource(ResourceDTO resourceDTO) {
        Assert.notNull(resourceDTO.getId(), "更新失败：资源id不能为空");
        Assert.isTrue(!resourceDTO.getId().equals(resourceDTO.getPid()), "更新失败：上级节点不能选择自己");
        if (resourceDTO.getPid() != null) {
            ResourceDO resourcePNode = resourceMapper.selectById(resourceDTO.getPid());
            Assert.notNull(resourcePNode, "更新失败：没有找到该资源的上级节点或已被删除");
            Assert.isTrue(!resourceDTO.getId().equals(resourcePNode.getPid()), "更新失败：节点之间不能互相引用");
        }
        ResourceDO resourceDO = resourceMapper.selectById(resourceDTO.getId());
        Assert.notNull(resourceDO, "更新失败：没有找到该资源或已被删除");
        CustomBeanUtils.copyPropertiesExcludeMeta(resourceDTO, resourceDO);
        resourceMapper.updateById(resourceDO);
        try {
            this.getAllResourceTreeByType(null);
        } catch (StackOverflowError e) {
            throw new IllegalArgumentException("更新失败：请检查资源的节点设置是否有问题");
        }
        return resourceDO.getId();
    }

    @Override
    @Transactional
    public Long deleteResource(Long resourceId) {
        resourceMapper.deleteById(resourceId);
        return resourceId;
    }

}
