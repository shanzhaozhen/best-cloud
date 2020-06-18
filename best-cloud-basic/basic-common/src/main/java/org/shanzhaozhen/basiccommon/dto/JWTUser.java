package org.shanzhaozhen.basiccommon.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.io.Serializable;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@ApiModel(description = "JWT登陆用户信息实体")
public class JWTUser implements Serializable {

    private static final long serialVersionUID = 6857556037808586928L;

    private Long id;

    private String username;

    private List<SimpleGrantedAuthority> authorities;

}
