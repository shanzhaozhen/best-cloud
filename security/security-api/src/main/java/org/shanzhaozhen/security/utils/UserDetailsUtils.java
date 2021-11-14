package org.shanzhaozhen.security.utils;

import org.shanzhaozhen.security.dto.JWTUser;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.context.request.ServletWebRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class UserDetailsUtils {

    /**
     * 获取当前登录用户授权.
     */
    public static Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    /**
     * 获取当前用户名JWTUser.
     */
    public static JWTUser getJWTUser() {
        Authentication authentication = UserDetailsUtils.getAuthentication();
        if (authentication == null) {
            return null;
        }
        Object jwtUser = authentication.getPrincipal();
        if ("anonymousUser".equals(jwtUser)) {
            return null;
        }
        return (JWTUser) jwtUser;
    }

    /**
     * 获取当前登录用户名.
     */
    public static Long getUserId() {
        JWTUser jwtUser = UserDetailsUtils.getJWTUser();
        if (jwtUser == null) {
            return null;
        }
        return jwtUser.getId();
    }

    /**
     * 获取当前登录用户名.
     */
    public static String getUsername() {
        JWTUser jwtUser = UserDetailsUtils.getJWTUser();
        if (jwtUser == null) {
            return null;
        }
        return jwtUser.getUsername();
    }

    /**
     * 获取当前登录用户已有的权限.
     */
    public static List<String> getAuthorities() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        List<String> authorities = new ArrayList<>();
        for (GrantedAuthority granted : authentication.getAuthorities()) {
            authorities.add(granted.getAuthority());
        }
        return authorities;
    }

    /**
     * 全局获取HttpServletResponse对象
     */
    public static HttpServletResponse getHttpServletResponse() {
        return ((ServletWebRequest) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getResponse();
    }

    /**
     * 全局获取HttpServletRequest对象
     */
    public static HttpServletRequest getHttpServletRequest() {
        return ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
    }



}
