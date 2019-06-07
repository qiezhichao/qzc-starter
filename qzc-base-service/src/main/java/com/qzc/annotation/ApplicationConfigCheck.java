package com.qzc.annotation;

import com.qzc.pojo.ApplicationCheckConfig;

import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ApplicationConfigCheck {

    Class<? extends ApplicationCheckConfig> configClass();

    String configServiceName();

}
