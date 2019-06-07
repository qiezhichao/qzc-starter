package com.qzc.captcha.config;

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
/**
 * tx:
 *   captcha:
 *     ##是否启用服务
 *     open: true
 *     appId: XXXX
 *     appSecretKey: XXXX
 *     verifyUri: https://ssl.captcha.qq.com/ticket/verify
 */
public class TXCaptchaConfig {

    @Value("${tx.captcha.open:#{null}}")
    private String open;

    @Value("${tx.captcha.appId:#{null}}")
    private String appId;

    @Value("${tx.captcha.appSecretKey:#{null}}")
    private String appSecretKey;

    @Value("${tx.captcha.verifyUri:#{null}}")
    private String verifyUri;

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
                && StringUtils.isNotBlank(this.getAppSecretKey())
                && StringUtils.isNotBlank(this.getVerifyUri())) {

            log.debug("腾讯防水墙配置校验成功");
        } else {
            log.error("腾讯防水墙配置校验失败");
            throw new ServiceException("腾讯防水墙配置校验失败");
        }
    }
}
