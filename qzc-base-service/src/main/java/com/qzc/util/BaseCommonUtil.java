package com.qzc.util;

import com.qzc.exception.ServiceException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * 通用工具类
 *
 * @Author: qiezhichao
 * @CreateDate: 2019/5/16 23:04
 */
@Slf4j
public class BaseCommonUtil {

    /**
     * 获取ip地址
     *
     * @Author: qiezhichao
     * @CreateDate: 2019/5/16 23:04
     */
    public static String getIpAddr(HttpServletRequest request) {
        String ipAddress = null;
        try {
            ipAddress = request.getHeader("x-forwarded-for");
            if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
                ipAddress = request.getHeader("Proxy-Client-IP");
            }
            if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
                ipAddress = request.getHeader("WL-Proxy-Client-IP");
            }
            if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
                ipAddress = request.getRemoteAddr();
                if (ipAddress.equals("127.0.0.1")) {
                    // 根据网卡取本机配置的IP
                    InetAddress inet = null;
                    try {
                        inet = InetAddress.getLocalHost();
                        ipAddress = inet.getHostAddress();
                    } catch (UnknownHostException e) {
                        e.printStackTrace();
                    }

                }
            }
            // 对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割, "***.***.***.***".length() = 15
            if (ipAddress != null && ipAddress.length() > 15) {
                if (ipAddress.indexOf(",") > 0) {
                    ipAddress = ipAddress.substring(0, ipAddress.indexOf(","));
                }
            }
        } catch (Exception e) {
            log.error("获取用户ip地址异常, {}", e.getMessage());
            throw new ServiceException("获取用户ip地址异常");
        }

        if (StringUtils.isBlank(ipAddress)) {
            log.error("无法获取用户ip地址, ipAddress=[{}]", ipAddress);
            throw new ServiceException("无法获取用户ip地址");
        }

        return ipAddress;
    }
}
