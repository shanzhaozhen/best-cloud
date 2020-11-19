package org.shanzhaozhen.basiccommon.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.shanzhaozhen.common.entity.MethodInfo;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "Bean信息")
public class BeanInfo {

    @ApiModelProperty(value = "bean名称")
    private String beanName;

    @ApiModelProperty(value = "bean对应的Class名")
    private String className;

    @ApiModelProperty(value = "bean对应的所含有的方法")
    private List<MethodInfo> methods;

}
