package org.shanzhaozhen.uaa.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.shanzhaozhen.common.core.entity.BasePageParams;
import org.shanzhaozhen.common.core.result.R;
import org.shanzhaozhen.uaa.converter.DepartmentConverter;
import org.shanzhaozhen.uaa.pojo.dto.DepartmentDTO;
import org.shanzhaozhen.uaa.pojo.form.DepartmentForm;
import org.shanzhaozhen.uaa.pojo.vo.DepartmentVO;
import org.shanzhaozhen.uaa.service.DepartmentService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "department", description = "部门接口")
@RestController
@RequiredArgsConstructor
public class DepartmentController {

    private static final String GET_DEPARTMENT_PAGE = "/department/page";
    private static final String GET_DEPARTMENT_list = "/department/list";
    private static final String GET_ALL_DEPARTMENT_TREE = "/department/tree";
    private static final String GET_DEPARTMENT_BY_PID = "/department/pid";
    private static final String GET_DEPARTMENT_BY_ID = "/department/{departmentId}";
    private static final String ADD_DEPARTMENT = "/department";
    private static final String UPDATE_DEPARTMENT = "/department";
    private static final String DELETE_DEPARTMENT = "/department/{departmentId}";
    private static final String BATCH_DELETE_DEPARTMENT = "/department";

    private final DepartmentService departmentService;

    @Operation(summary = "获取角色信息（分页）")
    @PostMapping(GET_DEPARTMENT_PAGE)
    public R<Page<DepartmentVO>> getDepartmentPage(@RequestBody BasePageParams<DepartmentDTO> pageParams) {
        return R.build(() -> DepartmentConverter.toVO(departmentService.getDepartmentPage(pageParams.getPage(), pageParams.getKeyword())));
    } 
    
    @Operation(summary = "获取部门列表")
    @GetMapping(GET_DEPARTMENT_list)
    public R<List<DepartmentVO>> getDepartmentList(@Parameter(description = "关键字") @RequestParam(value = "keyword", required = false) String keyword) {
        return R.build(() -> DepartmentConverter.toVO(departmentService.getDepartmentList(keyword)));
    }

    @Operation(summary = "获取所有部门信息（树状结构）")
    @GetMapping(GET_ALL_DEPARTMENT_TREE)
    public R<List<DepartmentVO>> getDepartmentTree() {
        return R.build(() -> DepartmentConverter.toVO(departmentService.getDepartmentTree()));
    }

    @Operation(summary = "通过父级ID获取部门列表")
    @GetMapping(GET_DEPARTMENT_BY_PID)
    public R<List<DepartmentVO>> getDepartmentByPId(@Parameter(description = "父级id", example = "1") Long pid) {
        return R.build(() -> DepartmentConverter.toVO(departmentService.getDepartmentByPId(pid)));
    }

    @Operation(summary = "获取部门信息（通过部门id）")
    @GetMapping(GET_DEPARTMENT_BY_ID)
    public R<DepartmentVO> getDepartmentById(@PathVariable("departmentId") @Parameter(description = "部门id", example = "1") Long departmentId) {
        return R.build(() -> DepartmentConverter.toVO(departmentService.getDepartmentById(departmentId)));
    }

    @Operation(summary = "添加部门接口")
    @PostMapping(ADD_DEPARTMENT)
    public R<Long> addDepartment(@RequestBody @Validated DepartmentForm departmentForm) {
        return R.build(() -> departmentService.addDepartment(DepartmentConverter.toDTO(departmentForm)));
    }

    @Operation(summary = "更新部门接口")
    @PutMapping(UPDATE_DEPARTMENT)
    public R<Long> updateDepartment(@RequestBody @Validated DepartmentForm departmentForm) {
        return R.build(() -> departmentService.updateDepartment(DepartmentConverter.toDTO(departmentForm)));
    }

    @Operation(summary = "删除部门接口")
    @DeleteMapping(DELETE_DEPARTMENT)
    public R<Long> deleteDepartment(@PathVariable("departmentId") @Parameter(description = "部门id", example = "1") Long departmentId) {
        return R.build(() -> departmentService.deleteDepartment(departmentId));
    }

    @Operation(summary = "批量删除部门接口")
    @DeleteMapping(BATCH_DELETE_DEPARTMENT)
    public R<List<Long>> batchDeleteDepartment(@Parameter(description = "部门id", example = "[1, 2]") @RequestBody List<Long> departmentIds) {
        return R.build(() -> departmentService.batchDeleteDepartment(departmentIds));
    }

}
