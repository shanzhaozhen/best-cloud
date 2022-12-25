package org.shanzhaozhen.authorize.pojo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "用户基础信息实体")
public class UserInfoBase {

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

}