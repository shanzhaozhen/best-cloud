package org.shanzhaozhen.bestcloudcommon.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.shanzhaozhen.bestcommon.domain.BaseEntity;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "资源VO实体")
public class ResourceVO extends BaseEntity {

    @ApiModelProperty(value = "主键ID")
    private Long id;

    @ApiModelProperty(value = "资源名称")
    private String name;

    @ApiModelProperty(value = "资源路由")
    private String path;

    @ApiModelProperty(value = "资源类型")
    private Integer type;

    @ApiModelProperty(value = "上级ID")
    private Long pid;

    @ApiModelProperty(value = "排序等级")
    private Integer priority;

    @ApiModelProperty(value = "支持Get请求")
    private Boolean supportGet;

    @ApiModelProperty(value = "支持Post请求")
    private Boolean supportPost;

    @ApiModelProperty(value = "支持Put请求")
    private Boolean supportPut;

    @ApiModelProperty(value = "支持Delete请求")
    private Boolean supportDelete;

    @ApiModelProperty(value = "支持Patch请求")
    private Boolean supportPatch;

    @ApiModelProperty(value = "资源描述")
    private String description;

    private List<RoleVO> roles;

    private List<ResourceVO> children;
}
