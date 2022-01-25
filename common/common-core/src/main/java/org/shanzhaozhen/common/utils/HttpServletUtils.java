package org.shanzhaozhen.common.utils;

import org.shanzhaozhen.common.result.R;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Objects;

public class HttpServletUtils {

    /**
     * 获取HttpServletRequest
     */
    public static HttpServletRequest getHttpServletRequest() {
        return ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
    }

    /**
     * 获取HttpServletResponse
     * @return
     */
    public static HttpServletResponse getHttpServletResponse() {
        return ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getResponse();
    }

    /**
     *
     */
    public static Object getAttribute(String name) {
        return getHttpServletRequest().getAttribute(name);
    }


    /**
     *
     * @param httpServletResponse
     * @param status
     * @param content
     * @throws IOException
     */
    public static void resultJson(HttpServletResponse httpServletResponse, Integer status, String content) throws IOException {
        httpServletResponse.setCharacterEncoding("UTF-8");
        httpServletResponse.setContentType("application/json; charset=utf-8");
        if (status != null) {
            httpServletResponse.setStatus(status);
        }
        PrintWriter writer = httpServletResponse.getWriter();
        writer.write(content);
    }


    public static void resultJson(HttpServletResponse httpServletResponse, Integer status, R<?> result) throws IOException {
        resultJson(httpServletResponse, status, JacksonUtils.objectToString(result));
    }

}
