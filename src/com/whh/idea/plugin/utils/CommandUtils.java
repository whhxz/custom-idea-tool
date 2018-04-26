package com.whh.idea.plugin.utils;

import com.intellij.ide.plugins.PluginManager;
import com.whh.idea.plugin.common.MessageUtils;
import com.whh.idea.plugin.common.UploadFileMessageUtils;

import java.io.*;
import java.util.concurrent.TimeUnit;

/**
 * CommandUtils
 * Created by xuzhuo on 2018/4/25.
 */
public class CommandUtils {
    public static void command(String command, String dir, long waitSeconds){
        Process process;
        try {
            UploadFileMessageUtils.print("准备执行命令.....");

            process = Runtime.getRuntime().exec(command, null, new File(dir));
            printMessage(process.getInputStream());
            printMessage(process.getErrorStream());
            process.waitFor(waitSeconds, TimeUnit.SECONDS);
            System.out.println(process.exitValue());
            UploadFileMessageUtils.print("执行命令完毕.....");
        } catch (IOException | InterruptedException e) {
            StringWriter errors = new StringWriter();
            e.printStackTrace(new PrintWriter(errors));
            UploadFileMessageUtils.print(errors.toString());
        }

    }

    private static void printMessage(final InputStream input) {
        new Thread(() -> {
            BufferedReader bf = new BufferedReader(new InputStreamReader(input));
            String line;
            try {
                while ((line = bf.readLine()) != null) {
                    UploadFileMessageUtils.print(line);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

    public static void main(String[] args) {
        System.out.println(System.getenv("M2_HOME"));
    }
}
