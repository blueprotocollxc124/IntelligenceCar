package org.linkworld.handler;

/*
 *@Author  LiuXiangCheng
 *@Since   2021/12/5  18:46
 */

import lombok.extern.slf4j.Slf4j;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.UnauthenticatedException;
import org.apache.shiro.authz.UnauthorizedException;
import org.linkworld.persist.vo.ResultBean;
import org.linkworld.util.ThrowableUtil;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;
import java.util.List;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(value= MethodArgumentNotValidException.class)
    @ResponseBody
    public ResultBean exception(MethodArgumentNotValidException e) {
        // 输出所有的错误信息
        List<ObjectError> errors = e.getBindingResult().getAllErrors();
        return ResultBean.bad().setMessage(errors.get(0).getDefaultMessage());
    }

    @ExceptionHandler(value= Exception.class)
    @ResponseBody
    public ResultBean exception(Exception e) {
        // 输出所有的错误信息
        String errors = "后台发生错误，正在反馈程序员";
        return ResultBean.bad().setMessage(errors);
    }

    @ExceptionHandler({ UnauthenticatedException.class, AuthenticationException.class })
    public Object authorizationException(HttpServletRequest request, HttpServletResponse response) {
        String errors = "用户无权限";
        return ResultBean.bad().setMessage(errors);
    }



}
