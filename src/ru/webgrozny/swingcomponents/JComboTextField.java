package ru.webgrozny.swingcomponents;

import javax.swing.*;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class JComboTextField extends JComboBox<String> implements KeyListener, PopupMenuListener {
    private List<String> items;
    private JTextField jTextField;
    private String previousText = "";

    public JComboTextField(List<String> items) {
        setEditable(true);
        jTextField = (JTextField) getEditor().getEditorComponent();
        this.items = items;
        setItems(items);
        addListeners();
    }

    public JComboTextField() {
        this(Collections.emptyList());
    }

    public JTextField getJTextField() {
        return jTextField;
    }

    private List<String> searchItems(String start) {
        if(start.length() == 0) {
            return items;
        } else {
            List<String> result = new ArrayList<>();
            items.stream().forEach( s -> { if(s.startsWith(start)) result.add(s); } );
            return result;
        }
    }

    private void doSearch() {
        if(jTextField.getSelectedText() != null) {
            return;
        }
        hidePopup();
        String text = jTextField.getText();
        if(text.length() > 0 && text.length() > previousText.length()) {
            List<String> result = searchItems(text);
            if(result.size() > 0 && !text.equals(result.get(0))) {

                addItemsFromList(result);
                showPopup();
                String hintText = result.get(0);
                jTextField.setText(hintText);
                jTextField.setSelectionStart(text.length());
            }
        }
        previousText = text;
    }

    private void addListeners() {
        jTextField.addKeyListener(this);
        addPopupMenuListener(this);
    }

    public void setItems(List<String> items) {
        this.items = items;
        addItemsFromList(items);
        if(items.size() > 0) {
            previousText = items.get(0);
        }
    }

    private void addItemsFromList(List<String> items) {
        removeAllItems();
        for(String item : items) {
            addItem(item);
        }
    }

    private void loadDefaultItems() {
        addItemsFromList(items);
    }

    private void beforeShowPopup() {
        if(jTextField.getText().length() == 0) {
            loadDefaultItems();
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {
        if(!e.isActionKey()) {
            doSearch();
        }
    }

    @Override
    public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
        beforeShowPopup();
    }

    @Override
    public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {

    }

    @Override
    public void popupMenuCanceled(PopupMenuEvent e) {

    }
}
