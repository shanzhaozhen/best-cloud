package org.shanzhaozhen.bestcloudcommon.domain.sys;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.shanzhaozhen.bestcommon.domain.BaseEntity;


@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("sys_resource")
@ApiModel(description = "资源DO实体")
public class ResourceDO extends BaseEntity {

    private static final long serialVersionUID = 4485640590947953262L;

    @ApiModelProperty(value = "主键ID")
    @TableId(type = IdType.AUTO)
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

//    @ApiModelProperty(value = "支持Get请求")
//    private Boolean supportGet;
//
//    @ApiModelProperty(value = "支持Post请求")
//    private Boolean supportPost;
//
//    @ApiModelProperty(value = "支持Put请求")
//    private Boolean supportPut;
//
//    @ApiModelProperty(value = "支持Delete请求")
//    private Boolean supportDelete;
//
//    @ApiModelProperty(value = "支持Patch请求")
//    private Boolean supportPatch;

    @ApiModelProperty(value = "资源描述")
    private String description;

}
