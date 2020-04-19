package org.shanzhaozhen.bestcloudcommon.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "前端菜单实体（用于前端菜单的存放）")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AsyncRoute {

    @ApiModelProperty(value = "主键ID")
    private Long id;

    @ApiModelProperty(value = "菜单名称")
    private String name;

    @ApiModelProperty(value = "菜单路由")
    private String path;

    @ApiModelProperty(value = "上级ID")
    private Long pid;

    @ApiModelProperty(value = "重定向路径")
    private String redirect;

    @ApiModelProperty(value = "排序等级")
    private Integer priority;

    @ApiModelProperty(value = "菜单是否隐藏")
    private Boolean hidden;

    @ApiModelProperty(value = "菜单是否总是显示")
    private Boolean alwaysShow;

    @ApiModelProperty(value = "菜单是否总是显示")
    private Meta meta;

    @ApiModelProperty(value = "参数")
    private String props;

    @ApiModelProperty(value = "菜单描述")
    private String description;

    @ApiModelProperty(value = "下级菜单")
    private List<AsyncRoute> children;

}
