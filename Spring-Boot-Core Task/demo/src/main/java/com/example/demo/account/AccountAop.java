package com.example.demo.account;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class AccountAop {

    @Pointcut("execution(* com.example.demo.service..login(..)) || execution(* com.example.demo.service..logout(..))")
    public void cut(){

    }

    @Before("cut()")
    public Object before(JoinPoint joinPoint){
        log.info("{}({})", joinPoint.getSignature().getName(), joinPoint.getArgs());
        return joinPoint;
    }
}
