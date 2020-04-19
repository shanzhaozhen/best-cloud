package org.shanzhaozhen.bestcloudcommon.form;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "部门Form实体")
public class DepartmentForm {

    @ApiModelProperty(value = "主键ID")
    private Long id;

    @ApiModelProperty(value = "部门名称")
    private String name;

    @ApiModelProperty(value = "上级ID")
    private Long pid;

}
