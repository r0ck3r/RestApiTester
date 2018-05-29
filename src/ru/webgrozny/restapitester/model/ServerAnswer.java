package ru.webgrozny.restapitester.model;

public class ServerAnswer {
    private String content;
    private String headers;
    private double time;

    public ServerAnswer(String content, String headers, double time) {
        this.content = content;
        this.headers = headers;
        this.time = time;
    }

    public String getContent() {
        return content;
    }

    public String getHeaders() {
        return headers;
    }

    public double getTime() {
        return time;
    }
}
