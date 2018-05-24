package ru.webgrozny.restapitester.controller;

import javax.swing.*;
import javax.swing.event.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;
import java.util.stream.Stream;

public class AutoCompleteJComboBox {
    private List<String> elements;
    private JComboBox<String> jComboBox;
    private JTextField jTextField;
    private String previous = "";
    private Semaphore semaphore = new Semaphore(1, true);

    public AutoCompleteJComboBox(JComboBox<String> jComboBox) {
        this.jComboBox = jComboBox;
        jComboBox.setEditable(true);
        jTextField = (JTextField) jComboBox.getEditor().getEditorComponent();
        previous = jTextField.getText();
        loadElements();
        addListener();
    }

    private void loadElements() {
        elements = new ArrayList<>();
        int count = jComboBox.getItemCount();
        for(int i = 0; i < count; i++) {
            elements.add(jComboBox.getItemAt(i));
        }
    }

    private List<String> search(String starts) {
        if(starts.length() == 0) {
            return elements;
        }
        List<String> found = new ArrayList<>();
        Stream<String> foundStream = elements.stream().filter( s -> s.startsWith(starts) );
        foundStream.forEach( s -> found.add(s) );
        return found;
    }

    private void reloadComboBox(List<String> elements) {
        jComboBox.removeAllItems();
        for(String element : elements) {
            jComboBox.addItem(element);
        }
    }

    private void reloadComboBox() {
        try {
            semaphore.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        jComboBox.hidePopup();
        String text = jTextField.getText();
        String selected = jTextField.getSelectedText();
        if(selected == null) {
            int position = jTextField.getCaretPosition();
            if (!text.equals(previous)) {
                jTextField.setEditable(false);
                List<String> found = search(text);
                reloadComboBox(found);
                String foundText = text;
                if (found.size() > 0 && !found.get(0).equals(text) && text.length() > 0 && previous.length() < text.length()) {
                    jComboBox.showPopup();
                    foundText = found.get(0);
                }

                jTextField.setText(foundText);

                jTextField.setEditable(true);
                jTextField.setCaretPosition(position);
                if (foundText.length() > text.length()) {
                    jTextField.setSelectionStart(position);
                    jTextField.setSelectionEnd(foundText.length());
                }
                previous = text;
            }
        }
        semaphore.release();
    }

    private void addListener() {
        jTextField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                if(!e.isActionKey()) {
                    reloadComboBox();
                }
            }
        });

        jComboBox.addPopupMenuListener(new PopupMenuListener() {
            @Override
            public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
                if (jTextField.getText().length() == 0) {
                    reloadComboBox(elements);
                }
            }

            @Override
            public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {

            }

            @Override
            public void popupMenuCanceled(PopupMenuEvent e) {

            }
        });
    }

    public void reload() {
        loadElements();
    }
}
