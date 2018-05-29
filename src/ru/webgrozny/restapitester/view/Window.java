package ru.webgrozny.restapitester.view;

import ru.webgrozny.swingcomponents.JComboTextField;

import javax.swing.*;
import java.awt.*;

public class Window extends JFrame {
    private static final int TAB_SIZE = 4;

    public JTextArea jTextAreaInputJson = new JTextArea();
    public JComboTextField jComboTextFieldHost = new JComboTextField();
    public JTextArea jTextAreaResult = new JTextArea();
    public JButton jButtonSend = new JButton("Send");
    public JComboBox<String> jComboBoxMethod = new JComboBox<>();
    public JTextArea jTextAreaHeaders = new JTextArea();
    public JButton jButtonResetHeaders = new JButton("Load default");
    public JTextArea jTextAreaResponseHeaders = new JTextArea();
    public JButton jButtonCopyResponseHeaders = new JButton("Copy");
    public JButton jButtonCopyResponseContent = new JButton("Copy");
    public JButton jButtonClearAndPasteInput = new JButton("Clear and paste");
    private JPanel jPanelProfile = new JPanel();
    private JLabel jLabelProfile = new JLabel("Profile: ");
    public JComboTextField jComboBoxProfile = new JComboTextField();
    public JButton jButtonProfileLoad = new JButton("Load");
    public JButton jButtonProfileSave = new JButton("Save");
    public JButton jButtonProfileRemove = new JButton("Delete");
    public JLabel jLabelStatus = new JLabel();
    private JLabel jLabelHost = new JLabel("API URL:");
    private JLabel jLabelInput = new JLabel("Request body:");
    private JLabel jLabelResult = new JLabel("Answer from Server:");
    private JLabel jLabelMethod = new JLabel("Method:");
    private JLabel jLabelHeaders = new JLabel("Headers:");
    private JLabel jLabelResponseHeaders = new JLabel("Response headers:");
    private JScrollPane jScrollPaneResult = new JScrollPane(jTextAreaResult);
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

        setLayout(new BorderLayout());

        jComboBoxProfile.setEditable(true);
        jTextAreaResult.setEditable(false);
        jTextAreaResponseHeaders.setEditable(false);

        layoutManager = new GridBagLayout();
        JPanel jPanel = new JPanel();
        add(jPanel, BorderLayout.CENTER);

        JPanel jPanelStatus = new JPanel();
        jPanelStatus.add(jLabelStatus);
        GridBagLayout gridBagLayoutStatus = new GridBagLayout();
        jPanelStatus.setLayout(gridBagLayoutStatus);
        GridBagConstraints gridBagConstraintsStatus = new GridBagConstraints();
        gridBagConstraintsStatus.anchor = GridBagConstraints.LINE_END;
        gridBagConstraintsStatus.weightx = 1.0;
        gridBagConstraintsStatus.insets = new Insets(2, 5, 2, 5);
        gridBagLayoutStatus.setConstraints(jLabelStatus, gridBagConstraintsStatus);
        add(jPanelStatus, BorderLayout.SOUTH);

        jPanel.setLayout(layoutManager);

        jPanel.add(jComboBoxMethod);
        jPanel.add(jLabelMethod);
        jPanel.add(jScrollPaneHeaders);
        jPanel.add(jPanelTop);
        jPanel.add(jSplitPane);
        jPanel.add(jPanelProfile);
        configProfilePane();

        jSplitPane.add(createTopJSplitPane());
        jSplitPane.add(createBottomJSplitPane());

        setTopPaneConstraints();
        setConstraints();

        jScrollPaneInput.setPreferredSize(new Dimension(0, 0));
        jScrollPaneResult.setPreferredSize(new Dimension(0, 0));
        jScrollPaneHeaders.setPreferredSize(new Dimension(0, 0));
        jScrollPaneResponseHeaders.setPreferredSize(new Dimension(0, 0));

        fixTextAreaFonts();
    }

    private void configProfilePane() {
        GridBagLayout gridBagLayout = new GridBagLayout();
        jPanelProfile.setLayout(gridBagLayout);

        jPanelProfile.add(jLabelProfile);
        jPanelProfile.add(jComboBoxProfile);
        jPanelProfile.add(jButtonProfileLoad);
        jPanelProfile.add(jButtonProfileSave);
        jPanelProfile.add(jButtonProfileRemove);

        GridBagConstraints gridBagConstraints;

        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.weightx = 0.0;
        gridBagConstraints.insets = new Insets(5, 0, 5, 10);
        gridBagConstraints.anchor = GridBagConstraints.LINE_START;
        gridBagLayout.setConstraints(jLabelProfile, gridBagConstraints);

        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.fill = GridBagConstraints.BOTH;
        gridBagConstraints.insets = new Insets(5, 5, 5, 5);
        gridBagLayout.setConstraints(jComboBoxProfile, gridBagConstraints);

        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.weightx = 0.0;
        gridBagConstraints.insets = new Insets(5, 5, 5, 5);
        gridBagLayout.setConstraints(jButtonProfileLoad, gridBagConstraints);

        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.weightx = 0.0;
        gridBagConstraints.insets = new Insets(5, 5, 5, 5);
        gridBagLayout.setConstraints(jButtonProfileSave, gridBagConstraints);

        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.weightx = 0.0;
        gridBagConstraints.insets = new Insets(5, 5, 5, 0);
        gridBagLayout.setConstraints(jButtonProfileRemove, gridBagConstraints);
    }

    private void fixTextAreaFonts() {
        Font set = jLabelHost.getFont();
        Font font = new Font(Font.MONOSPACED, Font.PLAIN, set.getSize());

        jTextAreaInputJson.setTabSize(4);
        jTextAreaInputJson.setFont(font);

        jTextAreaHeaders.setTabSize(TAB_SIZE);
        jTextAreaHeaders.setFont(font);

        jTextAreaResponseHeaders.setTabSize(4);
        jTextAreaResponseHeaders.setFont(font);

        jTextAreaResult.setTabSize(4);
        jTextAreaResult.setFont(font);
    }

    private void setPosition() {
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();

        int w = this.getSize().width;
        int h = this.getSize().height;
        int x = (dim.width-w)/2;
        int y = (dim.height-h)/2;

        this.setLocation(x, y);
    }



    private void setTopPaneConstraints() {
        GridBagLayout gridBagLayout = new GridBagLayout();
        jPanelTop.setLayout(gridBagLayout);

        jPanelTop.add(jLabelHost);
        jPanelTop.add(jComboTextFieldHost);
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
        gridBagLayout.setConstraints(jComboTextFieldHost, gridBagConstraints);

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
        gridBagConstraints.gridwidth = 6;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 0.0;
        gridBagConstraints.anchor = GridBagConstraints.LINE_START;
        gridBagConstraints.fill = GridBagConstraints.BOTH;
        gridBagConstraints.insets = new Insets(5, 5, 5, 5);
        layoutManager.setConstraints(jPanelProfile, gridBagConstraints);

        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 1;
        gridBagConstraints.weighty = 0.0;
        gridBagConstraints.weightx = 0.0;
        gridBagConstraints.insets = new Insets(5, 5, 5, 5);
        layoutManager.setConstraints(jLabelMethod, gridBagConstraints);

        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.weighty = 0.0;
        gridBagConstraints.weightx = 0.0;
        gridBagConstraints.anchor = GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new Insets(5, 5, 5, 5);
        layoutManager.setConstraints(jComboBoxMethod, gridBagConstraints);

        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridwidth = 6;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new Insets(5, 5, 5, 5);
        gridBagConstraints.fill = GridBagConstraints.BOTH;
        layoutManager.setConstraints(jSplitPane, gridBagConstraints);
    }

    static {
        try { UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); } catch (Exception e) {}
    }
}
