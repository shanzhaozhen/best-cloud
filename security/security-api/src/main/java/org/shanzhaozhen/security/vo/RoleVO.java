package org.shanzhaozhen.security.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.shanzhaozhen.common.entity.BaseInfoVO;
import org.shanzhaozhen.common.jackson.ToStringListSerialize;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "角色VO实体")
public class RoleVO extends BaseInfoVO {

    @Schema(title = "主键ID")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    @Schema(title = "名称")
    private String name;

    @Schema(title = "角色代码")
    private String code;

    @Schema(title = "描述")
    private String description;

    @Schema(title = "关联的菜单id")
    @JsonSerialize(using = ToStringListSerialize.class)
    private List<Long> menuIds;

    @Schema(title = "关联的资源id")
    @JsonSerialize(using = ToStringListSerialize.class)
    private List<Long> resourceIds;

}