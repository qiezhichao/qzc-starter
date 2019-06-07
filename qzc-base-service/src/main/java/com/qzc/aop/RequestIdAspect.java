package com.qzc.aop;

import com.qzc.pojo.Response;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * 请求id切面类
 *
 * @Author: qiezhichao
 * @CreateDate: 2019/5/15 19:57
 */
@Aspect
@Component
@Slf4j
public class RequestIdAspect {

    // 横切点，哪些方法需要被横切
    @Pointcut("execution(public * com..*.*Controller.*(..))")
    public void addControllerRequestId() {
    }

    // 横切点，哪些方法需要被横切
    @Pointcut("execution(public * com..*.*ExceptionHandler.*(..))")
    public void addExceptionHandlerRequestId() {
    }

    @AfterReturning(value = "execution(public * com..*.*Controller.*(..))", returning = "result")
    public void afterControllerReturning(JoinPoint joinPoint, Object result) {
        try {
            RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
            //从获取RequestAttributes中获取HttpServletRequest的信息
            HttpServletRequest request = (HttpServletRequest) requestAttributes.resolveReference(RequestAttributes.REFERENCE_REQUEST);
            String requestId = String.valueOf(request.getAttribute("requestId"));

            Response response = (Response) result;
            response.setRequestId(requestId);
        } catch (Exception e) {
            log.error("AOP after printLog error!", e);
        }
    }

    @AfterReturning(value = "execution(public * com..*.*ExceptionHandler.*(..))", returning = "result")
    public void afterExceptionHandlerReturning(JoinPoint joinPoint, Object result) {
        try {
            RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
            //从获取RequestAttributes中获取HttpServletRequest的信息
            HttpServletRequest request = (HttpServletRequest) requestAttributes.resolveReference(RequestAttributes.REFERENCE_REQUEST);
            String requestId = String.valueOf(request.getAttribute("requestId"));

            Map map = (Map) result;
            map.put("requestId", requestId);
        } catch (Exception e) {
            log.error("AOP after printLog error!", e);
        }
    }
}
