package org.shanzhaozhen.authorize.service;

import org.shanzhaozhen.basiccommon.dto.RouteDTO;
import org.shanzhaozhen.basiccommon.vo.AsyncRoute;

import java.util.List;

public interface RouteService {

    /**
     * 通过 RouteType 类型获取所有的Route（多对多含有角色信息）
     * @param type
     * @return
     */
    List<RouteDTO> getRouteRoleListByType(Integer type);

    /**
     * 通过当前用户的信息生成对应的前端路由
     * @return
     */
    List<AsyncRoute> getRoutesByCurrentUser();

    /**
     * 获取所有资源的树形结构
     * @return
     */
    List<RouteDTO> getAllRouteTree();

    /**
     * 通过资源id获取资源实体
     * @param routeId
     * @return
     */
    RouteDTO getRouteById(Long routeId);

    /**
     * 增加资源
     * @param routeDTO
     * @return
     */
    Long addRoute(RouteDTO routeDTO);

    /**
     * 修改资源
     * @param routeDTO
     * @return
     */
    Long updateRoute(RouteDTO routeDTO);

    /**
     * 删除资源(通过资源id删除)
     * @param routeId
     * @return
     */
    Long deleteRoute(Long routeId);

}
