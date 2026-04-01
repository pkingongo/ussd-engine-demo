package com.example.ussd.model;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

public class UssdSession {

    private String sessionId;
    private String msisdn;
    private String currentState;
    private String language = "en";
    private Instant createdAt = Instant.now();
    private Instant updatedAt = Instant.now();
    private Map<String, Object> context = new HashMap<>();

    public UssdSession() {
    }

    public UssdSession(String sessionId, String msisdn, String currentState) {
        this.sessionId = sessionId;
        this.msisdn = msisdn;
        this.currentState = currentState;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getMsisdn() {
        return msisdn;
    }

    public void setMsisdn(String msisdn) {
        this.msisdn = msisdn;
    }

    public String getCurrentState() {
        return currentState;
    }

    public void setCurrentState(String currentState) {
        this.currentState = currentState;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Map<String, Object> getContext() {
        return context;
    }

    public void setContext(Map<String, Object> context) {
        this.context = context;
    }
}
