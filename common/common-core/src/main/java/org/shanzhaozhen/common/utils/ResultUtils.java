package org.shanzhaozhen.common.utils;

import org.shanzhaozhen.common.entity.ResultObject;
import org.shanzhaozhen.common.enums.ResultType;

public class ResultUtils {

    public static ResultObject<?> success() {
        return ResultObject.build(ResultType.SUCCESS, "success", null);
    }

    public static <T> ResultObject<T> success(T data) {
        return ResultObject.build(ResultType.SUCCESS, "success", data);
    }

    public static ResultObject<?> success(String message) {
        return ResultObject.build(ResultType.SUCCESS, message, null);
    }

    public static <T> ResultObject<T> success(String message, T data) {
        return ResultObject.build(ResultType.SUCCESS, message, data);
    }

    public static ResultObject<?> failure() {
        return ResultObject.build(ResultType.FAILURE, "failure", null);
    }

    public static ResultObject<?> failure(String message) {
        return ResultObject.build(ResultType.FAILURE, message, null);
    }

    public static ResultObject<?> failure(String code, String message) {
        return ResultObject.build(code, message, null);
    }

    public static ResultObject<?> defaultResult(boolean success) {
        return success ? ResultUtils.success() : ResultUtils.failure();
    }

    public static ResultObject<?> defaultResult(String code) {
        return ResultObject.build(code, null, null);
    }

    public static ResultObject<?> defaultResult(String code, String message) {
        return ResultObject.build(code, message, null);
    }

    public static <T> ResultObject<T> defaultResult(String code, String message, T data) {
        return ResultObject.build(code, message, data);
    }


}
