package com.qzc.aop;

import com.qzc.annotation.ApplicationConfigCheck;
import com.qzc.env.BaseSpringApplicationContext;
import com.qzc.exception.ServiceException;
import com.qzc.pojo.ApplicationCheckConfig;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

/**
 * Application配置(Application.yml)服务启用校验切面类
 *
 * @Author: qiezhichao
 * @CreateDate: 2019/6/7 10:44
 */
@Aspect
@Component
@Slf4j
public class ApplicationConfigCheckAspect {

    // 横切点，哪些方法需要被横切
    @Pointcut("execution(public * com.qzc..*(..))")
    public void openCheck() {
    }

    @Before("openCheck()")
    public void before(JoinPoint joinPoint) {

        MethodSignature ms = (MethodSignature) joinPoint.getSignature();
        Class clazz = ms.getMethod().getDeclaringClass();

        if (clazz.isAnnotationPresent(ApplicationConfigCheck.class)) {
            ApplicationConfigCheck annotation = (ApplicationConfigCheck) clazz.getAnnotation(ApplicationConfigCheck.class);

            Class<? extends ApplicationCheckConfig> beanClass = annotation.configClass();

            ApplicationCheckConfig config = BaseSpringApplicationContext.getBean(beanClass);

            if (!config.isOpen()) {
                String configServiceName = annotation.configServiceName();
                log.error("[{}]未进行配置，无法启用", configServiceName);
                throw new ServiceException(configServiceName + " 未进行配置，无法启用");
            }
        }


    }
}
