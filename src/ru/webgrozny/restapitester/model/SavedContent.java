package ru.webgrozny.restapitester.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SavedContent implements Serializable {
    private History history = new History();
    private int activeMethod = 0;
    private String headers = "";
    private String body = "";
    private Map<String, Profile> profiles = new HashMap<>();
    private int sctiveProfile = 0;

    public History getHistory() {
        return history;
    }

    public void setHistory(History history) {
        this.history = history;
    }

    public int getActiveMethod() {
        return activeMethod;
    }

    public void setActiveMethod(int activeMethod) {
        this.activeMethod = activeMethod;
    }

    public String getHeaders() {
        return headers;
    }

    public void setHeaders(String headers) {
        this.headers = headers;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Map<String, Profile> getProfiles() {
        return profiles;
    }

    public int getSctiveProfile() {
        return sctiveProfile;
    }

    public void setSctiveProfile(int sctiveProfile) {
        this.sctiveProfile = sctiveProfile;
    }
}
