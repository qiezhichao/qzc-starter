package com.qzc.pojo;

import lombok.Data;

/**
 *  一些服务依赖application.yml中的配置，例如腾讯云短信服务，因此用户在调用对应服务中的方法时，需要校验application.yml中是否有配置
 * @Author:         qiezhichao
 * @CreateDate:     2019/6/7 11:43
 */
@Data
public abstract class ApplicationCheckConfig {
    private boolean isOpen = false;
}
