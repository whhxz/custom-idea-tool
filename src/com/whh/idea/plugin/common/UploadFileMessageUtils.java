package com.whh.idea.plugin.common;

import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.StyledDocument;

/**
 * UploadFileMessageUtils
 * Created by xuzhuo on 2018/4/25.
 */
public class UploadFileMessageUtils {
    private static JScrollPane jScrollPane;
    private static JTextArea jTextArea;

    public static void init(JScrollPane jScrollPane, JTextArea jTextArea) {
        UploadFileMessageUtils.jScrollPane = jScrollPane;
        UploadFileMessageUtils.jTextArea = jTextArea;
    }

    public static void print(String message) {
        jTextArea.append(message + "\n");
    }
}
