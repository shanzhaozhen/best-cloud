package org.shanzhaozhen.uaa.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.shanzhaozhen.common.core.entity.BasePageParams;
import org.shanzhaozhen.common.core.result.R;
import org.shanzhaozhen.uaa.pojo.dto.DepartmentDTO;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "login", description = "登陆接口")
@RestController
@RequiredArgsConstructor
public class AuthController {

    private static final String GET_LOGIN_PHONE_CODE = "/auth/phone";

    @Operation(summary = "获取角色信息（分页）")
    @PostMapping(GET_LOGIN_PHONE_CODE)
    public R<String> getPhoneCode(@RequestBody BasePageParams<DepartmentDTO> pageParams) {
        return R.build(() -> "fdf");
    }

}
