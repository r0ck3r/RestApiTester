package ru.webgrozny.restapitester.view;

import javax.swing.*;
import javax.swing.text.JTextComponent;
import java.awt.*;
import java.awt.datatransfer.*;
import java.awt.event.*;
import java.io.IOException;

public class ContextMenu {
    private JPopupMenu jPopupMenu = new JPopupMenu();
    private JMenuItem selectAll = new JMenuItem("Select All");
    private JMenuItem cut = new JMenuItem("Cut");
    private JMenuItem copy = new JMenuItem("Copy");
    private JMenuItem paste = new JMenuItem("Paste");
    private JMenuItem delete = new JMenuItem("Delete");
    private JTextComponent jTextComponent;
    private Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();

    private KeyStroke CtrlX = KeyStroke.getKeyStroke(KeyEvent.VK_X, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask());
    private KeyStroke CtrlC = KeyStroke.getKeyStroke(KeyEvent.VK_C, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask());
    private KeyStroke CtrlV = KeyStroke.getKeyStroke(KeyEvent.VK_V, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask());
    private KeyStroke CtrlA = KeyStroke.getKeyStroke(KeyEvent.VK_A, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask());

    public ContextMenu(JTextComponent jTextComponent) {
        this.jTextComponent = jTextComponent;
        initSubMenus();
        initMenu();
        jTextComponent.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if(e.isPopupTrigger()) {
                    displayMenu(e.getX(), e.getY());
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if(e.isPopupTrigger()) {
                    displayMenu(e.getX(), e.getY());
                }
            }
        });
    }

    public void initMenu() {
        jPopupMenu.add(selectAll);
        jPopupMenu.addSeparator();
        jPopupMenu.add(cut);
        jPopupMenu.add(copy);
        jPopupMenu.add(paste);
        jPopupMenu.add(delete);
    }

    private void initSubMenus() {
        selectAll.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selectAll();
            }
        });
        selectAll.setAccelerator(CtrlA);

        cut.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cut();
            }
        });
        cut.setAccelerator(CtrlX);

        copy.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                copy();
            }
        });
        copy.setAccelerator(CtrlC);

        paste.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                paste();
            }
        });
        paste.setAccelerator(CtrlV);

        delete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                delete();
            }
        });
    }

    private void checkMenuElements() {
        String selected = jTextComponent.getSelectedText();
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        if(selected == null) {
            copy.setEnabled(false);
            cut.setEnabled(false);
            delete.setEnabled(false);
        } else {
            copy.setEnabled(true);
            cut.setEnabled(true);
            delete.setEnabled(true);
        }
        String clipboardText = getClipboardString();
        if(clipboardText == null || clipboardText.isEmpty()) {
            paste.setEnabled(false);
        } else {
            paste.setEnabled(true);
        }
        if(jTextComponent.getText() == null || jTextComponent.getText().isEmpty()) {
            selectAll.setEnabled(false);
        } else {
            selectAll.setEnabled(true);
        }
        if(!jTextComponent.isEditable()) {
            cut.setEnabled(false);
            paste.setEnabled(false);
            delete.setEnabled(false);
        }
    }

    private String getClipboardString() {

        String clipboardText = null;
        try {
            clipboardText = (String) clipboard.getData(DataFlavor.stringFlavor);
        } catch (UnsupportedFlavorException e) {

        } catch (IOException e) {

        }
        return clipboardText;
    }

    private void displayMenu(int x, int y) {
        checkMenuElements();
        jPopupMenu.show(jTextComponent, x, y);
    }

    private void cut() {
        copy();
        delete();
    }

    private void copy() {
        String selectedText = jTextComponent.getSelectedText();
        StringSelection stringSelection = new StringSelection(selectedText);
        clipboard.setContents(stringSelection, null);
    }

    private void paste() {
        StringBuilder text = new StringBuilder(jTextComponent.getText());
        int position = jTextComponent.getCaretPosition();
        String clipboardText = getClipboardString();
        jTextComponent.setText(text.insert(position, clipboardText).toString());
    }

    private void delete() {
        int start = jTextComponent.getSelectionStart();
        int end = jTextComponent.getSelectionEnd();
        String text = jTextComponent.getText();
        jTextComponent.setText(text.substring(0, start) + text.substring(end));
    }

    private void selectAll() {
        jTextComponent.selectAll();
    }
}
