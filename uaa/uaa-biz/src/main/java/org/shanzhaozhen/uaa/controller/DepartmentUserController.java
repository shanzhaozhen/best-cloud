package org.shanzhaozhen.uaa.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "department-user", description = "部门用户接口")
@RestController
@RequiredArgsConstructor
public class DepartmentUserController {

}
