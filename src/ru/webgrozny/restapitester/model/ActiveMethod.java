package ru.webgrozny.restapitester.model;

import java.io.Serializable;

public class ActiveMethod implements Serializable {
    private int activeMethod = 0;

    public int getActiveMethod() {
        return activeMethod;
    }

    public void setActiveMethod(int activeMethod) {
        this.activeMethod = activeMethod;
    }
}
