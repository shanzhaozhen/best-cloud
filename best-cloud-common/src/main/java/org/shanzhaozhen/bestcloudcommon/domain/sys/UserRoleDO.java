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
@TableName("sys_user_role")
@ApiModel(description = "用户-角色关系DO实体")
public class UserRoleDO extends BaseEntity {

    private static final long serialVersionUID = -8389872342618587940L;

    @ApiModelProperty(value = "主键ID")
    @TableId(type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "用户ID")
    private Long userId;

    @ApiModelProperty(value = "角色ID")
    private Long roleId;

}
