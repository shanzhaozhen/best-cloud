package org.shanzhaozhen.authorize.exception;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.MyBatisSystemException;
import org.shanzhaozhen.common.core.constant.ResultType;
import org.shanzhaozhen.common.core.result.R;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLException;
import java.sql.SQLSyntaxErrorException;
import java.util.Objects;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /**
     * 未知异常
     * @param e
     * @return
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public R<?> handleIllegalArgumentException(Exception e) {
        log.warn("未知错误：{}", e.getMessage());
        e.printStackTrace();
        return new R<>().setCode(ResultType.FAILURE).setMessage("未知异常错误").setData(e.getMessage());
    }

    /**
     * 断言事件监听
     * @param e
     * @return
     */
    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public R<?> handleIllegalArgumentException(IllegalArgumentException e) {
        log.warn("未知错误：{}", e.getMessage());
        e.printStackTrace();
        return new R<>().setCode(ResultType.FAILURE).setMessage(e.getMessage());
    }

    /**
     * 监听表单验证错误信息
     * @param e
     * @return
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public R<?> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.warn("监听表单验证错误：{}", e.getMessage());
        e.printStackTrace();
        return new R<>().setCode(ResultType.FAILURE).setMessage(Objects.requireNonNull(e.getBindingResult().getFieldError()).getDefaultMessage());
    }

    /**
     * 监听SQL执行错误信息
     * @param e
     * @return
     */
    @ExceptionHandler({SQLException.class, SQLSyntaxErrorException.class, MyBatisSystemException.class, DataIntegrityViolationException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public R<?> handleAboutSQLException(Exception e) {
        log.warn("SQL执行错误：{}", e.getMessage());
        e.printStackTrace();
        return new R<>().setCode(ResultType.FAILURE).setMessage("执行失败").setData(e.getMessage());
    }

}
