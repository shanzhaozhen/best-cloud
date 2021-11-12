package org.shanzhaozhen.basiccommon.domain.sys;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.shanzhaozhen.basiccommon.domain.BaseInfo;

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("sys_user_role")
@Schema(description = "用户-角色关系DO实体")
public class UserRoleDO extends BaseInfo {

    private static final long serialVersionUID = -8389872342618587940L;

    @Schema(title = "主键ID")
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    @Schema(title = "用户ID")
    private Long userId;

    @Schema(title = "角色ID")
    private Long roleId;

}
