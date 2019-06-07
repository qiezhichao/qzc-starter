package com.qzc.message.config;

import com.qzc.exception.ServiceException;
import com.qzc.util.BaseValidatorUtil;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Data
@Component
@Slf4j
public class TXMessageConfig {

    @Value("${tx.message.open:#{null}}")
    private String open;

    @Value("${tx.message.appId:#{null}}")
    private String appId;

    @Value("${tx.message.appKey:#{null}}")
    private String appKey;

    @Value("${tx.message.templateId:#{null}}")
    private String templateId;

    @Value("${tx.message.smsSign:#{null}}")
    private String smsSign;

    @Value("${tx.message.nationCode:#{null}}")
    private String nationCode;

    @PostConstruct
    public void init() {
        if (StringUtils.equals(this.getOpen(), "true")) {
            this.check();
        }
    }

    //========================================
    private void check() {
        if (StringUtils.isNotBlank(this.getAppId())
                && BaseValidatorUtil.validateIsDigit(this.getAppId())
                && StringUtils.isNotBlank(this.getAppKey())
                && StringUtils.isNotBlank(this.getTemplateId())
                && BaseValidatorUtil.validateIsDigit(this.getTemplateId())
                && StringUtils.isNotBlank(this.getSmsSign())
                && StringUtils.isNotBlank(this.getNationCode())) {

            log.debug("腾讯云短信服务配置校验成功");
        } else {
            log.error("腾讯云短信服务配置校验失败");
            throw new ServiceException("腾讯云短信服务配置校验失败");
        }
    }
}
