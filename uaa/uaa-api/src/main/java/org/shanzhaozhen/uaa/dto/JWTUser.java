package org.shanzhaozhen.uaa.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "JWT登陆用户信息实体")
public class JWTUser implements Serializable {

    private static final long serialVersionUID = 7599077964056596636L;

    private Long id;

    private String username;

    private List<CustomGrantedAuthority> authorities;

}
