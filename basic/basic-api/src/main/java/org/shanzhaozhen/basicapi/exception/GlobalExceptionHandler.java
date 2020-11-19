package org.shanzhaozhen.basicapi.exception;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.MyBatisSystemException;
import org.shanzhaozhen.common.entity.ResultObject;
import org.shanzhaozhen.common.enums.CodeConst;
import org.shanzhaozhen.common.enums.ResultType;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.IOException;
import java.util.Objects;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /**
     * 未知异常
     * @param e
     * @throws IOException
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResultObject<String> handleIllegalArgumentException(Exception e) {
        return ResultObject.build(CodeConst.UNKNOWN_ERROR, e);
    }

    /**
     * 断言事件监听
     * @param e
     * @throws IOException
     */
    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResultObject<?> handleIllegalArgumentException(IllegalArgumentException e) {
        return new ResultObject<>().setCode(ResultType.FAILURE).setMessage(e.getMessage());
    }

    /**
     * 监听表单验证错误信息
     * @param e 数据校验事件
     * @return
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResultObject<?> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        return ResultObject.build(ResultType.FAILURE, Objects.requireNonNull(e.getBindingResult().getFieldError()).getDefaultMessage(), null);
//        return new ResultObject<>(ResultType.FAILURE, Objects.requireNonNull(e.getBindingResult().getFieldError()).getDefaultMessage(), null, null);
    }

    /**
     * 监听SQL执行错误信息
     * @param e 数据校验事件
     * @return
     */
    @ExceptionHandler(MyBatisSystemException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResultObject<?> handleMyBatisSystemException(MyBatisSystemException e) {
        return new ResultObject<>().setCode(ResultType.FAILURE).setMessage("执行失败").setData(e.getMessage());
    }

}
