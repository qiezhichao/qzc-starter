package com.qzc.storage;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.model.ObjectMetadata;
import com.qcloud.cos.model.PutObjectResult;
import com.qcloud.cos.region.Region;
import com.qzc.exception.ServiceException;
import com.qzc.storage.config.TXCosConfig;
import com.qzc.util.BaseUuidUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Date;

/**
 * 腾讯cos服务
 *
 * @Author: qiezhichao
 * @CreateDate: 2019/5/30 21:04
 */
@Service
@Slf4j
public class TXCosService {

    @Autowired
    private TXCosConfig txCosConfig;

    /**
     * 文件上传 同名文件进行覆盖
     *
     * @param file 待上传文件
     * @return 服务器文件名
     * @Author: qiezhichao
     * @CreateDate: 2019/6/3 21:11
     */
    public String uploadFile2Cos(MultipartFile file) {
        log.debug("enter uploadFile2Cos");


        String originalFilename = file.getOriginalFilename();
        String substring = originalFilename.substring(originalFilename.lastIndexOf(".")).toLowerCase();
        String fileName = BaseUuidUtil.generateUuid() + System.currentTimeMillis() + substring;

        try {
            this.uploadFile2Cos(file.getInputStream(), fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return fileName;
    }

    /**
     * 获得上传文件url链接
     *
     * @Author: qiezhichao
     * @CreateDate: 2019/6/3 21:10
     */
    public String getFileUrl(String fileName) {
        // 设置URL过期时间为10年 3600l* 1000*24*365*10
        Date expiration = new Date(System.currentTimeMillis() + 3600L * 1000 * 24 * 365 * 10);
        // 生成URL
        URL url = this.getCOSClient().generatePresignedUrl(txCosConfig.getBucketName(), fileName, expiration);
        if (url != null) {
            return url.toString();
        }
        return null;
    }


    // =========================================
    /*
     *  获取cosClient
     */
    private COSClient getCOSClient() {
        COSCredentials cred = new BasicCOSCredentials(txCosConfig.getSecretId(), txCosConfig.getSecretKey());
        ClientConfig clientConfig = new ClientConfig(new Region(txCosConfig.getRegion()));
        return new COSClient(cred, clientConfig);
    }

    /*
     *  校验文件
     */
    private void checkFile(MultipartFile file) {
        if (txCosConfig.getFileMaxSize() != null
                && file.getSize() > txCosConfig.getFileMaxSize()) {
            log.error("文件大小不能大于[{}]", txCosConfig.getFileMaxSize());
            throw new ServiceException("文件大小超过允许最大值");
        }

        if (CollectionUtils.isNotEmpty(txCosConfig.getFileValidatePostfixList())) {
            boolean flag = false;

            String filename = file.getOriginalFilename();
            for (String fileValidatePostfix : txCosConfig.getFileValidatePostfixList()) {
                if (filename.endsWith(fileValidatePostfix)) {
                    flag = true;
                    break;
                }
            }

            if (!flag) {
                log.error("当前文件后缀不支持上传");
                throw new ServiceException("当前文件后缀不支持上传");
            }
        }
    }

    /*
     * 文件上传 同名文件进行覆盖
     *
     * @param inputStream 文件流
     * @param fileName    文件名称 包括后缀名
     * @Author: qiezhichao
     * @CreateDate: 2019/6/3 21:11
     */
    private void uploadFile2Cos(InputStream inputStream, String fileName) {

        try {
            // 创建上传Object的Metadata
            ObjectMetadata objectMetadata = new ObjectMetadata();
            objectMetadata.setContentLength(inputStream.available());
            objectMetadata.setCacheControl("no-cache");
            objectMetadata.setHeader("Pragma", "no-cache");
            objectMetadata.setContentType(this.getContentType(fileName.substring(fileName.lastIndexOf("."))));
            objectMetadata.setContentDisposition("inline;filename=" + fileName);
            // 上传文件
            PutObjectResult putResult = this.getCOSClient().putObject(txCosConfig.getBucketName(), fileName, inputStream, objectMetadata);
            log.debug("putResult=[{}]", putResult);
        } catch (IOException e) {
            log.error("腾讯云COS存储服务异常， 异常信息:{}", e.getMessage());
            throw new ServiceException("腾讯云COS存储服务异常");
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /*
     *  根据文件后缀获取ContentType
     */
    private String getContentType(String filePostFix) {
        if (filePostFix.equalsIgnoreCase("bmp")) {
            return "image/bmp";
        }
        if (filePostFix.equalsIgnoreCase("gif")) {
            return "image/gif";
        }
        if (filePostFix.equalsIgnoreCase("jpeg")
                || filePostFix.equalsIgnoreCase("jpg")
                || filePostFix.equalsIgnoreCase("png")) {
            return "image/jpeg";
        }
        if (filePostFix.equalsIgnoreCase("html")) {
            return "text/html";
        }
        if (filePostFix.equalsIgnoreCase("txt")) {
            return "text/plain";
        }
        if (filePostFix.equalsIgnoreCase("vsd")) {
            return "application/vnd.visio";
        }
        if (filePostFix.equalsIgnoreCase("pptx") || filePostFix.equalsIgnoreCase("ppt")) {
            return "application/vnd.ms-powerpoint";
        }
        if (filePostFix.equalsIgnoreCase("docx") || filePostFix.equalsIgnoreCase("doc")) {
            return "application/msword";
        }
        if (filePostFix.equalsIgnoreCase("xml")) {
            return "text/xml";
        }
        return null;
    }

}
