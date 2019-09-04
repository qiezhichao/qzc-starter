package com.qzc.ag.handler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface Handler {
    void handler(HttpServletRequest req, HttpServletResponse resp);
}
