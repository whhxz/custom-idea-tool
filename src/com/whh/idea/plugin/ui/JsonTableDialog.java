package com.whh.idea.plugin.ui;

import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.ObservableList;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableView;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.stream.IntStream;

public class JsonTableDialog extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JPanel dataPanel;

    public JsonTableDialog() {

        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        buttonOK.addActionListener(e -> onOK());

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

        //添加jfxpandel
        initTreeTable();
    }

    private void initTreeTable(){
        final JFXPanel fxPanel = new JFXPanel();
        fxPanel.setLayout(new GridBagLayout());

        final TreeItem<String> childNode1 = new TreeItem<>("Child Node 1");
        final TreeItem<String> childNode2 = new TreeItem<>("Child Node 2");
        final TreeItem<String> childNode3 = new TreeItem<>("Child Node 3");
        final TreeItem<String> rootTree = new TreeItem<>("Root node");
        rootTree.setExpanded(true);
        rootTree.getChildren().setAll(childNode1, childNode2, childNode3);

        TreeTableColumn<String,String> column = new TreeTableColumn<>("Column");
        column.setCellValueFactory((TreeTableColumn.CellDataFeatures<String, String> p) ->
                new ReadOnlyStringWrapper(p.getValue().getValue()));

        final TreeTableView<String> treeTableView = new TreeTableView<>(rootTree);
        treeTableView.getColumns().add(column);
        treeTableView.setShowRoot(false);


        dataPanel.setLayout(new GridBagLayout());

        Group  root  =  new  Group();
        Scene  scene  =  new  Scene(root);
        root.getChildren().add(treeTableView);
        fxPanel.setScene(scene);
        dataPanel.add(fxPanel);
    }
    private static Scene createScene() {
        Group  root  =  new  Group();
        Scene  scene  =  new  Scene(root);
        Text text  =  new  Text();

        text.setX(40);
        text.setY(100);
        text.setFont(new Font(25));
        text.setText("Welcome JavaFX!");

        root.getChildren().add(text);

        return (scene);
    }


    private void onOK() {
        // add your code here
        dispose();
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }

    public static void main(String[] args) {
        JsonTableDialog dialog = new JsonTableDialog();
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }
}
