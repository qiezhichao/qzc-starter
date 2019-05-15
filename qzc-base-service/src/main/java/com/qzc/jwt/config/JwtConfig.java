package com.qzc.jwt.config;

import com.qzc.util.BaseValidatorUtil;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Data
@Component
//@ConfigurationProperties(prefix = "jwt")
//@Validated
public class JwtConfig {

    // 过期时长（单位秒）
    @Value("${jwt.expireSecond:#{null}}")
    private String expireSecond;

    public boolean check() {
        return StringUtils.isNotBlank(this.getExpireSecond())
                && BaseValidatorUtil.validateIsDigit(this.getExpireSecond());
    }
}
