package org.shanzhaozhen.common.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "参数信息")
public class MethodParamInfo {

    @ApiModelProperty(value = "参数类型")
    private Class<?> paramType;

    @ApiModelProperty(value = "参数值")
    private String paramValue;

}
