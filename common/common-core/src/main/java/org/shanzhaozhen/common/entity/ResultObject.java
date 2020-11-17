package org.shanzhaozhen.common.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.shanzhaozhen.common.enums.JwtErrorConst;
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
    private Integer code;

    @ApiModelProperty(value = "返回的信息", name = "message")
    private String message;

    @ApiModelProperty(value = "返回的数据", name = "data")
    private T data;

    @ApiModelProperty(value = "请求完成的时间", name = "timestamp")
    private long timestamp = System.currentTimeMillis();

    public ResultObject(JwtErrorConst jwtErrorConst) {
        this.code = jwtErrorConst.getCode();
        this.message = jwtErrorConst.getReason();
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
