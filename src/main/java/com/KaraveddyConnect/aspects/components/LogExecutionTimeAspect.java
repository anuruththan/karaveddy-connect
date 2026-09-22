package com.KaraveddyConnect.aspects.components;

import com.KaraveddyConnect.aspects.interfaces.LogExecutionTime;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

/**
 * @author Anuruththan-local
 * @org Smartzi
 * @since 2026-09-22 17:07 PM
 **/
@Aspect
@Slf4j
@Component
public class LogExecutionTimeAspect {
    @Around("@annotation(logExecutionTime)")
    public Object logTime(
            ProceedingJoinPoint joinPoint,
            LogExecutionTime logExecutionTime
    ) throws Throwable {
        String methodName = joinPoint.getSignature().getName();

        long startTime = System.currentTimeMillis();

        try {
            return joinPoint.proceed();
        } finally {
            long endTime = System.currentTimeMillis();

            log.info("Time taken to execute function {} is: {} ms",
                    methodName,
                    endTime - startTime
            );
        }
    }
}
