package ru.webgrozny.restapitester.controller;

import ru.webgrozny.restapitester.model.*;
import ru.webgrozny.restapitester.view.Window;

import javax.swing.*;
import javax.swing.text.JTextComponent;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.*;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Controller {
    private Window window = new Window();
    private Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
    private AutoCompleteJComboBox autoCompleteJComboBox;
    private AutoCompleteJComboBox autoCompleteJComboBoxProfiles;
    private Storage storage = Storage.getInstance();
    private History history = storage.getSavedContent().getHistory();

    public void start() {
        window.setVisible(true);
        window.jSplitPane.setDividerLocation(0.5);
        loadHistory();
        loadProfiles();
        autoCompleteJComboBox = new AutoCompleteJComboBox(window.jComboBoxHost);
        autoCompleteJComboBoxProfiles = new AutoCompleteJComboBox(window.jComboBoxProfile);
        loadRequestMethods();

        addListener();

        setContextMenuListener(window.jTextAreaInputJson);
        setContextMenuListener(window.jTextAreaResult);
        setContextMenuListener(window.jTextFieldHost);
        setContextMenuListener(window.jTextAreaResponseHeaders);
        setContextMenuListener(window.jTextAreaHeaders);

        setMethod(storage.getSavedContent().getActiveMethod());
        setInputJson(storage.getSavedContent().getBody());
        setHeaders(storage.getSavedContent().getHeaders());

        disableForGetAndDelete();
    }

    private void loadProfiles() {
        window.jComboBoxProfile.removeAllItems();
        Set<String> profiles = storage.getSavedContent().getProfiles().keySet();
        for(String profile : profiles) {
            window.jComboBoxProfile.addItem(profile);
        }
        try {
            window.jComboBoxProfile.setSelectedIndex(storage.getSavedContent().getSctiveProfile());
        } catch (Exception e) {}

    }

    private void loadHistory() {
        window.jComboBoxHost.setEditable(true);
        window.jComboBoxHost.removeAllItems();
        for(String item : history.getHistory()) {
            window.jComboBoxHost.addItem(item);
        }
        window.jTextFieldHost = (JTextField) window.jComboBoxHost.getEditor().getEditorComponent();
    }

    private void addListener() {
        window.jButtonSend.addActionListener(e -> {
            setEnabledTextFields(false);
            setResultText("Retrieving info...");
            sendJson().start();
        });

        window.jButtonResetHeaders.addActionListener(e -> setHeaders(getStringDefaultHeaders()));

        window.jButtonClearAndPasteInput.addActionListener(e -> {
            String content = getInputText();
            try {
                content = (String) clipboard.getData(DataFlavor.stringFlavor);
            } catch (Exception exc) {

            }
            setInputJson(content);
        });

        window.jButtonCopyResponseHeaders.addActionListener(e -> {
            StringSelection content = new StringSelection(getResponseHeaders());
            clipboard.setContents(content, null);
        });

        window.jButtonCopyResponseContent.addActionListener(e -> {
            StringSelection content = new StringSelection(getResultText());
            clipboard.setContents(content, null);
        });

        window.jComboBoxMethod.addItemListener(e -> disableForGetAndDelete());

        window.jButtonProfileSave.addActionListener(e -> {
            String profileName = getProfileName();
            if(profileName == null) {
                return;
            }
            Profile profile = getSelectedProfile();
            saveProfile(profile);
            storage.getSavedContent().getProfiles().put(getProfileName(), profile);
            loadProfiles();
            window.jComboBoxProfile.setSelectedItem(profileName);
            storage.getSavedContent().setSctiveProfile(window.jComboBoxProfile.getSelectedIndex());
            storage.save();
            autoCompleteJComboBoxProfiles.reload();
        });

        window.jButtonProfileLoad.addActionListener(e -> {
            Profile profile = getSelectedProfile();
            storage.getSavedContent().setSctiveProfile(window.jComboBoxProfile.getSelectedIndex());
            storage.save();
            loadProfile(profile);
        });

        window.jButtonProfileRemove.addActionListener(e -> {
            String name = getProfileName();
            storage.getSavedContent().getProfiles().remove(name);
            storage.save();
            loadProfiles();
            window.jComboBoxProfile.setSelectedItem("");
            autoCompleteJComboBoxProfiles.reload();
        });
    }

    private String getStringDefaultHeaders() {
        StringBuilder stringBuilder = new StringBuilder();
        List<String> headers = RequestSender.defaultHeaders;
        for(String header : headers) {
            stringBuilder.append(header).append("\r\n");
        }
        return stringBuilder.toString();
    }

    private void loadProfile(Profile profile) {
        setInputJson(profile.getBody());
        setHost(profile.getHost());
        setMethod(profile.getMethod());
        setHeaders(profile.getHeaders());
    }

    private void saveProfile(Profile profile) {
        profile.setBody(getInputText());
        profile.setHeaders(getHeaders());
        profile.setHost(getHost());
        profile.setMethod(getMethodIndex());
    }

    private Profile getSelectedProfile() {
        String name = getProfileName();
        Map<String, Profile> profiles = storage.getSavedContent().getProfiles();
        Profile profile = profiles.get(name);
        if(profile == null) {
            profile = new Profile();
            profile.setHeaders(getStringDefaultHeaders());
        }
        return profile;
    }

    private String getProfileName() {
        return (String) window.jComboBoxProfile.getSelectedItem();
    }

    private JTextComponent[] getFields () {
        return new JTextComponent[] {window.jTextAreaResult, window.jTextAreaResponseHeaders};
    }

    private void setEnabledTextFields(boolean enable) {
        for(JTextComponent component : getFields()) {
            component.setEnabled(enable);
        }
    }

    private void disableForGetAndDelete() {
        window.jTextAreaInputJson.setEnabled(RequestSender.sendBody((String) window.jComboBoxMethod.getSelectedItem()));
    }

    private void loadRequestMethods() {
        for(String method : RequestSender.methods) {
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
        return window.jTextAreaResult.getText();
    }

    private void setResultText(String text) {
        window.jTextAreaResult.setText(text);
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

    private void setHeaders(String headers) {
        window.jTextAreaHeaders.setText(headers);
    }

    private void setResponseHeaders(String responseHeaders) {
        window.jTextAreaResponseHeaders.setText(responseHeaders);
    }

    private Thread sendJson() {
        updateHistory();
        Runnable runnable = () -> {
            ServerAnswer result = new RequestSender().send(getHost(), getInputText(), getMethodIndex(), getHeaders());
            setResultText(result.getContent());
            setResponseHeaders(result.getHeaders());
            setEnabledTextFields(true);
        };
        return new Thread(runnable);
    }

    private void setContextMenuListener(JTextComponent jTextComponent) {
        new ContextMenu(jTextComponent);
    }

    private void updateHistory() {
        history.save(getHost());
        loadHistory();
        autoCompleteJComboBox.reload();

        storage.getSavedContent().setActiveMethod(getMethodIndex());
        storage.getSavedContent().setHeaders(getHeaders());
        storage.getSavedContent().setBody(getInputText());
        storage.save();
    }
}
