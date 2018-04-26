package com.whh.idea.plugin.utils;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.embed.swing.JFXPanel;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * SimpleSwingBrowser
 * Created by xuzhuo on 2018/1/26.
 */
public class SimpleSwingBrowser extends JFrame {

    private final JFXPanel jfxPanel = new JFXPanel();
    private WebEngine engine;

    private final JPanel panel = new JPanel(new BorderLayout());
    private final JLabel lblStatus = new JLabel();

    private final JButton btnGo = new JButton("Go");
    private final JTextField txtURL = new JTextField();
    private final JProgressBar progressBar = new JProgressBar();

    public static void main(String[] args) {
        SimpleSwingBrowser simpleSwingBrowser = new SimpleSwingBrowser();
        simpleSwingBrowser.setVisible(true);
    }

    public SimpleSwingBrowser() {
        super();
        initComponents();
    }

    private void initComponents() {
        createScene();

        ActionListener al = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadURL(txtURL.getText());
            }
        };

        btnGo.addActionListener(al);
        txtURL.addActionListener(al);

        progressBar.setPreferredSize(new Dimension(150, 18));
        progressBar.setStringPainted(true);

        JPanel topBar = new JPanel(new BorderLayout(5, 0));
        topBar.setBorder(BorderFactory.createEmptyBorder(3, 5, 3, 5));
        topBar.add(txtURL, BorderLayout.CENTER);
        topBar.add(btnGo, BorderLayout.EAST);

        JPanel statusBar = new JPanel(new BorderLayout(5, 0));
        statusBar.setBorder(BorderFactory.createEmptyBorder(3, 5, 3, 5));
        statusBar.add(lblStatus, BorderLayout.CENTER);
        statusBar.add(progressBar, BorderLayout.EAST);

        panel.add(topBar, BorderLayout.NORTH);
        panel.add(jfxPanel, BorderLayout.CENTER);
        panel.add(statusBar, BorderLayout.SOUTH);

        getContentPane().add(panel);

        setPreferredSize(new Dimension(1024, 600));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
    }
    public void loadURL(final String url) {
        Platform.runLater(new Runnable() {
            @Override public void run() {
                String tmp = toURL(url);

                if (url == null) {
                    tmp = toURL("http://" + url);
                }

                engine.load(tmp);
            }
        });
    }

    private static String toURL(String str) {
        try {
            return new URL(str).toExternalForm();
        } catch (MalformedURLException exception) {
            return null;
        }
    }
    private void createScene() {

        Platform.runLater(new Runnable() {
            @Override
            public void run() {

                WebView view = new WebView();
                engine = view.getEngine();

                engine.titleProperty().addListener(new ChangeListener<String>() {
                    @Override
                    public void changed(ObservableValue<? extends String> observable,
                                        String oldValue, final String newValue) {
                        SwingUtilities.invokeLater(new Runnable() {
                            @Override
                            public void run() {
                                SimpleSwingBrowser.this.setTitle(newValue);
                            }
                        });
                    }
                });
            }
        });
    }
}

