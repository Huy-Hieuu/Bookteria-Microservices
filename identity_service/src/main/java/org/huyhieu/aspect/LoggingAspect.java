package org.huyhieu.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class LoggingAspect {
    @Before("execution(* org.huyhieu.controller.*.*(..))")
    public void logMethodCall(JoinPoint joinPoint) {
        // Get the method name
        String methodName = joinPoint.getSignature().getName();

        // Get the class name
        String className = joinPoint.getTarget().getClass().getSimpleName();

        // Log the class name and method name
        log.info("Call API: " + className + "." + methodName);
    }
}
