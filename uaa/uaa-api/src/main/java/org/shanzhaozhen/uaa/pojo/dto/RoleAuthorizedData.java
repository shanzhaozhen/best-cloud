package org.shanzhaozhen.uaa.pojo.dto;

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
@Schema(description = "角色关联的授权信息")
public class RoleAuthorizedData implements Serializable {

    private static final long serialVersionUID = -8625871399478063273L;

    private String roleId;

    private List<String> permissionIds;

    private List<String> menuIds;

}
