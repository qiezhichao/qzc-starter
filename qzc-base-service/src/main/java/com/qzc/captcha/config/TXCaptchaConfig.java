package com.qzc.captcha.config;

import com.qzc.util.BaseValidatorUtil;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Data
@Component
public class TXCaptchaConfig {

    @Value("${tx.captcha.appId:#{null}}")
    private String appId;

    @Value("${tx.captcha.appSecretKey:#{null}}")
    private String appSecretKey;

    @Value("${tx.captcha.verifyUri:#{null}}")
    private String verifyUri;

    public boolean check() {
        return StringUtils.isNotBlank(this.getAppId())
                && BaseValidatorUtil.validateIsDigit(this.getAppId())
                && StringUtils.isNotBlank(this.getAppSecretKey())
                && StringUtils.isNotBlank(this.getVerifyUri());
    }
}
