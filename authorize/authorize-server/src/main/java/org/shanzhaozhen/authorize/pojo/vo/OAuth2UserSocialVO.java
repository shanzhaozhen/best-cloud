package org.shanzhaozhen.authorize.pojo.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @Author: shanzhaozhen
 * @Date: 2022-09-02
 * @Description:
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "oauth2用户授权记录VO实体")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OAuth2UserSocialVO {

    @Schema(description = "第三方的唯一标识")
    private String identifier;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime bindDate;

    @Schema(description = "昵称")
    private String name;

    @Schema(description = "头像")
    private String avatarUrl;

}
