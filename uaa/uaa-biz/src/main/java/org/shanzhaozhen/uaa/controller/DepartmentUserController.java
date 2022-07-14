package org.shanzhaozhen.uaa.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.shanzhaozhen.common.core.result.R;
import org.shanzhaozhen.uaa.pojo.form.DepartmentUserForm;
import org.shanzhaozhen.uaa.service.DepartmentUserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "department-user", description = "部门用户接口")
@RestController
@RequiredArgsConstructor
public class DepartmentUserController {

    private static final String ADD_DEPARTMENT_USER = "/department-user";
    private static final String DELETE_DEPARTMENT_USER = "/department-user";

    private final DepartmentUserService departmentUserService;

    @Operation(summary = "添加用户角色")
    @PostMapping(ADD_DEPARTMENT_USER)
    public R<List<String>> addDepartmentUsers(@RequestBody DepartmentUserForm departmentUserForm) {
        return R.build(() -> departmentUserService.batchAddDepartmentUser(departmentUserForm.getDepartmentId(), departmentUserForm.getUserIds()));
    }

    @Operation(summary = "删除用户角色")
    @DeleteMapping(DELETE_DEPARTMENT_USER)
    public R<Integer> deleteDepartmentUsers(@RequestBody DepartmentUserForm departmentUserForm) {
        return R.build(() -> departmentUserService.batchDeleteDepartmentUser(departmentUserForm.getDepartmentId(), departmentUserForm.getUserIds()));
    }

}
