package org.shanzhaozhen.bestcloudcommon.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "动态定时任务VO实体")
public class DynamicScheduledTaskVO {

    @ApiModelProperty(value = "主键ID")
    private Long id;

    @ApiModelProperty(value = "名称")
    private String name;

    @ApiModelProperty(value = "cron表达式")
    private String cron;

    @ApiModelProperty(value = "注册在容器的bean名称")
    private String beanName;

    @ApiModelProperty(value = "对应的方法信息")
    private String methodInfo;

    @ApiModelProperty(value = "参数")
    private String paramInfo;

    @ApiModelProperty(value = "开启状态")
    private Boolean open;

    @ApiModelProperty(value = "描述")
    private String description;

}
