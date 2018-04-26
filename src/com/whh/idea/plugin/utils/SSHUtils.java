package com.whh.idea.plugin.utils;

import ch.ethz.ssh2.ChannelCondition;
import ch.ethz.ssh2.Connection;
import ch.ethz.ssh2.Session;
import com.whh.idea.plugin.common.UploadFileMessageUtils;

import java.io.IOException;

/**
 * SSHUtils
 * Created by xuzhuo on 2018/1/24.
 */
public class SSHUtils {
    public static void execScript(String host, String username, String password, int port, String scriptPath, String scriptName) {
        UploadFileMessageUtils.print("执行远程脚本开始.....");
        Connection connection = new Connection(host, port);
        Session session = null;
        try {
            connection.connect();
            if (connection.authenticateWithPassword(username, password)) {
                session = connection.openSession();
                session.execCommand("cd " + scriptPath + " ;./" + scriptName);
                session.waitForCondition(ChannelCondition.EXIT_STATUS | ChannelCondition.EXIT_SIGNAL, 3000);
                UploadFileMessageUtils.print("执行远程脚本结束.....");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (session != null){
                session.close();
            }
            connection.close();
        }
    }
}
