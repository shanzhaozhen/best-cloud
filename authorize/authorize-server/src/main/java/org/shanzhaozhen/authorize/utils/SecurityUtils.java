package org.shanzhaozhen.authorize.utils;

import org.shanzhaozhen.uaa.pojo.dto.AuthUser;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

/**
 * @Author: shanzhaozhen
 * @Date: 2022-08-29
 * @Description:
 */
public class SecurityUtils {

    public static AuthUser getCurrentUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal != null) {
            if (principal instanceof AuthUser) {
                return (AuthUser) principal;
            }
        }
        return null;
    }

    public static String getCurrentUserId() {
        AuthUser currentUser = getCurrentUser();
        return Optional.ofNullable(currentUser).map(AuthUser::getUserId).orElse(null);
    }

}
