package org.shanzhaozhen.authorize.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.shanzhaozhen.basiccommon.domain.sys.RouteDO;
import org.shanzhaozhen.basiccommon.dto.RouteDTO;

import java.util.List;

public interface RouteMapper extends BaseMapper<RouteDO> {

    @Select("select r.id, r.name, r.path, r.pid, r.redirect, r.title, r.icon, r.priority, " +
            "r.hidden, r.always_show, r.no_cache, r.affix, r.breadcrumb, r.props, r.description " +
            "from sys_route r " +
            "inner join sys_role_route srr on srr.role_id = #{roleId} and r.id = srr.route_id")
    List<RouteDTO> getRouteByRoleId(@Param("roleId") Long roleId);

    @Select("select id, name, path, pid, redirect, title, icon, priority, " +
            "hidden, always_show, no_cache, affix, breadcrumb, props, description " +
            "from sys_route order by priority")
    List<RouteDTO> getRouteList();

    List<RouteDTO> getRouteRoleListByUserId(@Param("userId") Long userId);
}
