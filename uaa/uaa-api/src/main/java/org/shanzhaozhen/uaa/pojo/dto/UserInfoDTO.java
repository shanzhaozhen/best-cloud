package org.shanzhaozhen.uaa.pojo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.shanzhaozhen.common.core.entity.BaseInfo;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "角色DTO实体")
public class UserInfoDTO extends BaseInfo {

    private static final long serialVersionUID = -7438360875495028237L;

    @Schema(description = "主键ID")
    private Long id;

    @Schema(description = "关联用户id")
    private Long pid;

    @Schema(description = "姓名")
    private String name;

    @Schema(description = "昵称")
    private String nickname;

    @Schema(description = "性别")
    private Integer sex;

    @Schema(description = "生日")
    private LocalDate birthday;

    @Schema(description = "头像")
    private String avatar;

    @Schema(description = "邮箱")
    private String email;

    @Schema(description = "手机号码")
    private String phoneNumber;

    @Schema(description = "地址编号")
    private String addressCode;

    @Schema(description = "详细地址")
    private String detailedAddress;

    @Schema(description = "个人介绍")
    private String introduction;

}
