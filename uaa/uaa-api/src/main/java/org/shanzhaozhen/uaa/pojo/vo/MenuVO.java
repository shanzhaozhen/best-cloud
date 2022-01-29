package org.shanzhaozhen.uaa.pojo.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.shanzhaozhen.common.core.entity.BaseInfoVO;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "菜单VO实体")
public class MenuVO extends BaseInfoVO {

    @Schema(title = "主键ID")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    @Schema(title = "菜单名称")
    private String name;

    @Schema(title = "菜单名称（本地化）")
    private String locale;

    @Schema(title = "菜单路径")
    private String path;

    @Schema(title = "上级ID")
    @JsonSerialize(using = ToStringSerializer.class)
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
    private List<RoleVO> roleVOList;

    @Schema(title = "下级菜单")
    private List<MenuVO> children;

    @Schema(title = "授权角色")
    private List<String> access;

}
