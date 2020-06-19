package org.shanzhaozhen.bestcloudsecurity.config;

import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Collection;

/**
 * accessDecisionManager中有decide(Authentication authentication,
 * Object object,Collection<ConfigAttribute> configAttributes)方法，
 * 该方法用于判断当前用户是否有权限进行操作，参数中authentication包含了当前用户所拥有的权限，
 * configAttributes中包含了进行该步骤需要的权限，对其进行对比就可以判断该用户是否有权限进行操作
 */
@Component
public class CustomAccessDecisionManager implements AccessDecisionManager {

    /**
     * decide           是判定是否拥有权限的决策方法，
     * authentication   是释 UserDetailsService 中循环添加到 GrantedAuthority 对象中的权限信息集合.
     * object           包含客户端发起的请求的requset信息,
     *                  可转换为 HttpServletRequest request = ((FilterInvocation) object).getHttpRequest();
     * configAttributes 为FilterInvocationSecurityMetadataSource的getAttributes(Object object)这个方法返回的结果,
     *                  此方法是为了判定用户请求的url 是否在权限表中，如果在权限表中，则返回给 decide 方法，
     *                  用来判定用户是否有此权限。如果不在权限表中则放行。
     */
    @Override
    public void decide(Authentication authentication, Object object, Collection<ConfigAttribute> configAttributes) throws AccessDeniedException, InsufficientAuthenticationException {
        if (configAttributes == null) {
            return;
        }
        //所请求的资源拥有的权限(一个资源对多个权限)
        for (ConfigAttribute configAttribute : configAttributes) {
            //访问所请求资源所需要的权限
            String needRole = configAttribute.getAttribute();
            for (GrantedAuthority grantedAuthority : authentication.getAuthorities()) {
                //如果访问的资源在用户的权限表里面，则不拦截
                if (needRole.equals(grantedAuthority.getAuthority())) {
                    return;
                }
            }
        }
        throw new AccessDeniedException("没有权限访问！");
    }

    @Override
    public boolean supports(ConfigAttribute attribute) {
        return true;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return true;
    }

}
