package ru.webgrozny.restapitester.controller;

import ru.webgrozny.restapitester.model.PostJsonSender;
import ru.webgrozny.restapitester.model.ServerAnswer;
import ru.webgrozny.restapitester.model.UserDataSaver;
import ru.webgrozny.restapitester.view.Window;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class Controller {
    Window window = new Window();

    public void start() {
        window.setVisible(true);
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

    private Thread sendJson() {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                ServerAnswer result = new PostJsonSender().send(getHost(), getInputText(), getMethodIndex(), getHeaders());
                setResultText(result.getContent());
            }
        };
        return new Thread(runnable);
    }
}
