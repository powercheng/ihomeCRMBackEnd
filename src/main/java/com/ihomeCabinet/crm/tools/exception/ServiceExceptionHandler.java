package com.ihomeCabinet.crm.tools.exception;

import com.auth0.jwt.exceptions.*;
import com.ihomeCabinet.crm.tools.response.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class ServiceExceptionHandler {



    /**
     * 处理token异常
     */
    @ExceptionHandler({JWTVerificationException.class, SignatureVerificationException.class, AlgorithmMismatchException.class, JWTDecodeException.class})
    public Result tokenErrorException(Exception e) {
        Result result = Result.fail(401,"invalid token");
        log.error("invalid token");
        log.error(e.getMessage());
        e.printStackTrace();
        return result;
    }

    /**
     * 处理所有RuntimeException异常
     */
    @ExceptionHandler({RuntimeException.class})
    public Result allException(RuntimeException e) {
        Result result = Result.fail("system error");
        log.error(e.getMessage());
        e.printStackTrace();
        return result;
    }

    /**
     * 处理所有Exception异常
     */
    @ExceptionHandler({Exception.class})
    public Result ResultallException(Exception e) {
        Result result = Result.fail("system error");
        log.error(e.getMessage());
        e.printStackTrace();
        return result;
    }

}