package org.shanzhaozhen.basicservice.service;

import org.shanzhaozhen.basiccommon.dto.ResourceDTO;

import java.util.List;

public interface ResourceService {

    /**
     * 通过 ResourceType 类型获取所有的Resource（多对多含有角色信息）
     * @param type
     * @return
     */
    List<ResourceDTO> getResourceRoleListByType(Integer type);

    /**
     * 获取所有资源的树形结构
     * @return
     */
    List<ResourceDTO> getAllResourceTreeByType(Integer type);

    /**
     * 通过资源id获取资源实体
     * @param resourceId
     * @return
     */
    ResourceDTO getResourceById(Long resourceId);

    /**
     * 增加资源
     * @param resourceDTO
     * @return
     */
    Long addResource(ResourceDTO resourceDTO);

    /**
     * 修改资源
     * @param resourceDTO
     * @return
     */
    Long updateResource(ResourceDTO resourceDTO);

    /**
     * 删除资源(通过资源id删除)
     * @param resourceId
     * @return
     */
    Long deleteResource(Long resourceId);

}
