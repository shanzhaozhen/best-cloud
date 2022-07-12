package org.shanzhaozhen.uaa.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.shanzhaozhen.uaa.pojo.dto.DepartmentDTO;
import org.shanzhaozhen.uaa.pojo.entity.DepartmentDO;

import java.util.List;

public interface DepartmentMapper extends BaseMapper<DepartmentDO> {

    Page<DepartmentDTO> getDepartmentPage(Page<DepartmentDTO> page, String keyword);

    List<DepartmentDTO> getAllDepartments();

    List<DepartmentDTO> getDepartmentByPId(Long pid);
}
