package org.shanzhaozhen.basicapi.config.security;

import org.shanzhaozhen.basiccommon.enums.sys.ResourceType;
import org.shanzhaozhen.basiccommon.dto.ResourceDTO;
import org.shanzhaozhen.basiccommon.dto.RoleDTO;
import org.shanzhaozhen.basicservice.service.ResourceService;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

/**
 * FilterInvocationSecurityMetadataSource在被加载时候，会首先将权限的信息建立起来
 * 这里用一个resourceMap，key为url,value为该权限的名称，这一步是在构造方法中进行的，也就是服务器启动时候完成的。
 * 而当用户访问某一个地址时，SpringSecurity会到该类中调用getAttributes(Object obj)方法，
 * obj中包含了访问的url地址，我们需要做的就是将该url对应的权限名称返回给SpringSecurity，
 * 而SpringSecurity会将返回的这个对象，其实就是accessDecisionManager的decide方法中的configAttributes对象
 */
@Component
public class CustomFilterInvocationSecurityMetadataSource implements FilterInvocationSecurityMetadataSource {

    private final ResourceService resourceService;

    private HashMap<String, Collection<ConfigAttribute>> resourceMap;

    public CustomFilterInvocationSecurityMetadataSource(ResourceService resourceService) {
        this.resourceService = resourceService;
    }

    /**
     * 此方法用来将所有权限存至resourceMap，供给getAttributes判断
     */
    public void loadResourceDefine() {
        resourceMap = new HashMap<>();
        List<ResourceDTO> resourceDTOList = resourceService.getResourceRoleListByType(ResourceType.API.getValue());
        for (ResourceDTO resourceDTO : resourceDTOList) {
            Collection<ConfigAttribute> configAttributes = new ArrayList<>();
            //此处只添加了用户的名字，其实还可以添加更多权限的信息，例如请求方法到ConfigAttribute的集合中去。
            //此处添加的信息将会作为MyAccessDecisionManager类的decide的第三个参数z
            List<RoleDTO> roles = resourceDTO.getRoles();
            if (roles != null && roles.size() > 0) {
                for (RoleDTO roleDTO : roles) {
                    configAttributes.add(new SecurityConfig(roleDTO.getIdentification()));
                }
                resourceMap.put(resourceDTO.getPath(), configAttributes);
            }
        }
    }

    /**
     * 此方法是为了判定用户请求的url 是否在权限表中。
     * 如果在权限表中，则返回给 decide 方法，用来判定用户是否有此权限。
     * 如果不在权限表中则放行。
     */
    @Override
    public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
        if (resourceMap == null) {
            this.loadResourceDefine();
        }

        //取出拦截的 object 对象中包含用户的 request 请求信息
        HttpServletRequest httpRequest = ((FilterInvocation) object).getHttpRequest();
        AntPathRequestMatcher antPathRequestMatcher;
        for (String requestUrl : resourceMap.keySet()) {
            antPathRequestMatcher = new AntPathRequestMatcher(requestUrl);
            if (antPathRequestMatcher.matches(httpRequest)) {
                return resourceMap.get(requestUrl);
            }
        }
        /*
            Collection<ConfigAttribute> nullPermission = new ArrayList<ConfigAttribute>();
            nullPermission.add(new SecurityConfig("无相应权限"));
            return nullPermission;

            //防止数据库中没有数据，不能进行权限拦截
            if(collection.size() < 1) {
                ConfigAttribute configAttribute = new SecurityConfig("ROLE_NO_USER");
                collection.add(configAttribute);
            }
            return collection;
         */
        return null;
    }

    /**
     * getAllConfigAttributes 方法如果返回了所有定义的权限资源，Spring Security会在启动时校验每个ConfigAttribute是否配置正确，不需要校验直接返回null。
     * @return
     */
    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return true;
    }

}
