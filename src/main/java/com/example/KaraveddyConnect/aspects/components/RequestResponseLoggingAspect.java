package com.example.KaraveddyConnect.aspects.components;

import com.example.KaraveddyConnect.aspects.interfaces.LogRequestResponse;
import com.example.KaraveddyConnect.utils.CommonUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.aop.support.AopUtils;
import org.springframework.stereotype.Component;

import java.lang.reflect.Parameter;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Anuruththan-local
 * @org Smartzi
 * @since 2026-09-22 17:08 PM
 **/
@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class RequestResponseLoggingAspect {

    private final Map<Class<?>, String> layerCache = new ConcurrentHashMap<>();

    private final AspectLayerResolver layerResolver;

    @Around("@annotation(logRequestResponse)")
    public Object logRequestAndResponse(ProceedingJoinPoint joinPoint, LogRequestResponse logRequestResponse) throws Throwable {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Class<?> targetClass = AopUtils.getTargetClass(joinPoint.getTarget());

        String layer = layerResolver.resolve(targetClass);
        String className = targetClass.getSimpleName();
        String methodName = signature.getName();

        if (log.isInfoEnabled()) {
            Map<String, Object> requestParams = getRequestParams(signature, joinPoint.getArgs());

            log.info(
                    "REQUEST|layer={}|class={}|method={}|params={}",
                    layer,
                    className,
                    methodName,
                    CommonUtil.toJSON(requestParams)
            );
        }

        try {
            Object response = joinPoint.proceed();

            if (log.isInfoEnabled()) {
                log.info(
                        "RESPONSE|layer={}|class={}|method={}|response={}",
                        layer,
                        className,
                        methodName,
                        CommonUtil.toJSON(response)
                );
            }

            return response;

        } catch (Exception ex) {
            log.error(
                    "EXCEPTION|layer={}|class={}|method={}|error={}",
                    layer,
                    className,
                    methodName,
                    ex.getMessage(),
                    ex
            );
            throw ex;
        }
    }

    private Map<String, Object> getRequestParams(MethodSignature signature, Object[] args) {
        Map<String, Object> params = new LinkedHashMap<>();

        Parameter[] parameters = signature.getMethod().getParameters();

        for (int i = 0; i < args.length; i++) {
            String paramName = i < parameters.length
                    ? parameters[i].getName()
                    : "arg" + i;

            Object paramValue = args[i];

            params.put(paramName, paramValue);
        }

        return params;
    }
}

