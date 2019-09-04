package com.qzc.ag.init.repertory;

import com.qzc.ag.annotation.ServiceMapping;
import com.qzc.ag.handler.executor.DefaultExecutor;
import com.qzc.ag.handler.executor.Executor;
import org.springframework.context.ApplicationContext;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class ServiceRepertory {
    private static ServiceRepertory serviceRepertory;

    private final Map<String, Executor> serviceMap = new HashMap<>();

    private ServiceRepertory() {}

    /**
     * 获取ServiceRepertory单例对象
     * @return serviceRepertory
     */
    public static ServiceRepertory getInstance(){
        if(null == serviceRepertory){
            synchronized (ServiceRepertory.class){
                if(null == serviceRepertory){
                    serviceRepertory = new ServiceRepertory();
                }
            }
        }
        return serviceRepertory;
    }

    public void loadServiceFromSpringBean(ApplicationContext applicationContext) {
        String[] beanNames = applicationContext.getBeanDefinitionNames();

        Class<?> classType;

        for (String beanName : beanNames) {
            classType = applicationContext.getType(beanName);

            for (Method method:classType.getDeclaredMethods()){
                ServiceMapping serviceMapping = method.getAnnotation(ServiceMapping.class);
                if(null != serviceMapping){
                    Object bean = applicationContext.getBean(beanName);
                    this.addServiceItem(serviceMapping.value(), bean, method);
                }
            }
        }
    }

    public Executor findExecutor(String serviceCode){
        return serviceMap.get(serviceCode);
    }

    private void addServiceItem(String value, Object bean, Method method) {
        Executor executor = new DefaultExecutor(value, bean, method);
        serviceMap.put(value,executor);
    }
}
