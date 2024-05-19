package com.example.demo.price;

import com.example.demo.service.AuthenticationService;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class PriceAop {
    AuthenticationService authenticationService;

    public PriceAop(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @Pointcut("within(com.example.demo.MyCommands)")
    public void shell(){

    }

    @Pointcut("execution(* *city(..)) || execution(* *sector(..)) || execution(* *price(..)) || execution(* *billTotal(..))")
    public void priceMethod(){

    }

    @Around("(shell() && priceMethod())")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        if(authenticationService.getCurrentAccount() == null){
            throw new RuntimeException("need to login first");
        } else {
            log.info("----- {} {}.{}({}) ----->", new Object[]{this.authenticationService.getCurrentAccount().getName(), joinPoint.getTarget().getClass(), joinPoint.getSignature().getName(), joinPoint.getArgs()});
            Object object = joinPoint.proceed();
            log.info("<----- {} {}.{}({}) -----", new Object[]{this.authenticationService.getCurrentAccount().getName(), joinPoint.getTarget().getClass(), joinPoint.getSignature().getName(), object.toString()});
            return object;
        }
    }


}
