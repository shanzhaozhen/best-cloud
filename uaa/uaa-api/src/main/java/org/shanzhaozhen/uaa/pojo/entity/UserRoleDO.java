package org.shanzhaozhen.uaa.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.shanzhaozhen.common.core.entity.BaseEntity;

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("sys_user_role")
@Schema(description = "用户-角色关系DO实体")
public class UserRoleDO extends BaseEntity {

    private static final long serialVersionUID = -8389872342618587940L;

    @Schema(description = "主键ID")
    @TableId(type = IdType.ASSIGN_ID)
    private String id;

    @Schema(description = "用户ID")
    private String userId;

    @Schema(description = "角色ID")
    private String roleId;
    
}
