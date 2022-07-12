package org.shanzhaozhen.uaa.pojo.form;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.ibatis.annotations.Update;
import org.shanzhaozhen.common.core.entity.BaseInfo;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "部门-用户关系Form实体")
public class DepartmentUserForm {

    @Schema(description = "主键ID")
    private Long id;

    @Schema(description = "部门ID")
    private Long departmentId;

    @Schema(description = "用户ID")
    private Long userId;

}
