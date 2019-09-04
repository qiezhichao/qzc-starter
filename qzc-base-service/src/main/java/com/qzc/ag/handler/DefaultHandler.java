package com.qzc.ag.handler;

import com.google.common.base.Strings;
import com.qzc.ag.handler.executor.Executor;
import com.qzc.ag.handler.message.Message;
import com.qzc.ag.init.repertory.ServiceRepertory;
import com.qzc.ag.util.JsonUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class DefaultHandler implements Handler {

    @Override
    public void handler(HttpServletRequest req, HttpServletResponse resp) {
        Message message = this.buildMessage(req);
        Object resultObj = this.doService(message);
        this.handlerResult(resp, resultObj);
    }

    /**
     * 构造Message对象
     * @param req
     * @return message对象
     */
    private Message buildMessage(HttpServletRequest req) {
        // String requestHeader = req.getParameter("requestHeader");
        String requestBody = req.getParameter("requestBody");

        Message message = new Message();
        String serviceCode = JsonUtils.getValueFromJson(requestBody, "serviceCode").replaceAll("\"","");
        message.setServiceCode(serviceCode);
        String paramterJson = JsonUtils.getValueFromJson(requestBody, "paramterJson");
        message.setParamterJson(paramterJson);

        if(!message.isValidate()){
            // TODO
            throw new RuntimeException("请求参数错误");
        }

        return message;
    }

    /**
     * 调用服务方法
     * @param message
     * @return Message对象
     */
    private Object doService(Message message) {
        String serviceCode = message.getServiceCode();
        Executor executor = ServiceRepertory.getInstance().findExecutor(serviceCode);
        if(null == executor){
            throw new RuntimeException("请求参数错误");
        }

        Object resultObj = executor.execute(message);

        return resultObj;
    }

    /**
     * 处理服务调用结果
     * @param resp
     * @param resultObj
     */
    private void handlerResult(HttpServletResponse resp, Object resultObj) {
        try {
            resp.setCharacterEncoding("UTF-8");
            resp.setContentType("text/html/json;charset=utf-8");
            resp.setDateHeader("Expires",0);
            resp.setHeader("Cache-Control", "no-cache");
            resp.setHeader("Pragma", "no-cache");

            String jsonStr = JsonUtils.convert2Json(resultObj);
            if(!Strings.isNullOrEmpty(jsonStr)){
                    resp.getWriter().write(jsonStr);
            }
        }catch (IOException e) {
            // TODO
            e.printStackTrace();
        }
    }

}
