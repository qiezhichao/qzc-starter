package com.qzc.jwt.config;

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
//@ConfigurationProperties(prefix = "jwt")
//@Validated
public class JwtConfig {

    @Value("${jwt.open:#{null}}")
    private String open;

    // 过期时长（单位秒）
    @Value("${jwt.expireSecond:#{null}}")
    private String expireSecond;

    @PostConstruct
    public void init() {
        if (StringUtils.equals(this.getOpen(), "true")) {
            this.check();
        }
    }

    //===================================
    private void check() {
        if (StringUtils.isNotBlank(this.getExpireSecond())
                && BaseValidatorUtil.validateIsDigit(this.getExpireSecond())) {

            log.debug("jwt配置对象校验成功");
        } else {
            log.error("jwt配置对象校验失败");
            throw new ServiceException("jwt配置对象校验失败");
        }
    }
}
