package org.shanzhaozhen.uaa.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.RequiredArgsConstructor;
import org.shanzhaozhen.common.core.utils.CustomBeanUtils;
import org.shanzhaozhen.common.core.utils.TreeUtils;
import org.shanzhaozhen.uaa.converter.DepartmentConverter;
import org.shanzhaozhen.uaa.converter.RoleConverter;
import org.shanzhaozhen.uaa.mapper.DepartmentMapper;
import org.shanzhaozhen.uaa.pojo.dto.DepartmentDTO;
import org.shanzhaozhen.uaa.pojo.dto.DepartmentDTO;
import org.shanzhaozhen.uaa.pojo.dto.RoleDTO;
import org.shanzhaozhen.uaa.pojo.entity.DepartmentDO;
import org.shanzhaozhen.uaa.pojo.entity.RoleDO;
import org.shanzhaozhen.uaa.service.DepartmentService;
import org.shanzhaozhen.uaa.service.DepartmentUserService;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.annotation.Nullable;
import javax.validation.constraints.NotEmpty;
import java.util.List;


@Service
@RequiredArgsConstructor
@CacheConfig(cacheNames = "department")
public class DepartmentServiceImpl implements DepartmentService {

    private final DepartmentMapper departmentMapper;
    private final DepartmentUserService departmentUserService;

    @Override
    public Page<DepartmentDTO> getDepartmentPage(Page<DepartmentDTO> page, String keyword) {
        return departmentMapper.getDepartmentPage(page, keyword);
    }

    @Override
    @Cacheable(key = "#root.methodName")
    public List<DepartmentDTO> getDepartmentList(String keyword) {
        return departmentMapper.getDepartmentList(keyword);
    }

    @Override
    public List<DepartmentDTO> getDepartmentByPid(@Nullable String pid) {
        return departmentMapper.getDepartmentByPid(pid);
    }

    @Override
    @Cacheable(key = "#root.methodName")
    public List<DepartmentDTO> getDepartmentTree() {
        List<DepartmentDTO> departmentDTOList = this.getDepartmentList(null);
        return TreeUtils.builtTree(departmentDTOList, DepartmentDTO.class);
    }

    @Override
    @Cacheable(key = "#root.methodName + ':' + #departmentId")
    public DepartmentDTO getDepartmentById(String departmentId) {
        DepartmentDO departmentDO = departmentMapper.selectById(departmentId);
        Assert.notNull(departmentDO, "获取失败：没有找到该菜单或已被删除");
        return DepartmentConverter.toDTO(departmentDO);
    }

    @Override
    public DepartmentDTO getDepartmentByCode(String code) {
        return departmentMapper.getDepartmentByCode(code);
    }

    @Override
    @Transactional
    @CacheEvict(allEntries = true)
    public String addDepartment(DepartmentDTO departmentDTO) {
        DepartmentDTO departmentInDB = this.getDepartmentByCode(departmentDTO.getCode());
        Assert.isNull(departmentInDB, "创建失败：部门编码已被占用");
        DepartmentDO departmentDO = DepartmentConverter.toDO(departmentDTO);
        departmentMapper.insert(departmentDO);
        return departmentDO.getId();
    }

    @Override
    @Transactional
    @CacheEvict(allEntries = true)
    public String updateDepartment(DepartmentDTO departmentDTO) {
        Assert.notNull(departmentDTO.getId(), "更新失败：菜单id不能为空");
        Assert.isTrue(!departmentDTO.getId().equals(departmentDTO.getPid()), "更新失败：上级菜单不能选择自己");
        if (departmentDTO.getPid() != null) {
            DepartmentDO departmentPNode = departmentMapper.selectById(departmentDTO.getPid());
            Assert.notNull(departmentPNode, "更新失败：没有找到该菜单的上级菜单或已被删除");
            Assert.isTrue(!departmentDTO.getId().equals(departmentPNode.getPid()), "更新失败：菜单之间不能互相引用");
        }
        DepartmentDTO departmentInDB = departmentMapper.getDepartmentByCode(departmentDTO.getCode());
        Assert.isTrue(departmentInDB == null || departmentInDB.getId().equals(departmentDTO.getId()), "更新失败：部门编码已被占用");
        DepartmentDO departmentDO = departmentMapper.selectById(departmentDTO.getId());
        Assert.notNull(departmentDO, "更新失败：没有找到该菜单或已被删除");
        CustomBeanUtils.copyPropertiesExcludeMeta(departmentDTO, departmentDO);
        departmentMapper.updateById(departmentDO);
        try {
            this.getDepartmentTree();
        } catch (StackOverflowError e) {
            throw new IllegalArgumentException("更新失败：请检查菜单节点间设置是否有问题");
        }
        return departmentDO.getId();
    }

    @Override
    @Transactional
    @CacheEvict(allEntries = true)
    public String deleteDepartment(String departmentId) {
        departmentMapper.deleteById(departmentId);
        // 删除用户的关联关系
        departmentUserService.deleteDepartmentUserByDepartmentId(departmentId);
        return departmentId;
    }

    @Override
    @Transactional
    @CacheEvict(allEntries = true)
    public List<String> batchDeleteDepartment(@NotEmpty(message = "没有需要删除的菜单") List<String> departmentIds) {
        for (String departmentId : departmentIds) {
            this.deleteDepartment(departmentId);
        }
        return departmentIds;
    }
    
}
