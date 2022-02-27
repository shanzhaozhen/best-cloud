package org.shanzhaozhen.common.core.result;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.shanzhaozhen.common.core.constant.ResultType;

import java.util.function.Function;
import java.util.function.Supplier;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "API公共返回对象")
@Slf4j
public class R<T> {

    @Schema(description = "业务状态码", name = "code")
    private String code;

    @Schema(description = "返回的信息", name = "message")
    private String message;

    @Schema(description = "返回的数据", name = "data")
    private T data;

    @Schema(description = "请求完成的时间", name = "timestamp")
    private long timestamp = System.currentTimeMillis();

    public R(String code, String msg) {
        this.code = code;
        this.message = msg;
    }

    public R(String code, T data) {
        this.code = code;
        this.data = data;
    }

    public R(String code, String msg, T data) {
        this.code = code;
        this.message = msg;
        this.data = data;
    }

    public R(IResultCode resultCode) {
        this.code = resultCode.getCode();
        this.message = resultCode.getMessage();
    }

    public static <T> R<T> build(Supplier<T> s) {
        R<T> result = new R<>();
        T data = s.get();
        result.setData(data);
        result.setCode(ResultType.SUCCESS);
        return result;
    }

    public static <T> R<T> build(Function<R<T>, T> s) {
        R<T> result = new R<>();
        T data = s.apply(result);
        if (result.getMessage() == null) {
            result.setData(data);
            result.setCode(ResultType.SUCCESS);
        }
        return result;
    }

    public static <T> R<T> build(IResultCode resultCode) {
        return new R<>(resultCode);
    }

    public static <T> R<T> ok() {
        return new R<>(ResultType.SUCCESS, null);
    }

    public static <T> R<T> ok(T data) {
        return new R<>(ResultType.SUCCESS, data);
    }

    public static <T> R<T> ok(T data, String message) {
        return new R<>(ResultType.SUCCESS, message, data);
    }

    public static <T> R<T> failed() {
        return new R<>(ResultType.FAILURE, null);
    }

    public static <T> R<T> failed(String message) {
        return new R<>(ResultType.FAILURE, message);
    }

    public static <T> R<T> failed(T data) {
        return new R<>(ResultType.FAILURE, data);
    }

    public static <T> R<T> failed(T data, String message) {
        return new R<>(ResultType.FAILURE, message, data);
    }

}
