package org.shanzhaozhen.uaa.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.shanzhaozhen.uaa.pojo.dto.DepartmentDTO;
import org.shanzhaozhen.uaa.pojo.entity.DepartmentDO;

import java.util.List;

public interface DepartmentMapper extends BaseMapper<DepartmentDO> {

    Page<DepartmentDTO> getDepartmentPage(Page<DepartmentDTO> page, @Param("keyword") String keyword);

    List<DepartmentDTO> getDepartmentList(@Param("keyword") String keyword);

    List<DepartmentDTO> getDepartmentByPid(@Param("pid") Long pid);

    List<DepartmentDTO> getDepartmentByUserId(@Param("userId") Long userId);

    DepartmentDTO getDepartmentByCode(@Param("code") String code);

}
