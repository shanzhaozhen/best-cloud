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
@TableName("sys_role")
@ApiModel(description = "角色DO实体")
public class RoleDO extends BaseEntity {

    private static final long serialVersionUID = 6203528166202612882L;

    @ApiModelProperty(value = "主键ID")
    @TableId(type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "名称")
    private String name;

    @ApiModelProperty(value = "标识名称")
    private String identification;

    @ApiModelProperty(value = "描述")
    private String description;

}
