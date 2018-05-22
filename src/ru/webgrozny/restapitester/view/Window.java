package ru.webgrozny.restapitester.view;

import javax.swing.*;
import javax.swing.text.JTextComponent;
import java.awt.*;

public class Window extends JFrame {
    public JTextArea jTextAreaInputJson = new JTextArea();
    public JTextField jTextFieldHost = new JTextField();
    public JTextArea jTextFieldResult = new JTextArea();
    public JButton jButtonSend = new JButton("Send");
    public JComboBox<String> jComboBoxMethod = new JComboBox<String>();
    public JTextArea jTextAreaHeaders = new JTextArea();
    public JButton jButtonResetHeaders = new JButton("Reset");
    private JLabel jLabelHost = new JLabel("API URL:");
    private JLabel jLabelInput = new JLabel("Input JSON:");
    private JLabel jLabelResult = new JLabel("Answer from Server:");
    private JLabel jLabelMethod = new JLabel("Method:");
    private JLabel jLabelHeaders = new JLabel("Headers:");
    private JScrollPane jScrollPaneResult = new JScrollPane(jTextFieldResult);
    private JScrollPane jScrollPaneInput = new JScrollPane(jTextAreaInputJson);
    private JScrollPane jScrollPaneHeaders = new JScrollPane(jTextAreaHeaders);
    private GridBagLayout layoutManager;

    public Window() {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(400, 400);

        setPosition();

        setTitle("RestApiTester");
        jTextFieldResult.setEditable(false);

        layoutManager = new GridBagLayout();
        setLayout(layoutManager);

        add(jTextFieldHost);
        add(jScrollPaneInput);
        add(jButtonSend);
        add(jScrollPaneResult);
        add(jLabelHost);
        add(jLabelInput);
        add(jLabelResult);
        add(jComboBoxMethod);
        add(jLabelMethod);
        add(jLabelHeaders);
        add(jScrollPaneHeaders);
        add(jButtonResetHeaders);

        setConstraints();

        setContextMenuListener(jTextAreaInputJson);
        setContextMenuListener(jTextFieldResult);
        setContextMenuListener(jTextFieldHost);

        jScrollPaneInput.setPreferredSize(new Dimension(0, 0));
        jScrollPaneResult.setPreferredSize(new Dimension(0, 0));
        jScrollPaneHeaders.setPreferredSize(new Dimension(0, 0));
    }

    private void setPosition() {
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();

        int w = this.getSize().width;
        int h = this.getSize().height;
        int x = (dim.width-w)/2;
        int y = (dim.height-h)/2;


        this.setLocation(x, y);
    }

    private void setContextMenuListener(JTextComponent jTextComponent) {
        new ContextMenu(jTextComponent);
    }

    private void setConstraints() {
        GridBagConstraints gridBagConstraints;

        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 1;
        gridBagConstraints.weighty = 0.0;
        gridBagConstraints.weightx = 0.0;
        gridBagConstraints.anchor = GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new Insets(5, 5, 5, 5);
        layoutManager.setConstraints(jLabelHost, gridBagConstraints);

        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 1;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 0.0;
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new Insets(5, 5, 5, 5);
        layoutManager.setConstraints(jTextFieldHost, gridBagConstraints);

        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 1;
        gridBagConstraints.weighty = 0.0;
        gridBagConstraints.weightx = 0.0;
        gridBagConstraints.insets = new Insets(5, 5, 5, 5);
        layoutManager.setConstraints(jButtonSend, gridBagConstraints);

        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 1;
        gridBagConstraints.weighty = 0.0;
        gridBagConstraints.weightx = 0.0;
        gridBagConstraints.insets = new Insets(5, 5, 5, 5);
        layoutManager.setConstraints(jLabelMethod, gridBagConstraints);

        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.weighty = 0.0;
        gridBagConstraints.weightx = 0.0;
        gridBagConstraints.anchor = GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new Insets(5, 5, 5, 5);
        layoutManager.setConstraints(jComboBoxMethod, gridBagConstraints);

        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.weighty = 0.0;
        gridBagConstraints.weightx = 0.0;
        gridBagConstraints.anchor = GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new Insets(5, 5, 2, 5);
        layoutManager.setConstraints(jLabelInput, gridBagConstraints);

        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.weighty = 0.3;
        gridBagConstraints.weightx = 0.0;
        gridBagConstraints.fill = GridBagConstraints.BOTH;
        gridBagConstraints.insets = new Insets(2, 5, 5, 5);
        layoutManager.setConstraints(jScrollPaneInput, gridBagConstraints);

        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.weighty = 0.0;
        gridBagConstraints.weightx = 0.0;
        gridBagConstraints.anchor = GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new Insets(5, 5, 2, 5);
        layoutManager.setConstraints(jLabelHeaders, gridBagConstraints);

        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.weightx = 0.0;
        gridBagConstraints.weighty = 0.3;
        gridBagConstraints.fill = GridBagConstraints.BOTH;
        gridBagConstraints.insets = new Insets(2, 5, 2, 5);
        layoutManager.setConstraints(jScrollPaneHeaders, gridBagConstraints);

        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.weightx = 0.0;
        gridBagConstraints.weighty = 0.0;
        gridBagConstraints.anchor = GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new Insets(2, 5, 5, 5);
        layoutManager.setConstraints(jButtonResetHeaders, gridBagConstraints);

        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.weighty = 0.0;
        gridBagConstraints.weightx = 0.0;
        gridBagConstraints.anchor = GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new Insets(5, 5, 2, 5);
        layoutManager.setConstraints(jLabelResult, gridBagConstraints);

        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.weighty = 0.3;
        gridBagConstraints.weightx = 0.0;
        gridBagConstraints.fill = GridBagConstraints.BOTH;
        gridBagConstraints.insets = new Insets(2, 5, 5, 5);
        layoutManager.setConstraints(jScrollPaneResult, gridBagConstraints);
    }
}
