package com.qzc.ag.servlet;

import com.qzc.ag.handler.DefaultHandler;
import com.qzc.ag.handler.Handler;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AgDispatcherServlet extends HttpServlet{

    private Handler handler = new DefaultHandler();


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        this.doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        handler.handler(req, resp);
    }

}
