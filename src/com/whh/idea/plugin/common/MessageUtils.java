package com.whh.idea.plugin.common;

import com.intellij.notification.Notification;
import com.intellij.notification.NotificationType;
import com.intellij.notification.Notifications;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.project.ProjectManager;

import javax.swing.*;

/**
 * MessageUtils
 * Created by xuzhuo on 2018/4/25.
 */
public class MessageUtils {
    public static void print(String title, String content){
        Project project = ProjectManager.getInstance().getOpenProjects()[0];
        Notifications.Bus.notify(new Notification("whhPlugin", title, content, NotificationType.INFORMATION), project);
    }

    public static void panel(JTextPane jTextPane, String message){
//        jTextPane.getStyledDocument().insertString();
    }
}
