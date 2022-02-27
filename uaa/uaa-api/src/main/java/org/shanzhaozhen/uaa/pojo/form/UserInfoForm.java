package org.shanzhaozhen.uaa.pojo.form;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.ibatis.annotations.Update;
import org.shanzhaozhen.common.core.entity.BaseInfo;

import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "角色DTO实体")
public class UserInfoForm extends BaseInfo {

    @Schema(description = "主键ID")
    @NotNull(groups = {Update.class}, message = "用户信息id不能为空")
    private Long id;

    @Schema(description = "关联用户id")
    @NotNull(groups = {Update.class}, message = "用户关联id不能为空")
    private Long pid;

    @Schema(description = "部门ID")
    private Long depId;

    @Schema(description = "姓名")
    private String name;

    @Schema(description = "昵称")
    private String nickname;

    @Schema(description = "性别")
    private Integer sex;

    @Schema(description = "生日")
    private Date birthday;

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
