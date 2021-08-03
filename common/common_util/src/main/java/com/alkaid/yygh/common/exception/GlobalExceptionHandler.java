package com.alkaid.yygh.common.exception;


import com.alkaid.yygh.common.result.Result;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created with IntelliJ IDEA.2020.2.3
 *
 * @Auther: Alkaid
 * @Date: 2021-08-03 18:19
 * @ClassName GlobalExceptionHandler
 * @Description:
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class) //处理的哪个异常的类
    @ResponseBody  //把结果用json形式输出
    public Result error(Exception e){
        e.printStackTrace();
        return Result.fail();
    }

    @ExceptionHandler(YyghException.class) //处理的哪个异常的类
    @ResponseBody  //把结果用json形式输出
    public Result error(YyghException e){
        e.printStackTrace();
        return Result.fail();
    }
}
