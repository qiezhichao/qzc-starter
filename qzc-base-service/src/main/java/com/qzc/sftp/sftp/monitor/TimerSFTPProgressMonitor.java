package com.qzc.sftp.sftp.monitor;

import com.jcraft.jsch.SftpProgressMonitor;

import java.text.DecimalFormat;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Create by qiezhichao on 2018/5/12 0012 12:04
 */
public class TimerSFTPProgressMonitor implements SftpProgressMonitor {

    private boolean isTransEnd = false; // 是否传输完成
    private long fileTotalSize;  // 需要传输文件的大小
    private long fileTransferedSize; // 已传输的大小
    private ScheduledExecutorService service = Executors.newScheduledThreadPool(1);


    public void init(int op, String src, String dest, long max) {
        System.out.println("Begin transferring.");
        fileTotalSize = max;
        if (fileTotalSize != 0) {
            final DecimalFormat df = new DecimalFormat("#.##");
            service.scheduleAtFixedRate(new Runnable() {
                public void run() {
                    if (!isTransEnd) {
                        if (fileTransferedSize != fileTotalSize) {
                            double d = ((double) fileTransferedSize * 100) / (double) fileTotalSize;
                            System.out.println("Current progress: " + df.format(d) + "%");
                        } else {
                            isTransEnd = true; // 已传输大小等于文件总大小，则已完成
                        }
                    }
                }
            }, 0, 1, TimeUnit.SECONDS);
        }
    }

    public boolean count(final long count) {
        fileTransferedSize = fileTransferedSize + count;
        return true;
    }

    public void end() {
        service.shutdown();
        System.out.println("End transferring, transferedSize : " + fileTransferedSize);
    }

}
