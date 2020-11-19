package org.shanzhaozhen.common.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.shanzhaozhen.common.enums.IResultCode;
import org.shanzhaozhen.common.enums.ResultType;

import java.util.function.Function;
import java.util.function.Supplier;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@ApiModel(description = "API公共返回对象")
@Slf4j
public class ResultObject<T> {

    @ApiModelProperty(value = "业务状态码", name = "code")
    private String code;

    @ApiModelProperty(value = "返回的信息", name = "message")
    private String message;

    @ApiModelProperty(value = "返回的数据", name = "data")
    private T data;

    @ApiModelProperty(value = "请求完成的时间", name = "timestamp")
    private long timestamp = System.currentTimeMillis();

    public static <T> ResultObject<T> build(IResultCode resultCode) {
        return build(resultCode.getCode(), resultCode.getMessage(), null);
    }

    public static ResultObject<String> build(IResultCode resultCode, Exception e) {
        return build(resultCode.getCode(), resultCode.getMessage(), e.getMessage());
    }

    public static <T> ResultObject<T> build(String code, String message, T data) {
        ResultObject<T> result = new ResultObject<>();
        result.setCode(code);
        result.setMessage(message);
        result.setData(data);
        return result;
    }

    public static <T> ResultObject<T> build(Supplier<T> s) {
        ResultObject<T> result = new ResultObject<>();
        T data = s.get();
        result.setData(data);
        result.setCode(ResultType.SUCCESS);
        return result;
    }

    public static <T> ResultObject<T> build(Function<ResultObject<T>, T> s) {
        ResultObject<T> result = new ResultObject<>();
        T data = s.apply(result);
        if (result.getMessage() == null) {
            result.setData(data);
            result.setCode(ResultType.SUCCESS);
        }
        return result;
    }

}
