package org.shanzhaozhen.oauth.pojo.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.shanzhaozhen.common.core.entity.BaseInfo;

import java.time.LocalDateTime;

/**
 * @Author: shanzhaozhen
 * @Date: 2022-09-02
 * @Description:
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "oauth2用户授权记录DTO实体")
public class OAuth2UserSocialDTO extends BaseInfo {

    private static final long serialVersionUID = 7833076780131728902L;

    @Schema(description = "主键ID")
    private String id;

    @Schema(description = "关联用户id")
    private String userId;

    @Schema(description = "第三方类型")
    private String identityType;

    @Schema(description = "第三方的唯一标识")
    private String identifier;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime bindDate;

    @Schema(description = "昵称")
    private String name;

    @Schema(description = "头像")
    private String avatarUrl;

    @Schema(description = "其他信息")
    private String other;

}
