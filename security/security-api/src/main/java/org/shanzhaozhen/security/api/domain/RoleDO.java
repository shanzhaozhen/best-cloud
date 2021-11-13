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
@TableName("sys_role")
@Schema(description = "角色DO实体")
public class RoleDO extends BaseInfo {

    private static final long serialVersionUID = 6203528166202612882L;

    @Schema(title = "主键ID")
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    @Schema(title = "名称")
    private String name;

    @Schema(title = "角色代码")
    private String code;

    @Schema(title = "描述")
    private String description;

}
