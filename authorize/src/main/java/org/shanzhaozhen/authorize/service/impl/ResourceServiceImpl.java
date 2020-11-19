package org.shanzhaozhen.authorize.service.impl;

import lombok.RequiredArgsConstructor;
import org.shanzhaozhen.authorize.mapper.ResourceMapper;
import org.shanzhaozhen.authorize.service.ResourceService;
import org.shanzhaozhen.basiccommon.converter.ResourceConverter;
import org.shanzhaozhen.basiccommon.domain.sys.ResourceDO;
import org.shanzhaozhen.basiccommon.dto.ResourceDTO;
import org.shanzhaozhen.basiccommon.dto.RoleDTO;
import org.shanzhaozhen.common.enums.AuthConstants;
import org.shanzhaozhen.common.enums.ResourceType;
import org.shanzhaozhen.common.utils.CustomBeanUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.*;

@Service
@RequiredArgsConstructor
public class ResourceServiceImpl implements ResourceService {

    private final ResourceMapper resourceMapper;

    private final RedisTemplate<String, Object> redisTemplate;

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

    @Override
    public Map<String, Collection<ConfigAttribute>> initResourceInfo() {
        Map<String, Collection<ConfigAttribute>> resourceMap = new HashMap<>();

        List<ResourceDTO> resourceDTOList = this.getResourceRoleListByType(ResourceType.API.getValue());
        for (ResourceDTO resourceDTO : resourceDTOList) {
            Collection<ConfigAttribute> configAttributes = new ArrayList<>();
            // 此处只添加了用户的名字，其实还可以添加更多权限的信息，例如请求方法到ConfigAttribute的集合中去。
            // 此处添加的信息将会作为MyAccessDecisionManager类的decide的第三个参数
            List<RoleDTO> roles = resourceDTO.getRoles();
            if (roles != null && roles.size() > 0) {
                for (RoleDTO roleDTO : roles) {
                    configAttributes.add(new SecurityConfig(roleDTO.getIdentification()));
                }
                resourceMap.put(resourceDTO.getPath(), configAttributes);
            }
        }

        redisTemplate.opsForHash().putAll(AuthConstants.RESOURCE_ROLES_KEY, resourceMap);

        return resourceMap;
    }

}
