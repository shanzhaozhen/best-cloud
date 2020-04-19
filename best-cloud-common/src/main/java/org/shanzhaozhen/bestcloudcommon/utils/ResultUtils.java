package org.shanzhaozhen.bestcloudcommon.utils;

import org.shanzhaozhen.bestcommon.common.sys.ResultType;
import org.shanzhaozhen.bestcommon.vo.ResultObject;

public class ResultUtils {

    public static ResultObject<?> success() {
        return new ResultObject<>(ResultType.SUCCESS, "success");
    }

    public static <T> ResultObject<T> success(T data) {
        return new ResultObject<>(ResultType.SUCCESS, "success", data);
    }

    public static ResultObject<?> success(String message) {
        return new ResultObject<>(ResultType.SUCCESS, message);
    }

    public static <T> ResultObject<T> success(String message, T data) {
        return new ResultObject<>(ResultType.SUCCESS, message, data);
    }

    public static ResultObject<?> failure() {
        return new ResultObject<>(ResultType.FAILURE, "failure");
    }

    public static ResultObject<?> failure(String message) {
        return new ResultObject<>(ResultType.FAILURE, message);
    }

    public static ResultObject<?> failure(Integer code, String message) {
        return new ResultObject<>(code, message);
    }

    public static ResultObject<?> defaultResult(boolean success) {
        return success ? ResultUtils.success() : ResultUtils.failure();
    }

    public static ResultObject<?> defaultResult(Integer code) {
        return new ResultObject<>(code);
    }

    public static ResultObject<?> defaultResult(Integer code, String message) {
        return new ResultObject<>(code, message);
    }

    public static <T> ResultObject<T> defaultResult(Integer code, String message, T data) {
        return new ResultObject<>(code, message, data);
    }


}
