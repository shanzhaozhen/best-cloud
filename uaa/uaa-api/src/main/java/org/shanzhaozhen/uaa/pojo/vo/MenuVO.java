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

    @Schema(description = "主键ID")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    @Schema(description = "菜单名称")
    private String name;

    @Schema(description = "菜单名称（本地化）")
    private String locale;

    @Schema(description = "菜单路径")
    private String path;

    @Schema(description = "上级ID")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long pid;

    @Schema(description = "图标")
    private String icon;

    @Schema(description = "排序等级")
    private Integer priority;

    @Schema(description = "菜单是否隐藏")
    private Boolean hideInMenu;

    @Schema(description = "隐藏子节点")
    private Boolean hideChildrenInMenu;

    @Schema(description = "参数")
    private String props;

    @Schema(description = "菜单描述")
    private String description;

    @Schema(description = "关联的角色")
    private List<RoleVO> roleVOList;

    @Schema(description = "下级菜单")
    private List<MenuVO> children;

    @Schema(description = "授权角色")
    private List<String> access;

}
