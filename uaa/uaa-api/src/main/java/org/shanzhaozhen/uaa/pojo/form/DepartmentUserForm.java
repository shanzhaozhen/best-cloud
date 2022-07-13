package org.shanzhaozhen.uaa.pojo.form;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "部门-用户关系Form实体")
public class DepartmentUserForm {

    @Schema(description = "角色ID")
    @NotNull(message = "部门ID不能为空")
    private Long departmentId;

    @Schema(description = "用户ID")
    @NotEmpty(message = "用户id不能为空")
    private List<Long> userIds;


}
