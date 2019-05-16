package com.qzc.captcha;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.qzc.captcha.config.TXCaptchaConfig;
import com.qzc.captcha.pojo.TXCaptchaBean;
import com.qzc.exception.ServiceException;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URLEncoder;

/**
 * 腾讯防水墙服务
 *
 * @Author: qiezhichao
 * @CreateDate: 2019/5/16 19:37
 */
@Service
@Slf4j
public class TXCaptchaService {

    @Autowired
    private TXCaptchaConfig txCaptchaConfig;

    /**
     * 获取校验防水墙票据结果
     *
     * @Author: qiezhichao
     * @CreateDate: 2019/5/16 19:38
     */
    public TXCaptchaBean getVerifyTicketResult(String ticket, String rand, String userIp) {

        if (!txCaptchaConfig.check()) {
            log.error("腾讯防水墙配置校验失败");
            throw new ServiceException("腾讯防水墙配置校验失败");
        }

        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpGet httpGet;
        CloseableHttpResponse response = null;
        try {
            String verifyUri = txCaptchaConfig.getVerifyUri();
            verifyUri = verifyUri + "?aid=%s&AppSecretKey=%s&Ticket=%s&Randstr=%s&UserIP=%s";

            httpGet = new HttpGet(String.format(verifyUri,
                    txCaptchaConfig.getAppId(),
                    txCaptchaConfig.getAppSecretKey(),
                    URLEncoder.encode(ticket, "UTF-8"),
                    URLEncoder.encode(rand, "UTF-8"),
                    URLEncoder.encode(userIp, "UTF-8")
            ));

            response = httpclient.execute(httpGet);
            HttpEntity entity = response.getEntity();

            if (entity != null) {
                String res = EntityUtils.toString(entity);

                JSONObject result = JSON.parseObject(res);
                TXCaptchaBean txCaptchaBean = new TXCaptchaBean();

                // 返回码
                int code = result.getInteger("response");
                txCaptchaBean.setResponse(code);
                txCaptchaBean.setIsVerifySuccess(code == 1);

                // 恶意等级
                int evilLevel = result.getInteger("evil_level");
                txCaptchaBean.setEvilLevel(evilLevel);
                // 错误信息
                String errMsg = result.getString("err_msg");
                txCaptchaBean.setErrMsg(errMsg);

                return txCaptchaBean;
            }
        } catch (Exception e) {
            log.error("腾讯防水墙服务异常，异常信息：{}", e.getMessage());
            throw new ServiceException("腾讯防水墙服务异常");
        } finally {
            if (response != null) {
                try {
                    response.close();
                } catch (IOException e) {
                    log.error(e.getMessage());
                }
            }
        }

        return null;
    }
}
