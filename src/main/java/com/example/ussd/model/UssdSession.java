package com.example.ussd.model;

import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UssdSession {

    private String sessionId;
    private String msisdn;
    private String currentState;
    private String language = "en";
    private Instant createdAt = Instant.now();
    private Instant updatedAt = Instant.now();
    private Map<String, Object> context = new HashMap<>();
    private String dataType;
    private List<Map<String, String>> fullData;
    private int page = 1;
    private int pageSize = 5;
    private int totalItems;

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

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public List<Map<String, String>> getFullData() {
        return fullData;
    }

    public void setFullData(List<Map<String, String>> fullData) {
        this.fullData = fullData;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getTotalItems() {
        return totalItems;
    }

    public void setTotalItems(int totalItems) {
        this.totalItems = totalItems;
    }
}
