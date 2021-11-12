package org.shanzhaozhen.basiccommon.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "菜单DTO实体")
public class MenuDTO implements Serializable {

    private static final long serialVersionUID = -6297609093568274879L;

    @Schema(title = "主键ID")
    private Long id;

    @Schema(title = "菜单名称")
    private String name;

    @Schema(title = "菜单名称（本地化）")
    private String locale;

    @Schema(title = "菜单路径")
    private String path;

    @Schema(title = "上级ID")
    private Long pid;

    @Schema(title = "图标")
    private String icon;

    @Schema(title = "排序等级")
    private Integer priority;

    @Schema(title = "菜单是否隐藏")
    private Boolean hideInMenu;

    @Schema(title = "隐藏子节点")
    private Boolean hideChildrenInMenu;

    @Schema(title = "参数")
    private String props;

    @Schema(title = "菜单描述")
    private String description;

    @Schema(title = "关联的角色")
    private List<RoleDTO> roles;

    @Schema(title = "下级菜单")
    private List<MenuDTO> children;
}
