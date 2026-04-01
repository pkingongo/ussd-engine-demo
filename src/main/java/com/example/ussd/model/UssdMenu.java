package com.example.ussd.model;

public class UssdMenu {

    private final long id;
    private final String stateKey;
    private final String language;
    private final String textTemplate;
    private final boolean dynamic;
    private final boolean active;

    public UssdMenu(long id, String stateKey, String language, String textTemplate, boolean dynamic, boolean active) {
        this.id = id;
        this.stateKey = stateKey;
        this.language = language;
        this.textTemplate = textTemplate;
        this.dynamic = dynamic;
        this.active = active;
    }

    public long getId() {
        return id;
    }

    public String getStateKey() {
        return stateKey;
    }

    public String getLanguage() {
        return language;
    }

    public String getTextTemplate() {
        return textTemplate;
    }

    public boolean isDynamic() {
        return dynamic;
    }

    public boolean isActive() {
        return active;
    }
}
