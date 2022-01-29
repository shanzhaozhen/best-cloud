package org.shanzhaozhen.uaa.pojo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.shanzhaozhen.common.core.entity.BaseInfo;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "角色DTO实体")
public class RoleDTO extends BaseInfo {

    private static final long serialVersionUID = -4386224070617343831L;

    @Schema(title = "主键ID")
    private Long id;

    @Schema(title = "名称")
    private String name;

    @Schema(title = "角色代码")
    private String code;

    @Schema(title = "描述")
    private String description;

    @Schema(title = "关联的菜单")
    private List<MenuDTO> menus;

    @Schema(title = "关联的菜单ID")
    private List<Long> menuIds;

    @Schema(title = "关联的资源")
    private List<PermissionDTO> permissions;

    @Schema(title = "关联的资源ID")
    private List<Long> permissionIds;

}
