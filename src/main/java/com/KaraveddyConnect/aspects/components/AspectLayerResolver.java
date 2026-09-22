package com.KaraveddyConnect.aspects.components;

import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Anuruththan-local
 * @org Smartzi
 * @since 2026-09-22 17:05 PM
 **/
@Component
public class AspectLayerResolver {

    private final Map<Class<?>, String> layerCache = new ConcurrentHashMap<>();

    public String resolve(Class<?> targetClass) {
        if (targetClass == null) {
            return "UNKNOWN";
        }

        return layerCache.computeIfAbsent(targetClass, this::detectLayer);
    }

    private String detectLayer(Class<?> targetClass) {

        if (AnnotationUtils.findAnnotation(targetClass, Repository.class) != null) {
            return "DAO";
        }

        if (AnnotationUtils.findAnnotation(targetClass, Service.class) != null) {
            return "SERVICE";
        }

        if (AnnotationUtils.findAnnotation(targetClass, RestController.class) != null
                || AnnotationUtils.findAnnotation(targetClass, Controller.class) != null) {
            return "CONTROLLER";
        }

        if (AnnotationUtils.findAnnotation(targetClass, Component.class) != null) {
            return "COMPONENT";
        }

        return "UNKNOWN";
    }

}
