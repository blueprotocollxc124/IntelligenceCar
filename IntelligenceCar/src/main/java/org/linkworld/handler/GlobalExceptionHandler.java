package org.linkworld.handler;

/*
 *@Author  LiuXiangCheng
 *@Since   2021/12/5  18:46
 */

import lombok.extern.slf4j.Slf4j;

import org.linkworld.persist.vo.ResultBean;
import org.linkworld.util.ThrowableUtil;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

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

}
