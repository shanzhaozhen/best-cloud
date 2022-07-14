package org.shanzhaozhen.uaa.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.shanzhaozhen.uaa.pojo.entity.PermissionDO;
import org.shanzhaozhen.uaa.pojo.dto.PermissionDTO;

import java.util.List;

public interface PermissionMapper extends BaseMapper<PermissionDO> {

    List<PermissionDTO> getPermissionByPid(@Param("pid") String pid);
    List<PermissionDTO> getPermissionByRoleId(@Param("roleId") String roleId);

    List<PermissionDTO> getPermissionListByType(@Param("type") Integer type);

    List<PermissionDTO> getPermissionRoleListByTypeAndUserId(@Param("type") Integer type, @Param("userId") String userId);

    List<PermissionDTO> getPermissionList();

}
