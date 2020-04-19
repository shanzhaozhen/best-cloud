package org.shanzhaozhen.bestcloudcommon.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "路由VO实体")
public class RouteVO {

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

    @ApiModelProperty(value = "显示名称")
    private String title;

    @ApiModelProperty(value = "图标")
    private String icon;

    @ApiModelProperty(value = "排序等级")
    private Integer priority;

    @ApiModelProperty(value = "菜单是否隐藏")
    private Boolean hidden;

    @ApiModelProperty(value = "菜单是否总是显示")
    private Boolean alwaysShow;

    @ApiModelProperty(value = "是否需要缓存")
    private Boolean noCache;

    @ApiModelProperty(value = "固钉")
    private Boolean affix;

    @ApiModelProperty(value = "面包屑")
    private Boolean breadcrumb;

    @ApiModelProperty(value = "参数")
    private String props;

    @ApiModelProperty(value = "菜单描述")
    private String description;

    private List<RoleVO> roleVOList;

    private List<RouteVO> children;

}
