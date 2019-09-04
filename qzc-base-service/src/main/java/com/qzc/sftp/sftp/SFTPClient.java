package com.qzc.sftp.sftp;

import com.jcraft.jsch.*;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

/**
 * Create by qiezhichao on 2018/5/10 0011 20:36
 */
public class SFTPClient {

    private Session session;
    private ChannelSftp channelSftp;
    private SFTPConfig sftpConfig;

    public SFTPClient(SFTPConfig sftpConfig) {
        this.sftpConfig = sftpConfig;
        this.connect(sftpConfig);
    }

    private void connect(SFTPConfig sftpConfig) {
        try {
            session = new JSch().getSession(sftpConfig.getSftpUserName(),
                    sftpConfig.getSftpHost(),
                    sftpConfig.getSftpPort());
            if (null != sftpConfig.getSftpPassword()) {
                session.setPassword(sftpConfig.getSftpPassword());
            }
            if (null != sftpConfig.getTimeout()) {
                session.setTimeout(sftpConfig.getTimeout());
            }
            session.setConfig("StrictHostKeyChecking", "no"); // 让ssh客户端自动接受新主机的hostkey
            session.connect();

            this.channelSftp = (ChannelSftp) session.openChannel("sftp");
            this.channelSftp.connect();

        } catch (JSchException e) {
            this.close();
            e.printStackTrace();
        }
    }

    public void close() {
        channelSftp.quit();

        if (null != channelSftp) {
            channelSftp.disconnect();
        }

        if (null != session) {
            session.disconnect();
        }
    }

    /*============================================upload============================================*/
    /**
     * @param sftpParams
     * @param channelSftpModel 调用的模式： ChannelSftp.OVERWRITE，ChannelSftp.APPEND，ChannelSftp.RESUME
     * @throws SFTPException
     */
    public void upload(SFTPParams sftpParams, int channelSftpModel) throws SFTPException {
        try {
            channelSftp.put(sftpParams.getLocalFilepath(), sftpParams.getRemoteFilepath(), channelSftpModel);
        } catch (SftpException e) {
            throw new SFTPException("Upload [" + sftpParams.getLocalFilepath() + "] to SFTP "
                    + sftpConfig.getSftpHost() + ":" + sftpConfig.getSftpPort()
                    + "[" + sftpParams.getRemoteFilepath() + "]" + " error.", e);
        }
    }

    /**
     * @param sftpParams
     * @param channelSftpModel 调用的模式： ChannelSftp.OVERWRITE，ChannelSftp.APPEND，ChannelSftp.RESUME
     * @param monitor 监视器
     * @throws SFTPException
     */
    public void upload(SFTPParams sftpParams, int channelSftpModel, SftpProgressMonitor monitor) throws SFTPException {
        try {
            channelSftp.put(sftpParams.getLocalFilepath(), sftpParams.getRemoteFilepath(), monitor, channelSftpModel);
        } catch (SftpException e) {
            throw new SFTPException("Upload [" + sftpParams.getLocalFilepath() + "] to SFTP "
                    + sftpConfig.getSftpHost() + ":" + sftpConfig.getSftpPort()
                    + "[" + sftpParams.getRemoteFilepath() + "]" + " error.", e);
        }
    }

    /**
     * @param sftpParams
     * @param channelSftpModel 调用的模式： ChannelSftp.OVERWRITE，ChannelSftp.APPEND，ChannelSftp.RESUME
     * @param src 输入流
     * @throws SFTPException
     */
    public void upload(SFTPParams sftpParams, int channelSftpModel, InputStream src) throws SFTPException {
        try {
            channelSftp.put(src, sftpParams.getRemoteFilepath(), channelSftpModel);
        } catch (SftpException e) {
            throw new SFTPException("Upload [" + sftpParams.getLocalFilepath() + "] to SFTP "
                    + sftpConfig.getSftpHost() + ":" + sftpConfig.getSftpPort()
                    + "[" + sftpParams.getRemoteFilepath() + "]" + " error.", e);
        }
    }

    /**
     * @param sftpParams
     * @param channelSftpModel 调用的模式： ChannelSftp.OVERWRITE，ChannelSftp.APPEND，ChannelSftp.RESUME
     * @param src 输入流
     * @param bufferSize 数据块大小
     * @throws SFTPException
     */
    public void upload(SFTPParams sftpParams, int channelSftpModel, InputStream src, int bufferSize) throws SFTPException {
        OutputStream out = null;
        try {
            out = channelSftp.put(sftpParams.getRemoteFilepath(), channelSftpModel);
            byte[] buff = new byte[bufferSize]; // 设定每次传输的数据块大小
            int read;
            if (out != null) {
                do {
                    read = src.read(buff, 0, buff.length);
                    if (read > 0) {
                        out.write(buff, 0, read);
                    }
                    out.flush();
                } while (read >= 0);
            }
        } catch (IOException e) {
            throw new SFTPException("Upload [" + sftpParams.getLocalFilepath() + "] to SFTP "
                    + sftpConfig.getSftpHost() + ":" + sftpConfig.getSftpPort()
                    + "[" + sftpParams.getRemoteFilepath() + "]" + " error.", e);
        } catch (SftpException e) {
            throw new SFTPException("Upload [" + sftpParams.getLocalFilepath() + "] to SFTP "
                    + sftpConfig.getSftpHost() + ":" + sftpConfig.getSftpPort()
                    + "[" + sftpParams.getRemoteFilepath() + "]" + " error.", e);
        } finally {
            if (null != out) {
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /*============================================download============================================*/
    /**
     * @param sftpParams
     * @throws SFTPException
     */
    public void download(SFTPParams sftpParams) throws SFTPException {
        try {
            channelSftp.get(sftpParams.getRemoteFilepath(), sftpParams.getLocalFilepath());
        } catch (SftpException e) {
            throw new SFTPException("Download [" + sftpParams.getRemoteFilepath() + "] from SFTP "
                    + sftpConfig.getSftpHost() + ":" + sftpConfig.getSftpPort() + " error.", e);
        }
    }

    /**
     * @param sftpParams
     * @param os
     * @throws SFTPException
     */
    public void download(SFTPParams sftpParams, OutputStream os) throws SFTPException {
        try {
            channelSftp.get(sftpParams.getRemoteFilepath(), os);
        } catch (SftpException e) {
            throw new SFTPException("Download [" + sftpParams.getRemoteFilepath() + "] from SFTP "
                    + sftpConfig.getSftpHost() + ":" + sftpConfig.getSftpPort() + " error.", e);
        }
    }

    /**
     * @param sftpParams
     * @param monitor
     * @throws SFTPException
     */
    public void download(SFTPParams sftpParams, SftpProgressMonitor monitor) throws SFTPException {
        try {
            channelSftp.get(sftpParams.getRemoteFilepath(), sftpParams.getLocalFilepath(), monitor);
        } catch (SftpException e) {
            throw new SFTPException("Download [" + sftpParams.getRemoteFilepath() + "] from SFTP "
                    + sftpConfig.getSftpHost() + ":" + sftpConfig.getSftpPort() + " error.", e);
        }
    }

    /**
     * @param sftpParams
     * @param monitor
     * @param os
     * @throws SFTPException
     */
    public void download(SFTPParams sftpParams, SftpProgressMonitor monitor, OutputStream os) throws SFTPException {
        try {
            channelSftp.get(sftpParams.getRemoteFilepath(), os, monitor);
        } catch (SftpException e) {
            throw new SFTPException("Download [" + sftpParams.getRemoteFilepath() + "] from SFTP "
                    + sftpConfig.getSftpHost() + ":" + sftpConfig.getSftpPort() + " error.", e);
        }
    }

    /**
     * @param sftpParams
     * @return
     * @throws SFTPException
     */
    public InputStream download2InputStream(SFTPParams sftpParams) throws SFTPException {
        try {
            return channelSftp.get(sftpParams.getRemoteFilepath());
        } catch (SftpException e) {
            throw new SFTPException("Download [" + sftpParams.getRemoteFilepath() + "] from SFTP "
                    + sftpConfig.getSftpHost() + ":" + sftpConfig.getSftpPort() + " error.", e);
        }
    }

    /*============================================other============================================*/

    /**
     * @param remotePath
     * @return
     * @throws SFTPException
     */
    public List<String> ls(String remotePath) throws SFTPException {
        try {
            List<String> remoteFileNameList = new ArrayList<String>();

            Vector ls = channelSftp.ls(remotePath);
            for(Object obj:ls){
                ChannelSftp.LsEntry lsEntry = (ChannelSftp.LsEntry) obj;
                remoteFileNameList.add(lsEntry.getFilename());
            }

            return remoteFileNameList;
        } catch (SftpException e) {
            throw new SFTPException("List [" + remotePath + "] from SFTP "
                    + sftpConfig.getSftpHost() + ":" + sftpConfig.getSftpPort() + " error.", e);
        }
    }

}
