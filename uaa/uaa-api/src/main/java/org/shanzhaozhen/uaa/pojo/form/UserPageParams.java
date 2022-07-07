package org.shanzhaozhen.uaa.pojo.form;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.shanzhaozhen.common.core.entity.BasePageParams;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "用户搜索Form实体")
public class UserPageParams<T> extends BasePageParams<T> {

    @Schema(description = "角色ID")
    Long roleId;

    @Schema(description = "部门ID")
    Long departmentId;


}
