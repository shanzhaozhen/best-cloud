package org.shanzhaozhen.common.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.shanzhaozhen.common.constant.IResultCode;
import org.shanzhaozhen.common.constant.ResultType;
import org.shanzhaozhen.common.constant.enums.JwtErrorConst;

import java.util.function.Function;
import java.util.function.Supplier;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@ApiModel(description = "API公共返回对象")
@Slf4j
public class R<T> {

    @ApiModelProperty(value = "业务状态码", name = "code")
    private Integer code;

    @ApiModelProperty(value = "返回的信息", name = "message")
    private String message;

    @ApiModelProperty(value = "返回的数据", name = "data")
    private T data;

    @ApiModelProperty(value = "请求完成的时间", name = "timestamp")
    private long timestamp = System.currentTimeMillis();

    public R(Integer code) {
        this.code = code;
    }

    public R(String msg) {
        this.message = msg;
    }

    public R(Integer code, String msg) {
        this.code = code;
        this.message = msg;
    }

    public R(Integer code, T data) {
        this.code = code;
        this.data = data;
    }

    public R(Integer code, String msg, T data) {
        this.code = code;
        this.message = msg;
        this.data = data;
    }

    public R(JwtErrorConst jwtErrorConst) {
        this.code = jwtErrorConst.getCode();
        this.message = jwtErrorConst.getReason();
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

    public static <T> R<T> ok() {
        return new R<>(ResultType.SUCCESS);
    }

    public static <T> R<T> ok(T data) {
        return new R<>(ResultType.SUCCESS, data);
    }

    public static <T> R<T> ok(T data, String message) {
        return new R<>(ResultType.SUCCESS, message, data);
    }

    public static <T> R<T> failed() {
        return new R<>(ResultType.FAILURE);
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
