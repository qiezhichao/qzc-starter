package com.qzc.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

/**
 *  日志自动写入方法切面类
 * @Author:         qiezhichao
 * @CreateDate:     2019/5/15 19:57
 */
@Aspect
@Component
@Slf4j
public class LogAspect {

    // 横切点，哪些方法需要被横切
    @Pointcut("execution(public * com..*.*Controller.*(..)) || execution(public * com..*.*ServiceImpl.*(..))")
    public void printLog() {
    }

    @Before("printLog()")
    public void before(JoinPoint joinPoint) {
        try {
            MethodSignature ms = (MethodSignature) joinPoint.getSignature();
            Method method = ms.getMethod();
            Parameter[] parameters = method.getParameters();
            Class<?> clazz = method.getDeclaringClass();

            StringBuilder sb = new StringBuilder();
            sb.append("begin method ")
                    .append(clazz.getSimpleName())
                    .append(".")
                    .append(method.getName())
                    .append("(");
            if (parameters != null && parameters.length > 0) {
                for (int i = 0; i < parameters.length; i++) {
                    sb.append(parameters[i].getName());
                    if (i != parameters.length - 1) {
                        sb.append(", ");
                    }
                }
                sb.append(")");
            }
            log.debug(sb.toString());
        } catch (Exception e) {
            log.error("AOP before printLog error!");
        }
    }

    @After("printLog()")
    public void after(JoinPoint joinPoint) {
        try {
            MethodSignature ms = (MethodSignature) joinPoint.getSignature();
            Method method = ms.getMethod();
            Parameter[] parameters = method.getParameters();
            Class<?> clazz = method.getDeclaringClass();

            StringBuilder sb = new StringBuilder();
            sb.append("finish method ")
                    .append(clazz.getSimpleName())
                    .append(".")
                    .append(method.getName())
                    .append("(");

            if (parameters != null && parameters.length > 0) {
                for (int i = 0; i < parameters.length; i++) {
                    sb.append(parameters[i].getName());
                    if (i != parameters.length - 1) {
                        sb.append(", ");
                    }
                }
            }
            sb.append(")");
            log.debug(sb.toString());
        } catch (Exception e) {
            log.error("AOP after printLog error!");
        }
    }
}
