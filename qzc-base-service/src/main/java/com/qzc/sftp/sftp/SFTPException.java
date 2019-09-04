package com.qzc.sftp.sftp;

/**
 * Create by qiezhichao on 2018/5/11 0011 21:37
 */
public class SFTPException extends Exception {
    private static final long serialVersionUID = -4539012617666225876L;

    private Throwable cause;

    public SFTPException(String message) {
        super(message);
    }

    public SFTPException(String message, Throwable e) {
        super(message);
        this.cause = e;
    }

    public Throwable getCause() {
        return this.cause;
    }


}
