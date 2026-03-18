package ru.itis.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class PerformanceMonitoringAspect {

    private static final long SLOW_METHOD_THRESHOLD_MS = 1000;

    @Pointcut("execution(public * ru.itis.controller..*(..)) || " +
              "execution(public * ru.itis.service..*(..)) || " +
              "execution(public * ru.itis.repository..*(..))")
    public void allPublicMethods() {
    }

    @Around("allPublicMethods()")
    public Object monitorPerformance(ProceedingJoinPoint joinPoint) throws Throwable {
        String className = joinPoint.getSignature().getDeclaringType().getSimpleName();
        String methodName = joinPoint.getSignature().getName();
        String fullMethodName = className + "." + methodName + "()";

        long startTime = System.currentTimeMillis();

        try {
            Object result = joinPoint.proceed();
            long executionTime = System.currentTimeMillis() - startTime;

            if (executionTime > SLOW_METHOD_THRESHOLD_MS) {
                log.warn("SLOW METHOD: {} took {}ms (threshold: {}ms)",
                        fullMethodName, executionTime, SLOW_METHOD_THRESHOLD_MS);
            } else {
                log.debug("{} executed in {}ms", fullMethodName, executionTime);
            }

            return result;
        } catch (Throwable ex) {
            long executionTime = System.currentTimeMillis() - startTime;
            log.error("{} failed after {}ms with exception: {}",
                    fullMethodName, executionTime, ex.getMessage());
            throw ex;
        }
    }
}