package com.whh.idea.plugin.ui;

import com.whh.idea.plugin.common.UploadFileMessageUtils;
import com.whh.idea.plugin.config.UploadFileConfig;
import com.whh.idea.plugin.utils.CommandUtils;
import com.whh.idea.plugin.utils.SFTPUtils;
import com.whh.idea.plugin.utils.SSHUtils;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.DefaultCaret;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class UploadFileRemoteHostDialog extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField remoteHost;
    private JLabel loginNameText;
    private JTextField loginName;
    private JLabel remoteHostText;
    private JLabel passwordText;
    private JLabel remoteDirText;
    private JTextField remoteDir;
    private JCheckBox savePassword;
    private JPasswordField password;
    private JCheckBox restartServer;
    private JTextField modulePath;
    private JLabel modlePathText;
    private JTextField buildCommand;
    private JLabel buildCommandText;
    private JTextField moduleName;
    private JLabel moduleNameText;
    private JScrollPane logScroll;
    private JTextArea logText;
    private JCheckBox uploadWar;

    private final String remoteHostTemp = UploadFileConfig.value("remoteHostTemp");
    private final String remoteDirTemp = UploadFileConfig.value("remoteDirTemp");
    private final String restartScriptName = UploadFileConfig.value("restartScriptName");

    public UploadFileRemoteHostDialog() {
        UploadFileMessageUtils.init(logScroll, logText);
        //滚动条保持最底部
        DefaultCaret caret = (DefaultCaret) logText.getCaret();
        caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
        //设置mvn环境变量
        String mavenHome = System.getenv("M2_HOME");
        buildCommand.setText(mavenHome + "/bin/./" + buildCommand.getText().trim());


//        Project project = ProjectManager.getInstance().getOpenProjects()[0];
        this.loginName.setText(UploadFileConfig.value("remoteUser"));
        this.password.setText(UploadFileConfig.value("remotePassword"));
        this.buildCommand.setText(UploadFileConfig.value("buildCommand"));

        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        buttonOK.addActionListener(e -> onOK());
        moduleName.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                changeModuleName();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                changeModuleName();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                changeModuleName();
            }
        });
        buttonCancel.addActionListener(e -> onCancel());

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(e -> onCancel(), KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);

    }

    private void onOK() {
        buttonOK.setEnabled(false);
        new Thread(() -> {
            String host = remoteHost.getText().trim();
            String username = loginName.getText().trim();
            String pwd = new String(password.getPassword()).trim();
            String remotePath = remoteDir.getText().trim();
            String filePathStr = modulePath.getText().trim();
            CommandUtils.command(buildCommand.getText().trim(), filePathStr, 60);
            if (uploadWar.isSelected()) {
                SFTPUtils.uploadFile(host,
                        username,
                        pwd,
                        22,
                        filePathStr + "/target/" + moduleName.getText().trim() + ".war",
                        remotePath,
                        true,
                        60 * 1000);

            }
            if (restartServer.isSelected()) {
                SSHUtils.execScript(host, username, pwd, 22, remotePath + "/..", restartScriptName);
            }
            buttonOK.setEnabled(true);

        }).start();
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }

    /**
     * 居中显示
     *
     * @param parentDimension
     */
    public void showCenter(Dimension parentDimension) {
        Dimension dialogDimension = getSize();
        int x = (int) ((parentDimension.getWidth() - dialogDimension.getWidth()) / 2);
        int y = (int) ((parentDimension.getHeight() - dialogDimension.getHeight()) / 2);
        setLocation(x, y);
    }

    public void changeTextInfo(String selectName, String filePath) {
        this.moduleName.setText(selectName);
        this.modulePath.setText(filePath);
    }

    public void changeModuleName() {
        String newModuleName = moduleName.getText().trim();
        this.remoteHost.setText(remoteHostTemp.replaceAll("\\{moduleName}", newModuleName));
        this.remoteDir.setText(remoteDirTemp.replaceAll("\\{moduleName}", newModuleName));

    }

    public static void main(String[] args) {
        UploadFileRemoteHostDialog dialog = new UploadFileRemoteHostDialog();
        dialog.pack();

        dialog.setVisible(true);
        System.exit(0);
    }
}
