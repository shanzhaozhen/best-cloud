package org.shanzhaozhen.bestcloudcommon.form;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Update;
import org.shanzhaozhen.bestcommon.domain.BaseEntity;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "动态定时任务From实体")
public class DynamicScheduledTaskForm extends BaseEntity {

    @ApiModelProperty(value = "主键ID")
    @NotNull(groups = {Update.class}, message = "任务id不能为空")
    private Long id;

    @ApiModelProperty(value = "名称")
    @NotEmpty(groups = {Insert.class, Update.class}, message = "任务名称不能为空")
    private String name;

    @ApiModelProperty(value = "cron表达式")
    @NotEmpty(groups = {Insert.class, Update.class}, message = "cron表达式不能为空")
    private String cron;

    @ApiModelProperty(value = "注册在容器的bean名称")
    private String beanName;

    @ApiModelProperty(value = "对应的方法信息")
    private String methodInfo;

    @ApiModelProperty(value = "参数")
    private String paramInfo;

    @ApiModelProperty(value = "开启状态")
    @NotNull(groups = {Insert.class, Update.class}, message = "开启状态不能为空")
    private Boolean open;

    @ApiModelProperty(value = "描述")
    private String description;

}
