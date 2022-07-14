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

    @Schema(description = "主键ID")
    private String id;

    @Schema(description = "名称")
    private String name;

    @Schema(description = "角色编码")
    private String code;

    @Schema(description = "描述")
    private String description;

    @Schema(description = "关联的菜单")
    private List<MenuDTO> menus;

    @Schema(description = "关联的菜单ID")
    private List<String> menuIds;

    @Schema(description = "关联的资源")
    private List<PermissionDTO> permissions;

    @Schema(description = "关联的资源ID")
    private List<String> permissionIds;

}
