package ru.webgrozny.restapitester.model;

public class ServerAnswer {
    private String content;
    private String headers;

    public ServerAnswer(String content, String headers) {
        this.content = content;
        this.headers = headers;
    }

    public String getContent() {
        return content;
    }

    public String getHeaders() {
        return headers;
    }
}
