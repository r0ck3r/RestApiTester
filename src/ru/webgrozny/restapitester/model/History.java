package ru.webgrozny.restapitester.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class History implements Serializable {
    private static int MAX = 20;

    private List<String> history;

    public History() {
        history = new ArrayList<>();
    }

    public void save(String element) {
        if(!element.equals("")) {
            history.remove(element);
            shrink();
            history.add(0, element);
        }
    }

    public List<String> getHistory() {
        return history;
    }

    private void shrink() {
        while(history.size() > MAX - 1) {
            history.remove(history.size() - 1);
        }
    }
}
