package com.qzc.ag.init;

import com.qzc.ag.init.repertory.ServiceRepertory;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class InitListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        // 获取容器与相关的Service对象
        ApplicationContext ac = WebApplicationContextUtils.getWebApplicationContext(servletContextEvent.getServletContext());

        // 启动时加载service
        ServiceRepertory.getInstance().loadServiceFromSpringBean(ac);
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }
}
