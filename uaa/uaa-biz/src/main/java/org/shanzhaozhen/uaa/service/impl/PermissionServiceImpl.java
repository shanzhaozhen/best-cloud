package org.shanzhaozhen.uaa.service.impl;


import org.shanzhaozhen.common.utils.CustomBeanUtils;
import org.shanzhaozhen.common.utils.TreeUtils;
import org.shanzhaozhen.uaa.converter.PermissionConverter;
import org.shanzhaozhen.uaa.domain.PermissionDO;
import org.shanzhaozhen.uaa.dto.PermissionDTO;
import org.shanzhaozhen.uaa.service.PermissionService;
import org.shanzhaozhen.uaa.mapper.PermissionMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.validation.constraints.NotEmpty;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PermissionServiceImpl implements PermissionService {

    private final PermissionMapper permissionMapper;

    @Override
    public List<PermissionDTO> getPermissionRoleListByType(Integer type) {
        return permissionMapper.getPermissionRoleListByTypeAndUserId(type, null);
    }

    @Override
    public List<PermissionDTO> getPermissionTreeByType(Integer type) {
        List<PermissionDTO> permissionDTOList = this.getPermissionRoleListByType(type);
        return TreeUtils.builtTree(permissionDTOList, PermissionDTO.class);
    }

    @Override
    public PermissionDTO getPermissionById(Long permissionId) {
        PermissionDO permissionDO = permissionMapper.selectById(permissionId);
        Assert.notNull(permissionDO, "获取失败：没有找到该权限或已被删除");
        return PermissionConverter.toDTO(permissionDO);
    }

    @Override
    @Transactional
    public Long addPermission(PermissionDTO permissionDTO) {
        PermissionDO permissionDO = PermissionConverter.toDO(permissionDTO);
        permissionMapper.insert(permissionDO);
        return permissionDO.getId();
    }

    @Override
    @Transactional
    public Long updatePermission(PermissionDTO permissionDTO) {
        Assert.notNull(permissionDTO.getId(), "更新失败：权限id不能为空");
        Assert.isTrue(!permissionDTO.getId().equals(permissionDTO.getPid()), "更新失败：上级节点不能选择自己");
        if (permissionDTO.getPid() != null) {
            PermissionDO permissionPNode = permissionMapper.selectById(permissionDTO.getPid());
            Assert.notNull(permissionPNode, "更新失败：没有找到该权限的上级节点或已被删除");
            Assert.isTrue(!permissionDTO.getId().equals(permissionPNode.getPid()), "更新失败：节点之间不能互相引用");
        }
        PermissionDO permissionDO = permissionMapper.selectById(permissionDTO.getId());
        Assert.notNull(permissionDO, "更新失败：没有找到该权限或已被删除");
        CustomBeanUtils.copyPropertiesExcludeMeta(permissionDTO, permissionDO);
        permissionMapper.updateById(permissionDO);
        try {
            this.getPermissionTreeByType(null);
        } catch (StackOverflowError e) {
            throw new IllegalArgumentException("更新失败：请检查权限的节点设置是否有问题");
        }
        return permissionDO.getId();
    }

    @Override
    @Transactional
    public Long deletePermission(Long permissionId) {
        permissionMapper.deleteById(permissionId);
        return permissionId;
    }

    @Override
    @Transactional
    public List<Long> batchDeletePermission(@NotEmpty(message = "没有需要删除的权限") List<Long> permissionIds) {
        for (Long permissionId : permissionIds) {
            this.deletePermission(permissionId);
        }
        return permissionIds;
    }

}
