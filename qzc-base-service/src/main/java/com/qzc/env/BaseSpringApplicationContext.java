package com.qzc.env;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationEvent;
import org.springframework.stereotype.Component;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

@Component
@Slf4j
public class BaseSpringApplicationContext implements ServletContextListener, ApplicationContextAware {
    private static ApplicationContext applicationContext;

    public BaseSpringApplicationContext() {
        super();
    }


    @Override
    public void contextInitialized(ServletContextEvent event) {
        applicationContext = WebApplicationContextUtils.getWebApplicationContext(event.getServletContext());
    }

    @Override
    public void contextDestroyed(ServletContextEvent event) {

    }

    @Override
    public void setApplicationContext(ApplicationContext ac) throws BeansException {
        if (applicationContext == null) {
            applicationContext = ac;
        }
    }

    public static <T> T getBean(Class<T> clazz) {
        return applicationContext.getBean(clazz);
    }

    @SuppressWarnings("unchecked")
    public static <T> T getBean(String beanName) {
        return (T) applicationContext.getBean(beanName);
    }

    @SuppressWarnings("unchecked")
    public static <T> T getBeanByClassName(String className) {
        Class<T> clazz = null;

        try {
            clazz = (Class<T>) Class.forName(className);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            log.error("class [{}] not found", className);
        }

        return applicationContext.getBean(clazz);
    }

    public static void publishEvent(ApplicationEvent event) {
        applicationContext.publishEvent(event);
    }

    public static void publishEventAsunc(ApplicationEvent event) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                applicationContext.publishEvent(event);
            }
        }).start();
    }

}
