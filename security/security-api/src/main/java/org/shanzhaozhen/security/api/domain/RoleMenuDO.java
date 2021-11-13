package org.shanzhaozhen.security.api.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.shanzhaozhen.security.api.domain.BaseInfo;

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("sys_role_menu")
@Schema(description = "角色-菜单关系DO实体")
public class RoleMenuDO extends BaseInfo {

    private static final long serialVersionUID = 1642473344166749722L;

    @Schema(title = "主键ID")
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    @Schema(title = "角色ID")
    private Long roleId;

    @Schema(title = "菜单ID")
    private Long menuId;

}
