package com.qzc.storage.config;

import com.qzc.exception.ServiceException;
import com.qzc.pojo.ApplicationCheckConfig;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.List;

@Data
@Component
@Slf4j
public class TXCosConfig extends ApplicationCheckConfig {

    @Value("${tx.cos.open:#{null}}")
    private String open;

    @Value("${tx.secretId:#{null}}")
    private String secretId;

    @Value("${tx.secretKey:#{null}}")
    private String secretKey;

    @Value("${tx.cos.region:#{null}}")
    private String region;

    @Value("${tx.cos.bucketName:#{null}}")
    private String bucketName;

    @Value("${tx.cos.fileDuration:#{null}}")
    private Long fileDuration; // 文件有效时间, 单位毫秒

    @Value("${tx.cos.fileMaxSize:#{null}}")
    private Long fileMaxSize; // 文件最大值

    @Value("${tx.cos.fileValidatePostfix:#{null}}")
    private String fileValidatePostfix; // 文件有效后缀名称，以","分割

    private List<String> fileValidatePostfixList;

    @PostConstruct
    public void init() {

        if (StringUtils.equals(this.getOpen(), "true")) {
            super.setOpen(true);
            this.check();
        }

        String fileValidatePostfix = this.getFileValidatePostfix();
        if (StringUtils.isNotBlank(fileValidatePostfix)) {
            this.setFileValidatePostfixList(Arrays.asList(fileValidatePostfix.split(",")));
        }
    }

    // ========================================
    private void check() {
        log.debug("begin to check TX cos config");
        if (StringUtils.isBlank(this.getSecretId())
                || StringUtils.isBlank(this.getSecretKey())
                || StringUtils.isBlank(this.getRegion())
                || StringUtils.isBlank(this.getBucketName())
                || this.getFileDuration() == null
                || this.getFileMaxSize() == null
                || StringUtils.isBlank(this.getFileValidatePostfix())) {

            log.error("腾讯云cos存储配置校验失败");
            throw new ServiceException("腾讯云cos存储配置校验失败");
        } else {
            log.debug("腾讯云cos存储配置校验成功");
        }
    }

}
