package org.shanzhaozhen.common.utils;

import lombok.extern.slf4j.Slf4j;
import org.shanzhaozhen.common.entity.MethodParamInfo;
import org.springframework.util.ClassUtils;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class CustomClassUtils {

    /**
     * 通过Class类、方法名、参数获取方法
     * @param tClass
     * @param methodName
     * @param params
     * @param <T>
     * @return
     */
    public static <T> Method getMethod(Class<T> tClass, String methodName, Object... params) {
        if (null != params && params.length > 0) {
            Class<?>[] paramCls = CustomClassUtils.getParamTypes(params);
            return ClassUtils.getMethod(tClass, methodName, paramCls);
        } else {
            return ClassUtils.getMethod(tClass, methodName);
        }
    }

    /**
     * 获取对应参数中的Class
     * @param params
     * @return
     */
    public static Class<?>[] getParamTypes(Object[] params) {
        List<Class<?>> classList = new ArrayList<>();
        for (Object param : params) {
            classList.add(param.getClass());
        }
        return classList.toArray(new Class[0]);
    }

    /**
     * 执行方法
     * @param beanName
     * @param methodName
     * @param params
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    public static Object executeMethod(String beanName, String methodName, Class<?>[] paramTypes, Object... params) throws InvocationTargetException, IllegalAccessException {
        Object target = SpringContextUtils.getBean(beanName);
        return CustomClassUtils.executeMethod(target, methodName, paramTypes, params);
    }

    public static Object executeMethod(Object target, String methodName, Class<?>[] paramTypes, Object... params) throws InvocationTargetException, IllegalAccessException {
        Method method = ClassUtils.getMethod(target.getClass(), methodName, paramTypes);
        ReflectionUtils.makeAccessible(method);
        if (null != params && params.length > 0) {
            return method.invoke(target, params);
        } else {
            return method.invoke(target);
        }
    }

    /**
     * 将方法的json值转成对应的方法的参数
     * @param paramJson
     * @return
     */
    public static Object[] methodParamInfoToParams(String paramJson) {
        List<MethodParamInfo> methodParamInfoList = JacksonUtils.toPojoList(paramJson, MethodParamInfo.class);;
        return CustomClassUtils.methodParamInfoToParams(methodParamInfoList);
    }

    /**
     * 将MethodParamInfo的参数转成对应的方法的参数
     * @param methodParamInfos
     * @return
     */
    public static Object[] methodParamInfoToParams(List<MethodParamInfo> methodParamInfos) {
        try {
            List<Object> params = new ArrayList<>();
            for (MethodParamInfo methodParamInfo : methodParamInfos) {
                Object param = JacksonUtils.toOPojo(methodParamInfo.getParamValue(), Object.class);
                params.add(param);
            }
            return params.toArray();
        } catch (Exception e) {
            e.printStackTrace();
            log.error("方法参数JSON转换至java参数出错，请检查输入的参数");
            throw new IllegalArgumentException("方法参数JSON转换至java参数出错，请检查输入的参数");
        }
    }

}
