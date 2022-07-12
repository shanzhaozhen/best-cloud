package org.shanzhaozhen.uaa.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.shanzhaozhen.uaa.pojo.dto.DepartmentDTO;

import javax.annotation.Nullable;
import java.util.List;

public interface DepartmentService {

    /**
     * 获取部门列表
     * @param page
     * @param keyword
     * @return
     */
    Page<DepartmentDTO> getDepartmentPage(Page<DepartmentDTO> page, String keyword);

    /**
     * 获取所有部门的树形结构
     * @return
     */
    List<DepartmentDTO> getAllDepartments();

    /**
     * 通过父级ID获取部门列表
     * @return
     */
    List<DepartmentDTO> getDepartmentByPId(@Nullable Long pid);

    /**
     * 获取所有部门的树形结构
     * @return
     */
    List<DepartmentDTO> getDepartmentTree();

    /**
     * 通过部门id获取部门实体
     * @param departmentId
     * @return
     */
    DepartmentDTO getDepartmentById(Long departmentId);

    /**
     * 增加部门
     * @param departmentDTO
     * @return
     */
    Long addDepartment(DepartmentDTO departmentDTO);

    /**
     * 修改部门
     * @param departmentDTO
     * @return
     */
    Long updateDepartment(DepartmentDTO departmentDTO);

    /**
     * 删除部门(通过部门id删除)
     * @param departmentId
     * @return
     */
    Long deleteDepartment(Long departmentId);

    /**
     * 批量删除部门
     * @param departmentIds
     * @return
     */
    List<Long> batchDeleteDepartment(List<Long> departmentIds);

}
