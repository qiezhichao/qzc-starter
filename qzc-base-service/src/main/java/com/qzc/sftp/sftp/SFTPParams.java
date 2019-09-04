package com.qzc.sftp.sftp;

/**
 * SFTP相关参数
 * <p>
 * Create by qiezhichao on 2018/5/10 0010 19:36
 */
public class SFTPParams {
    private String localFilepath; // 包含全路径的本地文件名
    private String remoteFilepath; // 包含全路径的远程文件名

    public String getLocalFilepath() {
        return localFilepath;
    }

    public void setLocalFilepath(String localFilepath) {
        this.localFilepath = localFilepath;
    }

    public String getRemoteFilepath() {
        return remoteFilepath;
    }

    public void setRemoteFilepath(String remoteFilepath) {
        this.remoteFilepath = remoteFilepath;
    }
}
