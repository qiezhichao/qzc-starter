package com.qzc.ag.handler.executor;

import com.qzc.ag.handler.message.Message;
import com.qzc.ag.util.JsonUtils;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.core.ParameterNameDiscoverer;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

public class DefaultExecutor implements Executor {
    private String serviceCode;

    private Object springBean;

    private Method targetMethod;

    private ParameterNameDiscoverer parameterNameDiscoverer;

    public DefaultExecutor(String serviceCode, Object springBean, Method targetMethod) {
        this.serviceCode = serviceCode;
        this.springBean = springBean;
        this.targetMethod = targetMethod;
        parameterNameDiscoverer = new LocalVariableTableParameterNameDiscoverer();
    }

    public String getServiceCode() {
        return serviceCode;
    }

    public Object getSpringBean() {
        return springBean;
    }

    public Method getTargetMethod() {
        return targetMethod;
    }


    @Override
    public Object execute(Message message) {
        String paramterJson = message.getParamterJson();
        Object[] args = this.buildParams(paramterJson);

        Object result = null;
        try {
            result = targetMethod.invoke(springBean, args);
        } catch (IllegalAccessException e) {
            // TODO
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            // TODO
            e.printStackTrace();
        }

        return result;
    }

    private Object[] buildParams(String paramterJson) {
        Map<String, Object> map = JsonUtils.convert2Map(paramterJson);
        if (null == map) {
            map = new HashMap<>();
        }

        List<String> parameterNameList = Arrays.asList(parameterNameDiscoverer.getParameterNames(targetMethod));
        for (Map.Entry entry : map.entrySet()) {
            if (!parameterNameList.contains(entry.getKey())) {
                throw new RuntimeException("参数不存在");
            }
        }

        Class<?>[] parameterTypeArr = targetMethod.getParameterTypes();
        Object[] args = new Object[parameterTypeArr.length];
        for (int i = 0; i < parameterTypeArr.length; i++) {
            if (map.containsKey(parameterNameList.get(i))) {
                args[i] = this.convert2Object(map.get(parameterNameList.get(i)), parameterTypeArr[i]);
            } else {
                args[i] = null;
            }
        }
        return args;
    }

    private Object convert2Object(Object value, Class<?> clazz) {
        Object result;
        if (null == value) {
            return null;
        } else if (Integer.class.equals(clazz) || int.class.equals(clazz)) {
            result = Integer.parseInt(value.toString());
        } else if (Long.class.equals(clazz) || long.class.equals(clazz)) {
            result = Long.parseLong(value.toString());
        } else if (Float.class.equals(clazz) || float.class.equals(clazz)) {
            result = Float.parseFloat(value.toString());
        } else if (Double.class.equals(clazz) || double.class.equals(clazz)) {
            result = Double.parseDouble(value.toString());
        } else if (Boolean.class.equals(clazz) || boolean.class.equals(clazz)) {
            if (value.toString().equals("true")) {
                result = true;
            } else if (value.toString().equals("false")) {
                result = false;
            } else {
                throw new IllegalArgumentException("参数类型错误");
            }
        } else if (Short.class.equals(clazz) || short.class.equals(clazz)) {
            throw new IllegalArgumentException("参数类型错误");
        } else if (Byte.class.equals(clazz) || byte.class.equals(clazz)) {
            throw new IllegalArgumentException("参数类型错误");
        } else if (Character.class.equals(clazz) || char.class.equals(clazz)) {
            throw new IllegalArgumentException("参数类型错误");
        } else if (String.class.equals(clazz)) {
            if (value instanceof String) {
                result = value.toString();
            } else {
                throw new IllegalArgumentException("转换类型需要为字符串");
            }
        } else if (Date.class.equals(clazz)) {
            if (value.toString().matches("[0-9]+")) {
                result = new Date(Long.parseLong(value.toString()));
            } else {
                throw new IllegalArgumentException("日期必须是长整型的时间戳");
            }
        } else {
            result = JsonUtils.convert2Object(value.toString(), clazz);
        }

        return result;
    }
}
