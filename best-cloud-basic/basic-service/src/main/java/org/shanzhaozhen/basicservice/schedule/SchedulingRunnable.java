package org.shanzhaozhen.basicservice.schedule;

import lombok.extern.slf4j.Slf4j;
import org.shanzhaozhen.basiccommon.utils.CustomClassUtils;
import org.springframework.lang.Nullable;

import javax.validation.constraints.NotEmpty;
import java.util.Arrays;
import java.util.Objects;

@Slf4j
public class SchedulingRunnable implements Runnable {

    private String beanName;

    private String methodName;

    private Class<?>[] paramTypes;

    private Object[] params;

    public SchedulingRunnable(@NotEmpty String beanName, @NotEmpty String methodName) {
        this.beanName = beanName;
        this.methodName = methodName;
    }

    public SchedulingRunnable(@NotEmpty String beanName, @NotEmpty String methodName, @Nullable Class<?>[] paramTypes, @Nullable Object...params) {
        this.beanName = beanName;
        this.methodName = methodName;
        this.paramTypes = paramTypes;
        this.params = params;
    }

    @Override
    public void run() {
        log.info("定时任务开始执行 - bean：{}，方法：{}，参数类型：{}，参数：{}", beanName, methodName, paramTypes, params);
        long startTime = System.currentTimeMillis();

        try {
            CustomClassUtils.executeMethod(beanName, methodName, paramTypes, params);
        } catch (Exception e) {
            log.error("定时任务执行异常 - bean：{}，方法：{}，参数类型：{}，参数：{}", beanName, methodName, paramTypes, params, e);
        }

        log.info("定时任务执行结束 - bean：{}，方法：{}，参数类型：{}，参数：{}，耗时：{} 毫秒", beanName, methodName, paramTypes, params, System.currentTimeMillis() - startTime);
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        SchedulingRunnable that = (SchedulingRunnable) object;
        if (params == null) {
            return beanName.equals(that.beanName) &&
                    methodName.equals(that.methodName) &&
                    that.params == null;
        }

        return beanName.equals(that.beanName) &&
                methodName.equals(that.methodName) &&
                Arrays.equals(params, that.params);
    }

    @Override
    public int hashCode() {
        if (params == null) {
            return Objects.hash(beanName, methodName);
        }

        return Objects.hash(beanName, methodName, params);
    }

}
