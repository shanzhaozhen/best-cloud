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
     * 获取部门列表
     * @return
     */
    List<DepartmentDTO> getDepartmentList(String keyword);

    /**
     * 通过父级ID获取部门列表
     * @return
     */
    List<DepartmentDTO> getDepartmentByPid(@Nullable String pid);

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
    DepartmentDTO getDepartmentById(String departmentId);

    /**
     * 通过部门编码获取部门实体
     * @param code
     * @return
     */
    DepartmentDTO getDepartmentByCode(String code);

    /**
     * 增加部门
     * @param departmentDTO
     * @return
     */
    String addDepartment(DepartmentDTO departmentDTO);

    /**
     * 修改部门
     * @param departmentDTO
     * @return
     */
    String updateDepartment(DepartmentDTO departmentDTO);

    /**
     * 删除部门(通过部门id删除)
     * @param departmentId
     * @return
     */
    String deleteDepartment(String departmentId);

    /**
     * 批量删除部门
     * @param departmentIds
     * @return
     */
    List<String> batchDeleteDepartment(List<String> departmentIds);

}
