package com.qzc.captcha.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TXCaptchaBean {

    // 1:验证成功，0:验证失败，
    private int response;

    // 恶意等级,取值范围[0,100]
    private int evilLevel;

    // 错误信息
    private String errMsg;
}
