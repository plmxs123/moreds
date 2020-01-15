package com.example.moreds.config;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * @author admin
 * @date 2020/1/9 0009
 */
@Aspect
@Order(1)
@Component
public class DynamicDataSourceAspect {

    @Around("execution(* com.example.moreds.service..*.*(..))")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        SourceTarget annotation = method.getAnnotation(SourceTarget.class);
        String dataSource = "mysql1";
        if (annotation != null && DataSourceHolder.containsDataSource(annotation.value())) {
            dataSource = annotation.value();
        }
        DataSourceHolder.setDataSource(dataSource);
        try {
            return joinPoint.proceed();
        } finally {
            DataSourceHolder.clearDataSource();
        }
    }
}
