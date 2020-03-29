package com.spring.api.swagger.config;

import com.spring.api.swagger.common.ErrorResponseEntity;
import com.spring.api.swagger.common.ExceptionType;
import com.spring.api.swagger.exception.CustomException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author WU, Homer
 * @version 1.0
 * @date 2020/3/26 8:55
 * @description
 */
@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    //    捕获CustomException异常
    @ExceptionHandler(CustomException.class)
    public ErrorResponseEntity customExceptionHandler(HttpServletRequest request, final Exception e,
                                                      HttpServletResponse response){
        response.setStatus(HttpStatus.BAD_REQUEST.value());
        CustomException exception = (CustomException) e;
        return new ErrorResponseEntity(exception.getCode(),exception.getMessage());
    }

//    捕获RuntimeException异常
    @ExceptionHandler(RuntimeException.class)
    public ErrorResponseEntity runtimeExceptionHandler(HttpServletRequest request, final Exception e,
                                                       HttpServletResponse response){
        response.setStatus(HttpStatus.BAD_REQUEST.value());
        CustomException exception = (CustomException) e;
        return new ErrorResponseEntity(ExceptionType.SERVER_ERROR.getCode(),
                ExceptionType.SERVER_ERROR.getMsg());
    }

//通用的接口映射异常处理方法
    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers,
                                                             HttpStatus status, WebRequest request){
        if(ex instanceof MethodArgumentNotValidException){
            MethodArgumentNotValidException exception=(MethodArgumentNotValidException) ex;
            return new ResponseEntity<>(new ErrorResponseEntity(status.value(),
                    exception.getBindingResult().getAllErrors().get(0).getDefaultMessage()),status);
        }
        if (ex instanceof MethodArgumentTypeMismatchException){
            MethodArgumentTypeMismatchException exception = (MethodArgumentTypeMismatchException) ex;
            logger.error("参数错误，方法："+exception.getParameter().getMethod().getName()+",参数："+exception.getName()
                    +",信息："+exception.getLocalizedMessage());
            return new ResponseEntity<>(new ErrorResponseEntity(status.value(),"参数错误"),status);
        }
        return new ResponseEntity<>(new ErrorResponseEntity(status.value(),"参数错误"),status);
    }
}
