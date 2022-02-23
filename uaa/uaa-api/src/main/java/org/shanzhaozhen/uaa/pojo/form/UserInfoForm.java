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


    @Schema(title = "主键ID")
    @NotNull(groups = {Update.class}, message = "用户信息id不能为空")
    private Long id;

    @Schema(title = "关联用户id")
    @NotNull(groups = {Update.class}, message = "用户关联id不能为空")
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

}
