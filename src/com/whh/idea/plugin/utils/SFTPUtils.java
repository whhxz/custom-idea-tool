package com.whh.idea.plugin.utils;

import com.intellij.ide.plugins.PluginManager;
import com.intellij.notification.Notification;
import com.intellij.notification.NotificationType;
import com.intellij.notification.Notifications;
import com.jcraft.jsch.*;
import com.whh.idea.plugin.common.MessageUtils;
import com.whh.idea.plugin.common.UploadFileMessageUtils;

import java.io.*;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

/**
 * SFTPUtils
 * Created by xuzhuo on 2018/1/24.
 */
public class SFTPUtils {

    /**
     * 上传文件
     *
     * @param host
     * @param username
     * @param password
     * @param port
     * @param filePath
     * @param remotePath
     */
    public static void uploadFile(String host, String username, String password, int port, String filePath, String remotePath, boolean block, long time) {
        UploadFileMessageUtils.print("准备上传文件.....");
        JSch jSch = new JSch();
        Session session = null;
        ChannelSftp sftp = null;
        try {
            session = jSch.getSession(username, host, port);
            session.setPassword(password);
            Properties config = new Properties();
            config.put("StrictHostKeyChecking", "no");
            session.setConfig(config);
            session.connect();
            Channel channel = session.openChannel("sftp");
            channel.connect();
            sftp = (ChannelSftp) channel;

            Notification notification = new Notification("github", "Success", "Successfully created project '''' on github", NotificationType.INFORMATION);
            Notifications.Bus.notify(notification);


            sftp.cd(remotePath);
            File file = new File(filePath);
            final boolean[] uploadEnd = {false};
            sftp.put(new FileInputStream(file), file.getName(), new SftpProgressMonitor() {
                private long transfered;

                @Override
                public void init(int i, String s, String s1, long l) {
                    UploadFileMessageUtils.print("文件开始上传.....");
                }

                @Override
                public boolean count(long l) {
                    transfered += l;
                    return true;
                }

                @Override
                public void end() {
                    uploadEnd[0] = true;
                    UploadFileMessageUtils.print("文件上传完毕.....");
                }
            });
            long now = System.currentTimeMillis();
            while (block && !uploadEnd[0] && System.currentTimeMillis() - now < time) {
                TimeUnit.MILLISECONDS.sleep(500);
            }
        } catch (JSchException | SftpException | FileNotFoundException | InterruptedException e) {
            StringWriter errors = new StringWriter();
            e.printStackTrace(new PrintWriter(errors));
            UploadFileMessageUtils.print(errors.toString());
        } finally {
            if (sftp != null && sftp.isConnected()) {
                sftp.disconnect();
            }
            if (session != null && session.isConnected()) {
                session.disconnect();
            }
        }
    }
}
