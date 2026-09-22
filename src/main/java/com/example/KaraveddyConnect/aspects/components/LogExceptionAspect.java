package com.example.KaraveddyConnect.aspects.components;

import com.example.KaraveddyConnect.aspects.interfaces.LogException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.aop.support.AopUtils;
import org.springframework.stereotype.Component;

/**
 * @author Anuruththan-local
 * @org Smartzi
 * @since 2026-09-22 17:06 PM
 **/
@Aspect
@Slf4j
@Component
@RequiredArgsConstructor
public class LogExceptionAspect {

    private final AspectLayerResolver layerResolver;

    @Around("@annotation(logException)")
    public Object logException(
            ProceedingJoinPoint joinPoint,
            LogException logException
    ) throws Throwable {

        try {
            return joinPoint.proceed();
        } catch (Exception ex) {
            MethodSignature signature = (MethodSignature) joinPoint.getSignature();
            Class<?> targetClass = AopUtils.getTargetClass(joinPoint.getTarget());
            String layer = layerResolver.resolve(targetClass);
            Throwable rootCause = getRootCause(ex);

            log.error(
                    "EXCEPTION|{}|{}|{}|exception={}|rootException={}|message={}|rootMessage={}",
                    layer,
                    targetClass.getSimpleName(),
                    signature.getName(),
                    ex.getClass().getName(),
                    rootCause.getClass().getName(),
                    ex.getMessage(),
                    rootCause.getMessage(),
                    ex
            );

            throw ex;
        }
    }

    private Throwable getRootCause(Throwable throwable) {
        Throwable cause = throwable;

        while (cause.getCause() != null) {
            cause = cause.getCause();
        }

        return cause;
    }
}