package com.whh.idea.plugin.action;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.actionSystem.LangDataKeys;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.openapi.wm.WindowManager;
import com.whh.idea.plugin.ui.UploadFileRemoteHostDialog;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * UploadFileRemoteHostAction
 * Created by xuzhuo on 2018/1/23.
 */
public class UploadFileRemoteHostAction extends AnAction {

    @Override
    public void actionPerformed(AnActionEvent e) {
        Module selectModule = e.getData(LangDataKeys.MODULE);
        Project project = e.getProject();
        VirtualFile file = e.getData(CommonDataKeys.VIRTUAL_FILE);
        if (selectModule != null && file != null) {
            UploadFileRemoteHostDialog uploadFileRemoteHostDialog = new UploadFileRemoteHostDialog();
            uploadFileRemoteHostDialog.pack();
            String filePath = file.getPath();
            uploadFileRemoteHostDialog.changeTextInfo(filePath.substring(filePath.lastIndexOf("/") + 1), filePath);
            uploadFileRemoteHostDialog.setSize(800, 500);
            uploadFileRemoteHostDialog.showCenter(WindowManager.getInstance().getFrame(project).getSize());
            uploadFileRemoteHostDialog.setVisible(true);
        }
    }
}
