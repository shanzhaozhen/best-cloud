package org.shanzhaozhen.uaa.pojo.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "用户登陆信息")
public class CurrentUser {

    @Schema(description = "用户信息")
    private UserInfoVO userInfo;

    @Schema(description = "角色")
    private List<RoleBase> roles;

//    @Schema(description = "菜单")
//    private List<MenuVO> menus;

}
