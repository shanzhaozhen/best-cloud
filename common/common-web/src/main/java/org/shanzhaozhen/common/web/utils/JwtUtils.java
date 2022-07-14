package org.shanzhaozhen.common.web.utils;

import com.nimbusds.jose.util.JSONObjectUtils;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.shanzhaozhen.common.core.constant.SecurityConstants;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.text.ParseException;
import java.util.*;

/**
 * @Author: shanzhaozhen
 * @Date: 2022-01-28
 * @Description: JWT工具类
 */
@Slf4j
public class JwtUtils {

    /**
     * 获取认证头
     * @return 认证头
     */
    @SneakyThrows
    public static Map<String, Object> getJwtPayload() {
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        Assert.notNull(servletRequestAttributes, "没有获取到 request 参数");

        String payload = servletRequestAttributes.getRequest().getHeader(SecurityConstants.JWT_PAYLOAD_KEY);
        Assert.hasText(payload, "请传入认证头");
        return JSONObjectUtils.parse(payload);
    }

    /**
     * 获取认证头
     * @return 认证头
     */
    @SneakyThrows
    public static Map<String, Object> getJwtPayloadWithoutError() {
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (servletRequestAttributes == null) return null;
        String payload = servletRequestAttributes.getRequest().getHeader(SecurityConstants.JWT_PAYLOAD_KEY);
        if (!StringUtils.hasText(payload)) return null;
        return JSONObjectUtils.parse(payload);
    }

    /**
     * 解析JWT获取用户ID
     * @return 用户ID
     */
    public static Long getUserId() {
        Object useId = getJwtPayload().get(SecurityConstants.USER_ID_KEY);
        return useId == null ? null : (Long) useId;
    }


    /**
     * 解析JWT获取用户ID（处理异常）
     * @return 用户ID
     */
    public static String getUserIdWithoutError() {
        Map<String, Object> payload = getJwtPayloadWithoutError();
        if (CollectionUtils.isEmpty(payload)) return null;
        Object useId = payload.get(SecurityConstants.USER_ID_KEY);
        return useId == null ? null : (String) useId;
    }


    /**
     * 解析JWT获取获取用户名
     * @return 用户名
     */
    public static String getUsername() {
        Object username = getJwtPayload().get(SecurityConstants.USER_NAME_KEY);
        return username == null ? null : (String) username;
    }


    /**
     * 解析JWT获取获取用户名（处理异常）
     * @return 用户名
     */
    public static String getUsernameWithoutError() {
        Map<String, Object> payload = getJwtPayloadWithoutError();
        if (CollectionUtils.isEmpty(payload)) return null;
        Object useId = payload.get(SecurityConstants.USER_NAME_KEY);
        return useId == null ? null : (String) useId;
    }


    /**
     * JWT获取用户角色列表
     * @return 角色列表
     */
    public static List<String> getRoles() {
        Map<String, Object> payload = getJwtPayload();
        if (payload.containsKey(SecurityConstants.JWT_AUTHORITIES_KEY)) {
            try {
                return JSONObjectUtils.getStringList(payload, SecurityConstants.JWT_AUTHORITIES_KEY);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return Collections.emptyList();
    }

    /**
     * 是否「超级管理员」
     * @return 是/否
     */
    public static boolean isRoot() {
        List<String> roles = getRoles();
        return !CollectionUtils.isEmpty(roles) && roles.contains("ROOT");
    }
}
