package org.shanzhaozhen.basicapi.exception;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.MyBatisSystemException;
import org.shanzhaozhen.basiccommon.enums.sys.ResultType;
import org.shanzhaozhen.basiccommon.vo.ResultObject;
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
    public ResultObject<?> handleIllegalArgumentException(Exception e) {
        return new ResultObject<>().setCode(ResultType.FAILURE).setMessage("未知异常错误").setData(e.getMessage());
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
        return new ResultObject<>().setCode(ResultType.FAILURE).setMessage(Objects.requireNonNull(e.getBindingResult().getFieldError()).getDefaultMessage());
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
