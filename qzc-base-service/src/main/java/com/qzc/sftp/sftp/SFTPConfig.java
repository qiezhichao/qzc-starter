package com.qzc.sftp.sftp;

/**
 * SFTP的基础配置
 * <p>
 * Create by qiezhichao on 2018/5/12 0012 21:19
 */
public class SFTPConfig {
    private String sftpHost; // sftp主机地址
    private int sftpPort; // sftp端口
    private String sftpUserName; // sftp用户名
    private String sftpPassword; // sftp密码
    private Integer timeout; // 过期时间

    public SFTPConfig(String sftpHost, int sftpPort, String sftpUserName, String sftpPassword) {
        this.sftpHost = sftpHost;
        this.sftpPort = sftpPort;
        this.sftpUserName = sftpUserName;
        this.sftpPassword = sftpPassword;
    }

    public String getSftpHost() {
        return sftpHost;
    }

    public void setSftpHost(String sftpHost) {
        this.sftpHost = sftpHost;
    }

    public int getSftpPort() {
        return sftpPort;
    }

    public void setSftpPort(int sftpPort) {
        this.sftpPort = sftpPort;
    }

    public String getSftpUserName() {
        return sftpUserName;
    }

    public void setSftpUserName(String sftpUserName) {
        this.sftpUserName = sftpUserName;
    }

    public String getSftpPassword() {
        return sftpPassword;
    }

    public void setSftpPassword(String sftpPassword) {
        this.sftpPassword = sftpPassword;
    }

    public Integer getTimeout() {
        return timeout;
    }

    public void setTimeout(Integer timeout) {
        this.timeout = timeout;
    }

    @Override
    public String toString() {
        return "SFTPConfig{" +
                "sftpHost='" + sftpHost + '\'' +
                ", sftpPort=" + sftpPort +
                ", sftpUserName='" + sftpUserName + '\'' +
                ", sftpPassword='" + sftpPassword + '\'' +
                ", timeout=" + timeout +
                '}';
    }
}
