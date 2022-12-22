package org.shanzhaozhen.authorize.pojo.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @Author: shanzhaozhen
 * @Date: 2022-09-06
 * @Description:
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "github 用户信息实体")
public class GithubUserInfo {


    @Schema(description = "github 用户名")
    private String username;

    @Schema(description = "头像")
    private String avatarUrl;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @Schema(description = "绑定时间")
    private LocalDateTime bindDate;

}
