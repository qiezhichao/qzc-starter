package com.qzc.message.config;

import com.qzc.util.BaseValidatorUtil;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Data
@Component
public class TXMessageConfig {

    @Value("${tx.message.appid:#{null}}")
    private String appid;

    @Value("${tx.message.appkey:#{null}}")
    private String appkey;

    @Value("${tx.message.templateId:#{null}}")
    private String templateId;

    @Value("${tx.message.smsSign:#{null}}")
    private String smsSign;

    @Value("${tx.message.nationCode:#{null}}")
    private String nationCode;


    public boolean check() {
        return StringUtils.isNotBlank(this.getAppid())
                && BaseValidatorUtil.validateIsDigit(this.getAppid())
                && StringUtils.isNotBlank(this.getAppkey())
                && StringUtils.isNotBlank(this.getTemplateId())
                && BaseValidatorUtil.validateIsDigit(this.getTemplateId())
                && StringUtils.isNotBlank(this.getSmsSign())
                && StringUtils.isNotBlank(this.getNationCode());
    }
}
