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
    public JButton jButtonResetHeaders = new JButton("Restore default");
    public JTextArea jTextAreaResponseHeaders = new JTextArea();
    public JButton jButtonCopyResponseHeaders = new JButton("Copy");
    public JButton jButtonCopyResponseContent = new JButton("Copy");
    public JButton jButtonClearAndPasteInput = new JButton("Clear and paste");
    private JLabel jLabelHost = new JLabel("API URL:");
    private JLabel jLabelInput = new JLabel("Input JSON:");
    private JLabel jLabelResult = new JLabel("Answer from Server:");
    private JLabel jLabelMethod = new JLabel("Method:");
    private JLabel jLabelHeaders = new JLabel("Headers:");
    private JLabel jLabelResponseHeaders = new JLabel("Response headers:");
    private JScrollPane jScrollPaneResult = new JScrollPane(jTextFieldResult);
    private JScrollPane jScrollPaneInput = new JScrollPane(jTextAreaInputJson);
    private JScrollPane jScrollPaneHeaders = new JScrollPane(jTextAreaHeaders);
    private JScrollPane jScrollPaneResponseHeaders = new JScrollPane(jTextAreaResponseHeaders);
    private GridBagLayout layoutManager;
    private JPanel jPanelTop = new JPanel();
    public JSplitPane jSplitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);

    public Window() {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(700, 600);

        setPosition();

        setTitle("RestApiTester");
        jTextFieldResult.setEditable(false);
        jTextAreaResponseHeaders.setEditable(false);

        layoutManager = new GridBagLayout();
        setLayout(layoutManager);

        add(jComboBoxMethod);
        add(jLabelMethod);
        add(jScrollPaneHeaders);
        add(jPanelTop);
        add(jSplitPane);

        JPanel topSubPanel = createTopJSplitPane();
        JPanel bottomSubPanel = createBottomJSplitPane();

        jSplitPane.add(topSubPanel);
        jSplitPane.add(bottomSubPanel);
        int max = jSplitPane.getMaximumDividerLocation();
        int min = jSplitPane.getMinimumDividerLocation();
        int size = jSplitPane.getDividerSize();

        setTopPaneConstraints();
        setConstraints();

        setContextMenuListener(jTextAreaInputJson);
        setContextMenuListener(jTextFieldResult);
        setContextMenuListener(jTextFieldHost);
        setContextMenuListener(jTextAreaResponseHeaders);
        setContextMenuListener(jTextAreaHeaders);

        jScrollPaneInput.setPreferredSize(new Dimension(0, 0));
        jScrollPaneResult.setPreferredSize(new Dimension(0, 0));
        jScrollPaneHeaders.setPreferredSize(new Dimension(0, 0));
        jScrollPaneResponseHeaders.setPreferredSize(new Dimension(0, 0));
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

    private void setTopPaneConstraints() {
        GridBagLayout gridBagLayout = new GridBagLayout();
        jPanelTop.setLayout(gridBagLayout);

        jPanelTop.add(jLabelHost);
        jPanelTop.add(jTextFieldHost);
        jPanelTop.add(jButtonSend);

        GridBagConstraints gridBagConstraints;
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 1;
        gridBagConstraints.weighty = 0.0;
        gridBagConstraints.weightx = 0.0;
        gridBagConstraints.anchor = GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new Insets(5, 5, 5, 5);
        gridBagLayout.setConstraints(jLabelHost, gridBagConstraints);

        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 1;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 0.0;
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new Insets(5, 5, 5, 5);
        gridBagLayout.setConstraints(jTextFieldHost, gridBagConstraints);

        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 1;
        gridBagConstraints.weighty = 0.0;
        gridBagConstraints.weightx = 0.0;
        gridBagConstraints.anchor = GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new Insets(5, 5, 5, 5);
        gridBagLayout.setConstraints(jButtonSend, gridBagConstraints);
    }

    private JPanel createTopJSplitPane() {
        JPanel jPanel = new JPanel();
        GridBagLayout gridBagLayout = new GridBagLayout();
        jPanel.setLayout(gridBagLayout);

        jPanel.add(jLabelHeaders);
        jPanel.add(jButtonResetHeaders);
        jPanel.add(jScrollPaneHeaders);

        jPanel.add(jLabelResponseHeaders);
        jPanel.add(jButtonCopyResponseHeaders);
        jPanel.add(jScrollPaneResponseHeaders);

        GridBagConstraints gridBagConstraints;

        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 1;
        gridBagConstraints.weightx = 0.0;
        gridBagConstraints.weighty = 0.0;
        gridBagConstraints.insets = new Insets(5, 5, 2, 5);
        gridBagConstraints.anchor = GridBagConstraints.LINE_START;
        gridBagLayout.setConstraints(jLabelHeaders, gridBagConstraints);

        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 1;
        gridBagConstraints.weightx = 0.0;
        gridBagConstraints.weighty = 0.0;
        gridBagConstraints.insets = new Insets(5, 5, 2, 5);
        gridBagConstraints.anchor = GridBagConstraints.LINE_END;
        gridBagLayout.setConstraints(jButtonResetHeaders, gridBagConstraints);

        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 1;
        gridBagConstraints.weightx = 0.5;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new Insets(2, 5, 5, 5);
        gridBagConstraints.fill = GridBagConstraints.BOTH;
        gridBagLayout.setConstraints(jScrollPaneHeaders, gridBagConstraints);

        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 1;
        gridBagConstraints.weightx = 0.0;
        gridBagConstraints.weighty = 0.0;
        gridBagConstraints.insets = new Insets(5, 5, 2, 5);
        gridBagConstraints.anchor = GridBagConstraints.LINE_START;
        gridBagLayout.setConstraints(jLabelResponseHeaders, gridBagConstraints);

        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 1;
        gridBagConstraints.weightx = 0.0;
        gridBagConstraints.weighty = 0.0;
        gridBagConstraints.insets = new Insets(5, 5, 2, 5);
        gridBagConstraints.anchor = GridBagConstraints.LINE_END;
        gridBagLayout.setConstraints(jButtonCopyResponseHeaders, gridBagConstraints);

        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 1;
        gridBagConstraints.weightx = 0.5;
        gridBagConstraints.weighty = 1;
        gridBagConstraints.insets = new Insets(2, 5, 5, 5);
        gridBagConstraints.fill = GridBagConstraints.BOTH;
        gridBagLayout.setConstraints(jScrollPaneResponseHeaders, gridBagConstraints);

        return jPanel;
    }

    private JPanel createBottomJSplitPane() {
        JPanel jPanel = new JPanel();
        GridBagLayout gridBagLayout = new GridBagLayout();
        jPanel.setLayout(gridBagLayout);

        jPanel.add(jLabelInput);
        jPanel.add(jButtonClearAndPasteInput);
        jPanel.add(jScrollPaneInput);

        jPanel.add(jLabelResult);
        jPanel.add(jButtonCopyResponseContent);
        jPanel.add(jScrollPaneResult);

        GridBagConstraints gridBagConstraints;

        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 1;
        gridBagConstraints.weightx = 0.0;
        gridBagConstraints.weighty = 0.0;
        gridBagConstraints.insets = new Insets(5, 5, 2, 5);
        gridBagConstraints.anchor = GridBagConstraints.LINE_START;
        gridBagLayout.setConstraints(jLabelInput, gridBagConstraints);

        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 1;
        gridBagConstraints.weightx = 0.0;
        gridBagConstraints.weighty = 0.0;
        gridBagConstraints.insets = new Insets(5, 5, 2, 5);
        gridBagConstraints.anchor = GridBagConstraints.LINE_END;
        gridBagLayout.setConstraints(jButtonClearAndPasteInput, gridBagConstraints);

        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 1;
        gridBagConstraints.weightx = 0.5;
        gridBagConstraints.weighty = 1;
        gridBagConstraints.insets = new Insets(2, 5, 5, 5);
        gridBagConstraints.fill = GridBagConstraints.BOTH;
        gridBagLayout.setConstraints(jScrollPaneInput, gridBagConstraints);

        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 1;
        gridBagConstraints.weightx = 0.0;
        gridBagConstraints.weighty = 0.0;
        gridBagConstraints.insets = new Insets(5, 5, 2, 5);
        gridBagConstraints.anchor = GridBagConstraints.LINE_START;
        gridBagLayout.setConstraints(jLabelResult, gridBagConstraints);

        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 1;
        gridBagConstraints.weightx = 0.0;
        gridBagConstraints.weighty = 0.0;
        gridBagConstraints.insets = new Insets(5, 5, 2, 5);
        gridBagConstraints.anchor = GridBagConstraints.LINE_END;
        gridBagLayout.setConstraints(jButtonCopyResponseContent, gridBagConstraints);

        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 1;
        gridBagConstraints.weightx = 0.5;
        gridBagConstraints.weighty = 1;
        gridBagConstraints.insets = new Insets(2, 5, 5, 5);
        gridBagConstraints.fill = GridBagConstraints.BOTH;
        gridBagLayout.setConstraints(jScrollPaneResult, gridBagConstraints);

        return jPanel;
    }

    private void setConstraints() {
        GridBagConstraints gridBagConstraints;

        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 6;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 0.0;
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        layoutManager.setConstraints(jPanelTop, gridBagConstraints);

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
        gridBagConstraints.gridwidth = 6;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new Insets(5, 5, 5, 5);
        gridBagConstraints.fill = GridBagConstraints.BOTH;
        layoutManager.setConstraints(jSplitPane, gridBagConstraints);
    }
}
