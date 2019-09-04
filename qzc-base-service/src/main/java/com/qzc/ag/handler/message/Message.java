package com.qzc.ag.handler.message;

import com.google.common.base.Strings;

public class Message {

    private String serviceCode;
    private String paramterJson;

    public String getServiceCode() {
        return serviceCode;
    }

    public void setServiceCode(String serviceCode) {
        this.serviceCode = serviceCode;
    }

    public String getParamterJson() {
        return paramterJson;
    }

    public void setParamterJson(String paramterJson) {
        this.paramterJson = paramterJson;
    }

    /**
     * 判断对象是否有效
     * @return true/false
     */
    public boolean isValidate(){
        return  !Strings.isNullOrEmpty(serviceCode) &&
                !Strings.isNullOrEmpty(paramterJson);
    }
}
