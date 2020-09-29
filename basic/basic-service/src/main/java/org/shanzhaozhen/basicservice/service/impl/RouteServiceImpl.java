package org.shanzhaozhen.basicservice.service.impl;

import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import org.shanzhaozhen.basiccommon.converter.RouteConverter;
import org.shanzhaozhen.basiccommon.domain.sys.RouteDO;
import org.shanzhaozhen.basiccommon.vo.AsyncRoute;
import org.shanzhaozhen.basiccommon.dto.RouteDTO;
import org.shanzhaozhen.basicrepo.mapper.RouteMapper;
import org.shanzhaozhen.basicservice.service.RouteService;
import org.shanzhaozhen.basiccommon.utils.CustomBeanUtils;
import org.shanzhaozhen.basiccommon.utils.UserDetailsUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.List;

@Service
public class RouteServiceImpl implements RouteService {

    private final RouteMapper routeMapper;

    public RouteServiceImpl(RouteMapper routeMapper) {
        this.routeMapper = routeMapper;
    }

    @Override
    public List<RouteDTO> getRouteRoleListByType(Integer type) {
        return routeMapper.getRouteRoleListByUserId(null);
    }

    @Override
    public List<AsyncRoute> getRoutesByCurrentUser() {
        Long userId = UserDetailsUtils.getUserId();
        Assert.notNull(userId, "没有获取到当前的登录状态或为匿名用户");
        List<RouteDTO> routeDTOList = routeMapper.getRouteRoleListByUserId(userId);
        return RouteConverter.builtAsyncRouteTreeByRouteList(routeDTOList);
    }

    @Override
    public List<RouteDTO> getAllRouteTree() {
        List<RouteDTO> routeDTOList = routeMapper.getRouteList();
        return RouteConverter.builtRouteTree(routeDTOList);
    }

    @Override
    public RouteDTO getRouteById(Long routeId) {
        RouteDO routeDO = routeMapper.selectById(routeId);
        Assert.notNull(routeDO, "获取失败：没有找到该菜单或已被删除");
        return RouteConverter.toDTO(routeDO);
    }

    @Override
    @Transactional
    public Long addRoute(RouteDTO routeDTO) {
        RouteDO routeDO = RouteConverter.toDO(routeDTO);
        routeMapper.insert(routeDO);
        return routeDO.getId();
    }

    @Override
    @Transactional
    public Long updateRoute(RouteDTO routeDTO) {
        Assert.notNull(routeDTO.getId(), "更新失败：路由id不能为空");
        Assert.isTrue(!routeDTO.getId().equals(routeDTO.getPid()), "更新失败：上级节点不能选择自己");
        if (routeDTO.getPid() != null) {
            RouteDO routePNode = routeMapper.selectById(routeDTO.getPid());
            Assert.notNull(routePNode, "更新失败：没有找到该路由的上级节点或已被删除");
            Assert.isTrue(!routeDTO.getId().equals(routePNode.getPid()), "更新失败：节点之间不能互相引用");
        }
        RouteDO routeDO = routeMapper.selectById(routeDTO.getId());
        Assert.notNull(routeDO, "更新失败：没有找到该路由或已被删除");
        CustomBeanUtils.copyPropertiesExcludeMeta(routeDTO, routeDO);
        routeMapper.updateById(routeDO);
        try {
            this.getAllRouteTree();
        } catch (StackOverflowError e) {
            throw new IllegalArgumentException("更新失败：请检查路由的节点设置是否有问题");
        }
        return routeDO.getId();
    }

    @Override
    @Transactional
    public Long deleteRoute(Long routeId) {
        routeMapper.deleteById(routeId);
        return routeId;
    }

}
