package com.highright.highcare.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
@Slf4j
public class ControllerAspect {

    @Pointcut("execution(* com.highright.highcare.*.controller.*.*(..))")
    public void executeLogging(){

    }

    @Around("executeLogging()")
    public Object loggingAround(ProceedingJoinPoint joinPoint) throws Throwable {
        Object proceed = joinPoint.proceed();
        String className = "CLASS: [" + joinPoint.getTarget().getClass().getSimpleName() + "],";
        String methodName = " METHOD: [" + joinPoint.getSignature().getName() + "()],";

        // DEBUG 레벨 로그
        if (log.isDebugEnabled()) {
            log.debug(className + methodName + " REQUEST: ");
            if (joinPoint.getArgs().length > 0) {
                Arrays.stream(joinPoint.getArgs()).forEach(arg -> log.debug(arg.toString()));
            } else {
                log.debug("[]");
            }
        }

        // INFO 레벨 로그
        if (log.isInfoEnabled()) {
            log.info(className + methodName + " REQUEST: ");
            if (joinPoint.getArgs().length > 0) {
                Arrays.stream(joinPoint.getArgs()).forEach(arg -> log.info(arg.toString()));
            } else {
                log.info("[]");
            }
        }

        // WARN 레벨 로그
        if (log.isWarnEnabled()) {
            log.warn(className + methodName + " REQUEST: ");
            if (joinPoint.getArgs().length > 0) {
                Arrays.stream(joinPoint.getArgs()).forEach(arg -> log.warn(arg.toString()));
            } else {
                log.warn("[]");
            }
        }

        // ERROR 레벨 로그
        if (log.isErrorEnabled()) {
            log.error(className + methodName + " REQUEST: ");
            if (joinPoint.getArgs().length > 0) {
                Arrays.stream(joinPoint.getArgs()).forEach(arg -> log.error(arg.toString()));
            } else {
                log.error("[]");
            }
        }

        // DEBUG 레벨 로그
        if (log.isDebugEnabled()) {
            log.debug(className + methodName + " RESPONSE: " + proceed.toString());
        }

        return proceed;
    }
}
