package org.shanzhaozhen.common.core.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "方法信息")
public class MethodInfo {

    @ApiModelProperty(value = "方法名")
    private String methodName;

    @ApiModelProperty(value = "方法名（简易）")
    private String methodSimpleName;

    @ApiModelProperty(value = "方法名（完全）")
    private String methodFullName;

    @ApiModelProperty(value = "参数类型")
    private Class<?>[] paramTypes;

}
