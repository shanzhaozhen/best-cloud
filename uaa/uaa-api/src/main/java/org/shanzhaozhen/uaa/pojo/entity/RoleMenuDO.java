package org.shanzhaozhen.uaa.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.Version;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.shanzhaozhen.common.core.entity.BaseInfo;

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("sys_role_menu")
@Schema(description = "角色-菜单关系DO实体")
public class RoleMenuDO extends BaseInfo {

    private static final long serialVersionUID = 1642473344166749722L;

    @Schema(description = "主键ID")
    @TableId(type = IdType.ASSIGN_ID)
    private String id;

    @Schema(description = "角色ID")
    private String roleId;

    @Schema(description = "菜单ID")
    private String menuId;

    @Schema(description = "版本号")
    @Version
    private Integer version;

}
