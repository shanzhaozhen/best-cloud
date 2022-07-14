package org.shanzhaozhen.uaa.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.shanzhaozhen.uaa.pojo.entity.MenuDO;
import org.shanzhaozhen.uaa.pojo.dto.MenuDTO;

import java.util.List;

public interface MenuMapper extends BaseMapper<MenuDO> {

    List<MenuDTO> getMenuByPid(@Param("pid") String pid);

    List<MenuDTO> getMenuByRoleId(@Param("roleId") String roleId);

    List<MenuDTO> getAllMenus();

    List<MenuDTO> getMenuRoleListByUserId(@Param("userId") String userId);

}
