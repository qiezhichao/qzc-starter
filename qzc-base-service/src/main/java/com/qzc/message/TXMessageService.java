package com.qzc.message;

import com.github.qcloudsms.SmsSingleSender;
import com.github.qcloudsms.SmsSingleSenderResult;
import com.qzc.exception.ServiceException;
import com.qzc.message.config.TXMessageConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 腾讯云短信服务
 *
 * @Author: qiezhichao
 * @CreateDate: 2019/5/12 13:00
 */
@Service
@Slf4j
public class TXMessageService {

    @Autowired
    private TXMessageConfig txMessageConfig;

    /**
     * 发送验证码
     *
     * @Author: qiezhichao
     * @CreateDate: 2019/5/12 13:00
     */
    public void sendVerificationCode(String phoneNum, String verificationCode) {
        log.debug(">>> enter TXMessageService sendVerificationCode(phoneNum, verificationCode), phoneNum=[{}], verificationCode=[{}]",
                phoneNum, verificationCode);

        if (!txMessageConfig.check()) {
            log.error("腾讯云短信配置校验失败");
            throw new ServiceException("腾讯云短信配置校验失败");
        }

        SmsSingleSenderResult result = null;
        try {
            String[] params = {verificationCode};
            SmsSingleSender ssender = new SmsSingleSender(Integer.parseInt(txMessageConfig.getAppId()), txMessageConfig.getAppKey());
            result = ssender.sendWithParam(txMessageConfig.getNationCode(),
                    phoneNum,
                    Integer.parseInt(txMessageConfig.getTemplateId()),
                    params,
                    txMessageConfig.getSmsSign(),
                    "",
                    "");
        } catch (Exception e) {
            log.error("调用腾讯云短信服务异常，异常信息：{}", e.getMessage());
            throw new ServiceException("调用腾讯云短信服务异常");
        }

        if (result == null || result.result != 0) {
            log.error("腾讯云短信服务发送失败, 错误码:{}, 错误消息:{}", result == null ? null : result.result, result == null ? null : result.errMsg);
            throw new ServiceException("腾讯云短信服务发送失败");
        }
    }
}
