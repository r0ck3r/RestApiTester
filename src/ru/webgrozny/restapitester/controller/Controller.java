package ru.webgrozny.restapitester.controller;

import ru.webgrozny.restapitester.model.PostJsonSender;
import ru.webgrozny.restapitester.model.ServerAnswer;
import ru.webgrozny.restapitester.model.UserDataSaver;
import ru.webgrozny.restapitester.view.Window;

import javax.swing.text.JTextComponent;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.List;

public class Controller {
    Window window = new Window();
    Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();

    public void start() {
        window.setVisible(true);
        window.jSplitPane.setDividerLocation(0.5);
        setRequestMethods();
        addListener();
        setHost(UserDataSaver.getInstance().getHost());
        setMethod(UserDataSaver.getInstance().getMethod());
        setInputJson(UserDataSaver.getInstance().getJson());
        setHeaders(UserDataSaver.getInstance().getHeaders());
    }

    public void addListener() {
        window.jButtonSend.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setEnabledTextFields(false);
                setResultText("Retrieving info...");
                sendJson().start();
            }
        });

        window.jButtonResetHeaders.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setHeaders(PostJsonSender.defaultHeaders);
            }
        });

        window.jButtonClearAndPasteInput.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String content = getInputText();
                try {
                    content = (String) clipboard.getData(DataFlavor.stringFlavor);
                } catch (UnsupportedFlavorException e1) {

                } catch (IOException e1) {

                }
                setInputJson(content);
            }
        });

        window.jButtonCopyResponseHeaders.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                StringSelection content = new StringSelection(getResponseHeaders());
                clipboard.setContents(content, null);
            }
        });

        window.jButtonCopyResponseContent.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                StringSelection content = new StringSelection(getResultText());
                clipboard.setContents(content, null);
            }
        });
    }

    private JTextComponent[] getFields () {
        return new JTextComponent[] {window.jTextFieldResult, window.jTextAreaResponseHeaders};
    }

    private void setEnabledTextFields(boolean enable) {
        for(JTextComponent component : getFields()) {
            component.setEnabled(enable);
        }
    }

    private void setRequestMethods() {
        for(String method : PostJsonSender.methods) {
            window.jComboBoxMethod.addItem(method);
        }
    }

    private String getInputText() {
        return window.jTextAreaInputJson.getText();
    }

    private String getHost() {
        return window.jTextFieldHost.getText();
    }

    private int getMethodIndex() {
        return window.jComboBoxMethod.getSelectedIndex();
    }

    private String getHeaders() {
        return window.jTextAreaHeaders.getText();
    }

    private String getResponseHeaders() {
        return window.jTextAreaResponseHeaders.getText();
    }

    private String getResultText() {
        return window.jTextFieldResult.getText();
    }

    private void setResultText(String text) {
        window.jTextFieldResult.setText(text);
    }

    private void setHost(String host) {
        window.jTextFieldHost.setText(host);
    }

    private void setInputJson(String json) {
        window.jTextAreaInputJson.setText(json);
    }

    private void setMethod(int index) {
        window.jComboBoxMethod.setSelectedIndex(index);
    }

    private void setHeaders(List<String> headers) {
        StringBuilder stringBuilder = new StringBuilder();
        for(String header : headers) {
            stringBuilder.append(header + "\r\n");
        }
        window.jTextAreaHeaders.setText(stringBuilder.toString());
    }

    private void setResponseHeaders(String responseHeaders) {
        window.jTextAreaResponseHeaders.setText(responseHeaders);
    }

    private Thread sendJson() {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                ServerAnswer result = new PostJsonSender().send(getHost(), getInputText(), getMethodIndex(), getHeaders());
                setResultText(result.getContent());
                setResponseHeaders(result.getHeaders());
                setEnabledTextFields(true);
            }
        };
        return new Thread(runnable);
    }
}
