package org.shanzhaozhen.basiccommon.converter;

import org.shanzhaozhen.basiccommon.domain.sys.RouteDO;
import org.shanzhaozhen.basiccommon.dto.RoleDTO;
import org.shanzhaozhen.basiccommon.dto.RouteDTO;
import org.shanzhaozhen.basiccommon.form.RouteForm;
import org.shanzhaozhen.basiccommon.vo.AsyncRoute;
import org.shanzhaozhen.basiccommon.vo.Meta;
import org.shanzhaozhen.basiccommon.vo.RouteVO;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class RouteConverter {

    /**
     * RouteDTO 转换 RouteDO
     * @param routeDTO
     * @return
     */
    public static RouteDO toDO(RouteDTO routeDTO) {
        RouteDO routeDO = new RouteDO();
        BeanUtils.copyProperties(routeDTO, routeDO);
        return routeDO;
    }

    /**
     * RouteDO 转换 RouteDTO
     * @param routeDO
     * @return
     */
    public static RouteDTO toDTO(RouteDO routeDO) {
        RouteDTO routeDTO = new RouteDTO();
        BeanUtils.copyProperties(routeDO, routeDTO);
        return routeDTO;
    }

    /**
     * RouteForm 转换 RouteDTO
     * @param routeForm
     * @return
     */
    public static RouteDTO toDTO(RouteForm routeForm) {
        RouteDTO routeDTO = new RouteDTO();
        BeanUtils.copyProperties(routeForm, routeDTO);
        return routeDTO;
    }

    /**
     * RouteVO 转换 RouteDTO
     * @param routeVO
     * @return
     */
    public static RouteDTO toDTO(RouteVO routeVO) {
        RouteDTO routeDTO = new RouteDTO();
        BeanUtils.copyProperties(routeVO, routeDTO);
        return routeDTO;
    }

    /**
     * RouteDTO 转换 RouteVO
     * @param routeDTO
     * @return
     */
    public static RouteVO toVO(RouteDTO routeDTO) {
        RouteVO routeVO = new RouteVO();
        BeanUtils.copyProperties(routeDTO, routeVO);
        if (routeDTO.getChildren() != null && routeDTO.getChildren().size() > 0) {
            routeVO.setChildren(toVO(routeDTO.getChildren()));
        }
        return routeVO;
    }

    /**
     * List<RouteDTO> 转换 List<RouteVO>
     * @param routeDTOList
     * @return
     */
    public static List<RouteVO> toVO(List<RouteDTO> routeDTOList) {
        List<RouteVO> routeVOList = new ArrayList<>();
        for (RouteDTO routeDTO : routeDTOList) {
            routeVOList.add(toVO(routeDTO));
        }
        return routeVOList;
    }

    /**
     * 将RouteDTO转换成AsyncRoute供给前端渲染使用
     * @param routeDTO
     * @return
     */
    public static AsyncRoute toAsyncRoute(RouteDTO routeDTO) {
        AsyncRoute asyncRoute = new AsyncRoute();
        List<RoleDTO> roleDTOList = routeDTO.getRoles();
        List<String> roles = new ArrayList<>();
        for (RoleDTO roleDTO : roleDTOList) {
            roles.add(roleDTO.getIdentification());
        }
        Meta meta = new Meta();
        meta
                .setTitle(routeDTO.getTitle())
                .setIcon(routeDTO.getIcon())
                .setNoCache(routeDTO.getNoCache())
                .setAffix(routeDTO.getAffix())
                .setBreadcrumb(routeDTO.getBreadcrumb())
                .setRoles(roles);
        asyncRoute
                .setId(routeDTO.getId())
                .setName(routeDTO.getName())
                .setPath(routeDTO.getPath())
                .setPid(routeDTO.getPid())
                .setRedirect(routeDTO.getRedirect())
                .setPriority(routeDTO.getPriority())
                .setHidden(routeDTO.getHidden())
                .setAlwaysShow(routeDTO.getAlwaysShow())
                .setProps(routeDTO.getProps())
                .setMeta(meta)
                .setDescription(routeDTO.getDescription());
        if (routeDTO.getChildren() != null && routeDTO.getChildren().size() > 0) {
            asyncRoute.setChildren(toAsyncRoute(routeDTO.getChildren()));
        }
        return asyncRoute;
    }

    /**
     * 批量将RouteDTOList转换成AsyncRoute供给前端渲染使用
     * @param routeDTOList
     * @return
     */
    public static List<AsyncRoute> toAsyncRoute(List<RouteDTO> routeDTOList) {
        List<AsyncRoute> asyncRouteList = new ArrayList<>();
        for (RouteDTO routeDTO : routeDTOList) {
            asyncRouteList.add(toAsyncRoute(routeDTO));
        }
        return asyncRouteList;
    }

    /**
     * 将asyncRouteList生成树状结构
     * @param asyncRouteList
     * @return
     */
    public static List<AsyncRoute> builtAsyncRouteTree(List<AsyncRoute> asyncRouteList) {
        List<AsyncRoute> rootList = new ArrayList<>();
        List<AsyncRoute> noRootList = new ArrayList<>();

        for (AsyncRoute asyncRoute : asyncRouteList) {
            if (asyncRoute.getPid() == null || asyncRoute.getPid() <= 0) {
                rootList.add(asyncRoute);
            } else {
                noRootList.add(asyncRoute);
            }
        }

        getAsyncRouteChildren(noRootList, asyncRouteList);

        rootList.sort((Comparator.comparing(AsyncRoute::getPriority)));

        return rootList;
    }

    /**
     * 对动态路由子节点进行递归查找
     * @param noRootList
     * @param children
     * @return
     */
    public static List<AsyncRoute> getAsyncRouteChildren(List<AsyncRoute> noRootList, List<AsyncRoute> children) {
        for (AsyncRoute child : children) {
            List<AsyncRoute> grandsons = new ArrayList<>();
            for (AsyncRoute noRoot : noRootList) {
                if (child.getId().equals(noRoot.getPid())) {
                    grandsons.add(noRoot);
                }
            }
            if (grandsons.size() > 0) {
                child.setChildren(getAsyncRouteChildren(noRootList, grandsons));
            }
        }
        children.sort((Comparator.comparing(AsyncRoute::getPriority)));

        return children;
    }

    /**
     * 将routeList生成AsyncRoute的树状结构
     * @param routeDTOList
     * @return
     */
    public static List<AsyncRoute> builtAsyncRouteTreeByRouteList(List<RouteDTO> routeDTOList) {
        List<AsyncRoute> asyncRouteList = toAsyncRoute(routeDTOList);
        return builtAsyncRouteTree(asyncRouteList);
    }

    /**
     * 装资源list转成树状结构
     * @param routeDTOList
     * @return
     */
    public static List<RouteDTO> builtRouteTree(List<RouteDTO> routeDTOList) {
        List<RouteDTO> rootList = new ArrayList<>();
        List<RouteDTO> noRootList = new ArrayList<>();

        for (RouteDTO routeDTO : routeDTOList) {
            if (routeDTO.getPid() == null || routeDTO.getPid() <= 0) {
                rootList.add(routeDTO);
            } else {
                noRootList.add(routeDTO);
            }
        }

        getRouteDTOChildren(noRootList, routeDTOList);
        rootList.sort((Comparator.comparing(RouteDTO::getPriority)));

        return rootList;
    }

    /**
     * 对动态路由子节点进行递归查找
     * @param noRootList
     * @param children
     * @return
     */
    public static List<RouteDTO> getRouteDTOChildren(List<RouteDTO> noRootList, List<RouteDTO> children) {
        for (RouteDTO child : children) {
            List<RouteDTO> grandsons = new ArrayList<>();
            for (RouteDTO noRoot : noRootList) {
                if (child.getId().equals(noRoot.getPid())) {
                    grandsons.add(noRoot);
                }
            }
            if (grandsons.size() > 0) {
                child.setChildren(getRouteDTOChildren(noRootList, grandsons));
            }
        }
        children.sort((Comparator.comparing(RouteDTO::getPriority)));

        return children;
    }

}
