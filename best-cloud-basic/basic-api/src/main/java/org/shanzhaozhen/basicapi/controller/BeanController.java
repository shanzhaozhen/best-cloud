package org.shanzhaozhen.basicapi.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.shanzhaozhen.basiccommon.dto.BeanInfo;
import org.shanzhaozhen.basiccommon.vo.DynamicScheduledTaskVO;
import org.shanzhaozhen.basiccommon.vo.ResultObject;
import org.shanzhaozhen.basicservice.service.BeanService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(value = "Bean接口")
@RestController
public class BeanController {

    private static final String GET_BEAN_LIST = "/bean/list";
    private static final String GET_BEAN_BY_NAME = "/bean/{beanName}";
    private static final String GET_BEAN_METHOD = "/bean/method";


    private final BeanService beanService;

    public BeanController(BeanService beanService) {
        this.beanService = beanService;
    }

    @GetMapping(GET_BEAN_LIST)
    @ApiOperation("获取Bean列表")
    public ResultObject<List<BeanInfo>> getBeanInfoList() {
        return ResultObject.build(result -> beanService.getBeanInfoList());
    }

    @GetMapping(GET_BEAN_BY_NAME)
    @ApiOperation("获取Bean列表")
    public ResultObject<BeanInfo> getBeanInfoByName(@PathVariable("beanName") String beanName) {
        return ResultObject.build(result -> beanService.getBeanInfoByName(beanName));
    }

    @PostMapping(GET_BEAN_METHOD)
    @ApiOperation("获取Bean对应的方法")
    public ResultObject<DynamicScheduledTaskVO> getBeanMethod(String beanName, String methodName) {
        return ResultObject.build(result -> null);
    }

}
