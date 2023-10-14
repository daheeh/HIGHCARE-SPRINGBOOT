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
    public void executeLoggin(){

    }

    @Around("executeLoggin()")
    public Object logginAround(ProceedingJoinPoint joinPoint)throws Throwable{
        Object proceed = joinPoint.proceed();
        String className = "CLASS: [" + joinPoint.getTarget().getClass().getSimpleName() + "],";
        String methodName = " METHOD: [" + joinPoint.getSignature().getName() + "()],";
        System.out.println(className + methodName + " REQUEST: ");
        if (joinPoint.getArgs().length > 0) {
            Arrays.stream(joinPoint.getArgs()).forEach(System.out::println);
        } else {
            System.out.println("[]");
        }
        System.out.println(className + methodName + " RESPONSE: " + proceed.toString());
        return proceed;
    }
}
