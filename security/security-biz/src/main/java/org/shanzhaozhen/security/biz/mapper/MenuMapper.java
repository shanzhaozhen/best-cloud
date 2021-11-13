package org.shanzhaozhen.security.biz.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.shanzhaozhen.basiccommon.domain.sys.MenuDO;
import org.shanzhaozhen.basiccommon.dto.MenuDTO;

import java.util.List;

public interface MenuMapper extends BaseMapper<MenuDO> {

    @Select("select m.id, m.name, m.locale, m.path, m.pid, m.icon, m.priority, " +
            "m.hide_in_menu, m.hide_children_in_menu, m.props, m.description " +
            "from sys_menu m " +
            "inner join sys_role_menu srm on srm.role_id = #{roleId} and m.id = srm.menu_id")
    List<MenuDTO> getMenuByRoleId(@Param("roleId") Long roleId);

    @Select("select id, name, locale, path, pid, icon, priority, " +
            "hide_in_menu, hide_children_in_menu, props, description " +
            "from sys_menu order by priority")
    List<MenuDTO> getAllMenus();

    List<MenuDTO> getMenuRoleListByUserId(@Param("userId") Long userId);

}
