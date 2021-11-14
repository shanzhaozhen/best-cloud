package org.shanzhaozhen.security.config.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.SecurityMetadataSource;
import org.springframework.security.access.intercept.AbstractSecurityInterceptor;
import org.springframework.security.access.intercept.InterceptorStatusToken;
import org.springframework.security.web.FilterInvocation;

import javax.servlet.*;
import java.io.IOException;

@RequiredArgsConstructor
public class CustomFilterSecurityInterceptor extends AbstractSecurityInterceptor implements Filter {

    // 防止重复进入过滤器，给该过滤器添加标识id
    private static final String FILTER_APPLIED = "CustomFilterSecurityInterceptorFilterApplied";

    private final CustomFilterInvocationSecurityMetadataSource customFilterInvocationSecurityMetadataSource;

    @Override
    public void init(FilterConfig filterConfig) {
    }

    @Override
    public void destroy() {
    }

    /**
     * 所有的请求到了这一个filter，如果这个filter之前没有执行过的话，
     * 那么首先执行的 InterceptorStatusToken token = super.beforeInvocation(filterInvocation);
     * 这个是由AbstractSecurityInterceptor提供。它就是spring security处理鉴权的入口。
     */
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        // 这个类的作用本身很简单，就是把doFilter传进来的ServletRequest,ServletResponse和FilterChain对象保存起来，
        // 供FilterSecurityInterceptor的处理代码调用
        FilterInvocation filterInvocation = new FilterInvocation(servletRequest, servletResponse, filterChain);
        this.invoke(filterInvocation);
    }

    @Override
    public SecurityMetadataSource obtainSecurityMetadataSource() {
        return this.customFilterInvocationSecurityMetadataSource;
    }

    @Override
    public Class<?> getSecureObjectClass() {
        return FilterInvocation.class;
    }

    /**
     * filterInvocation里面有一个被拦截的url
     * 里面调用 CustomInvocationSecurityMetadataSource 的 getAttributes(Object object) 这个方法获取 filterInvocation 对应的所有权限
     * 再调用CustomAccessDecisionManager的decide方法来校验用户的权限是否足够
     */
    public void invoke(FilterInvocation filterInvocation) throws IOException, ServletException {
        // 每个请求处理一次，已经处理的请求不需要重新执行安全检查
        // 如果已经进入过一次过滤器则直接跳过
        if (filterInvocation.getRequest() != null && filterInvocation.getRequest().getAttribute(FILTER_APPLIED) != null) {
            filterInvocation.getChain().doFilter(filterInvocation.getRequest(), filterInvocation.getResponse());
        }
        else {
            // 第一次调用此请求时，则进行权限检查
            if (filterInvocation.getRequest() != null) {
                filterInvocation.getRequest().setAttribute(FILTER_APPLIED, Boolean.TRUE);
            }

            InterceptorStatusToken token = super.beforeInvocation(filterInvocation);

            try {
                filterInvocation.getChain().doFilter(filterInvocation.getRequest(), filterInvocation.getResponse());
            }
            finally {
                super.finallyInvocation(token);
            }

            super.afterInvocation(token, null);
        }

    }

}
