package org.shanzhaozhen.security.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.shanzhaozhen.common.entity.BaseInfo;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "角色DTO实体")
public class UserInfoDTO extends BaseInfo {

    private static final long serialVersionUID = -7438360875495028237L;

    @Schema(title = "主键ID")
    private Long id;

    @Schema(title = "关联用户id")
    private Long pid;

    @Schema(title = "部门ID")
    private Long depId;

    @Schema(title = "姓名")
    private String name;

    @Schema(title = "昵称")
    private String nickname;

    @Schema(title = "性别")
    private Integer sex;

    @Schema(title = "生日")
    private Date birthday;

    @Schema(title = "头像")
    private String avatar;

    @Schema(title = "邮箱")
    private String email;

    @Schema(title = "手机号码")
    private String phoneNumber;

    @Schema(title = "地址编号")
    private String addressCode;

    @Schema(title = "详细地址")
    private String detailedAddress;

    @Schema(title = "个人介绍")
    private String introduction;

    @Schema(title = "记录用户的角色")
    private List<RoleDTO> roles;

    @Schema(title = "关联的角色ID")
    private List<Long> roleIds;

}
