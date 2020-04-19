package org.shanzhaozhen.bestcloudcommon.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "用户登陆信息")
public class UserInfo {

    @ApiModelProperty(value = "昵称")
    private String nickname;

    @ApiModelProperty(value = "头像")
    private String avatar;

    @ApiModelProperty(value = "个人简介")
    private String introduction;

    @ApiModelProperty(value = "角色")
    private List<RoleBase> roles;

    @ApiModelProperty(value = "菜单")
    private List<AsyncRoute> asyncRoutes;

}
