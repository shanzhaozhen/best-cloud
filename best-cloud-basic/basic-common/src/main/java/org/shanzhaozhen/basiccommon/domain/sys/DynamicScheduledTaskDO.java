package org.shanzhaozhen.basiccommon.domain.sys;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.shanzhaozhen.basiccommon.domain.BaseEntity;

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("sys_dynamic_scheduled_task")
@ApiModel(description = "动态定时任务DO实体")
public class DynamicScheduledTaskDO extends BaseEntity {

    private static final long serialVersionUID = 216445339652015543L;

    @ApiModelProperty(value = "主键ID")
    @TableId(type = IdType.AUTO)
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
