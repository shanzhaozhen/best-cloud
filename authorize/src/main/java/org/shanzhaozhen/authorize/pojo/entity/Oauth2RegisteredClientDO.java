package org.shanzhaozhen.authorize.pojo.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("oauth2_registered_client")
@Schema(description = "oauth2客户端信息DO实体")
public class Oauth2RegisteredClientDO {

    @Schema(description = "id")
    private String id;

    @Schema(description = "客户端id")
    private String registeredClientId;

    @Schema(description = "客户端密码")
    private String clientSecret;

    @Schema(description = "客户端名称")
    private String clientName;

}
