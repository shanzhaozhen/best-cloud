package org.shanzhaozhen.uaa.pojo.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.shanzhaozhen.common.core.entity.BaseInfoVO;
import org.shanzhaozhen.common.core.jackson.ToStringListSerialize;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "角色VO实体")
public class RoleVO extends BaseInfoVO {

    @Schema(description = "主键ID")
    private String id;

    @Schema(description = "名称")
    private String name;

    @Schema(description = "角色编码")
    private String code;

    @Schema(description = "描述")
    private String description;

    @Schema(description = "关联的菜单id")
    private List<String> menuIds;

    @Schema(description = "关联的资源id")
    private List<String> permissionIds;

}
