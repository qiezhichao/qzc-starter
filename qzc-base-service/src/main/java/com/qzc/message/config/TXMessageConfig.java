package com.qzc.message.config;

import com.qzc.util.BaseValidatorUtil;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Data
@Component
public class TXMessageConfig {

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


    public boolean check() {
        return StringUtils.isNotBlank(this.getAppId())
                && BaseValidatorUtil.validateIsDigit(this.getAppId())
                && StringUtils.isNotBlank(this.getAppKey())
                && StringUtils.isNotBlank(this.getTemplateId())
                && BaseValidatorUtil.validateIsDigit(this.getTemplateId())
                && StringUtils.isNotBlank(this.getSmsSign())
                && StringUtils.isNotBlank(this.getNationCode());
    }
}
