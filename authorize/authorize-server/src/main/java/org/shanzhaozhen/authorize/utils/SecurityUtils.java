package org.shanzhaozhen.authorize.utils;

import org.shanzhaozhen.uaa.pojo.dto.AuthUser;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * @Author: shanzhaozhen
 * @Date: 2022-08-29
 * @Description:
 */
public class SecurityUtils {

    public static AuthUser getCurrentUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal != null) {
            return (AuthUser) principal;
        }
        return null;
    }

    public static String getCurrentUserId() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal != null) {
            AuthUser authUser = (AuthUser) principal;
            return  authUser.getUserId();
        }
        return null;
    }

}
