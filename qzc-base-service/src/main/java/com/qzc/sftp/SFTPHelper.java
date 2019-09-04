package com.qzc.sftp;

import com.jcraft.jsch.ChannelSftp;
import com.qzc.sftp.sftp.SFTPClient;
import com.qzc.sftp.sftp.SFTPConfig;
import com.qzc.sftp.sftp.SFTPException;
import com.qzc.sftp.sftp.SFTPParams;
import com.qzc.sftp.sftp.monitor.TimerSFTPProgressMonitor;
import org.apache.commons.lang3.StringUtils;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

/**
 * Create by qiezhichao on 2018/5/10 0010 19:44
 */
public class SFTPHelper {

    private final static String sftpHost = "192.168.118.129";
    private final static int sftpPort = 22;
    private final static String sftpUserName = "sftpuser";
    private final static String sftpPassword = "19920508";

    private static SFTPClient getSFTPClient() {
        return new SFTPClient(new SFTPConfig(sftpHost, sftpPort, sftpUserName, sftpPassword));
    }

    /*************************************upload*************************************/
    public static void uploadOverwrite(String localFilePath, String remotePath) {
        SFTPClient client = getSFTPClient();
        try {
            SFTPParams params = buildSFTPParams(localFilePath, remotePath);
            client.upload(params, ChannelSftp.OVERWRITE);
        } catch (SFTPException e) {
            e.printStackTrace();
        } finally {
            client.close();
        }
    }

    public static void uploadAppend(String localFilePath, String remotePath) {
        SFTPClient client = getSFTPClient();
        try {
            SFTPParams params = buildSFTPParams(localFilePath, remotePath);
            client.upload(params, ChannelSftp.APPEND);
        } catch (SFTPException e) {
            e.printStackTrace();
        } finally {
            client.close();
        }
    }

    public static void uploadResume(String localFilePath, String remotePath) {
        SFTPClient client = getSFTPClient();
        try {
            SFTPParams params = buildSFTPParams(localFilePath, remotePath);
            client.upload(params, ChannelSftp.RESUME);
        } catch (SFTPException e) {
            e.printStackTrace();
        } finally {
            client.close();
        }
    }

    public static void uploadOverwriteWithTimerMonitor(String localFilePath, String remotePath) {
        SFTPClient client = getSFTPClient();
        try {
            SFTPParams params = buildSFTPParams(localFilePath, remotePath);
            client.upload(params, ChannelSftp.OVERWRITE, new TimerSFTPProgressMonitor());
        } catch (SFTPException e) {
            e.printStackTrace();
        } finally {
            client.close();
        }
    }

    public static void uploadAppendWithTimerMonitor(String localFilePath, String remotePath) {
        SFTPClient client = getSFTPClient();
        try {
            SFTPParams params = buildSFTPParams(localFilePath, remotePath);
            client.upload(params, ChannelSftp.APPEND, new TimerSFTPProgressMonitor());
        } catch (SFTPException e) {
            e.printStackTrace();
        } finally {
            client.close();
        }
    }

    public static void uploadResumeWithTimerMonitor(String localFilePath, String remotePath) {
        SFTPClient client = getSFTPClient();
        try {
            SFTPParams params = buildSFTPParams(localFilePath, remotePath);
            client.upload(params, ChannelSftp.RESUME, new TimerSFTPProgressMonitor());
        } catch (SFTPException e) {
            e.printStackTrace();
        } finally {
            client.close();
        }
    }

    public static void uploadOverwrite(InputStream src, String remotePath) {
        SFTPClient client = getSFTPClient();
        try {
            SFTPParams params = buildSFTPParams(null, remotePath);
            client.upload(params, ChannelSftp.OVERWRITE, src);
        } catch (SFTPException e) {
            e.printStackTrace();
        } finally {
            client.close();
        }
    }

    public static void uploadAppend(InputStream src, String remotePath) {
        SFTPClient client = getSFTPClient();
        try {
            SFTPParams params = buildSFTPParams(null, remotePath);
            client.upload(params, ChannelSftp.APPEND, src);
        } catch (SFTPException e) {
            e.printStackTrace();
        } finally {
            client.close();
        }
    }

    public static void uploadResume(InputStream src, String remotePath) {
        SFTPClient client = getSFTPClient();
        try {
            SFTPParams params = buildSFTPParams(null, remotePath);
            client.upload(params, ChannelSftp.RESUME, src);
        } catch (SFTPException e) {
            e.printStackTrace();
        } finally {
            client.close();
        }
    }

    public static void uploadOverwrite(InputStream src, int bufferSize, String remotePath) {
        SFTPClient client = getSFTPClient();
        try {
            SFTPParams params = buildSFTPParams(null, remotePath);
            client.upload(params, ChannelSftp.OVERWRITE, src, bufferSize);
        } catch (SFTPException e) {
            e.printStackTrace();
        } finally {
            client.close();
        }
    }

    public static void uploadAppend(InputStream src, int bufferSize, String remotePath) {
        SFTPClient client = getSFTPClient();
        try {
            SFTPParams params = buildSFTPParams(null, remotePath);
            client.upload(params, ChannelSftp.APPEND, src, bufferSize);
        } catch (SFTPException e) {
            e.printStackTrace();
        } finally {
            client.close();
        }
    }

    public static void uploadResume(InputStream src, int bufferSize, String remotePath) {
        SFTPClient client = getSFTPClient();
        try {
            SFTPParams params = buildSFTPParams(null, remotePath);
            client.upload(params, ChannelSftp.RESUME, src, bufferSize);
        } catch (SFTPException e) {
            e.printStackTrace();
        } finally {
            client.close();
        }
    }

    /*************************************download*************************************/
    public static void download(String remotePath, String localFilePath) {
        SFTPClient client = getSFTPClient();
        try {
            SFTPParams params = buildSFTPParams(localFilePath, remotePath);
            client.download(params);
        } catch (SFTPException e) {
            e.printStackTrace();
        } finally {
            client.close();
        }
    }

    public static void downloadWithTimerMonitor(String remotePath, String localFilePath) {
        SFTPClient client = getSFTPClient();
        try {
            SFTPParams params = buildSFTPParams(localFilePath, remotePath);
            client.download(params, new TimerSFTPProgressMonitor());
        } catch (SFTPException e) {
            e.printStackTrace();
        } finally {
            client.close();
        }
    }

    public static void download(OutputStream os, String remotePath) {
        SFTPClient client = getSFTPClient();
        try {
            SFTPParams params = buildSFTPParams(null, remotePath);
            client.download(params, os);
        } catch (SFTPException e) {
            e.printStackTrace();
        } finally {
            client.close();
        }
    }

    public static void downloadWithTimerMonitor(OutputStream os, String remotePath) {
        SFTPClient client = getSFTPClient();
        try {
            SFTPParams params = buildSFTPParams(null, remotePath);
            client.download(params, new TimerSFTPProgressMonitor(), os);
        } catch (SFTPException e) {
            e.printStackTrace();
        } finally {
            client.close();
        }
    }


    /**
     * 该方法使用时会出现异常：java.io.IOException: Pipe closed
     * 原因是在 finally 中执行了 client.close();
     *
     * @param remotePath
     * @return
     */
    @Deprecated
    public static InputStream download2InputStream(String remotePath) {
        SFTPClient client = getSFTPClient();
        try {
            SFTPParams params = new SFTPParams();
            params.setRemoteFilepath(remotePath);
            return client.download2InputStream(params);
        } catch (SFTPException e) {
            e.printStackTrace();
        } finally {
            client.close();
        }

        return null;
    }

    /*************************************other*************************************/
    public static List<String> ls(String remotePath) {
        SFTPClient client = getSFTPClient();
        try {
            if (StringUtils.isEmpty(remotePath.trim())) {
                return null;
            }

            return client.ls(remotePath);
        } catch (SFTPException e) {
            e.printStackTrace();
        } finally {
            client.close();
        }

        return null;
    }

    /*************************************private*************************************/
    /*
     * 构造SFTPParams参数
     *
     * @param remotePath
     * @param localFilePath
     * @return
     */
    private static SFTPParams buildSFTPParams(String localFilePath, String remotePath) {
        SFTPParams params = new SFTPParams();
        if (StringUtils.isNotEmpty(localFilePath)) {
            params.setLocalFilepath(localFilePath);
        }
        if (StringUtils.isNotEmpty(remotePath)) {
            params.setRemoteFilepath(remotePath);
        }

        return params;
    }

}