package com.example.ussd.model;

public class UssdTransition {

    private final long id;
    private final String currentState;
    private final String inputPattern;
    private final String nextState;
    private final String contextKeyToSet;
    private final String contextValueToSet;
    private final String matchConditionKey;
    private final String matchConditionValue;
    private final int priority;

    public UssdTransition(
            long id,
            String currentState,
            String inputPattern,
            String nextState,
            String contextKeyToSet,
            String contextValueToSet,
            String matchConditionKey,
            String matchConditionValue,
            int priority
    ) {
        this.id = id;
        this.currentState = currentState;
        this.inputPattern = inputPattern;
        this.nextState = nextState;
        this.contextKeyToSet = contextKeyToSet;
        this.contextValueToSet = contextValueToSet;
        this.matchConditionKey = matchConditionKey;
        this.matchConditionValue = matchConditionValue;
        this.priority = priority;
    }

    public long getId() {
        return id;
    }

    public String getCurrentState() {
        return currentState;
    }

    public String getInputPattern() {
        return inputPattern;
    }

    public String getNextState() {
        return nextState;
    }

    public String getContextKeyToSet() {
        return contextKeyToSet;
    }

    public String getContextValueToSet() {
        return contextValueToSet;
    }

    public String getMatchConditionKey() {
        return matchConditionKey;
    }

    public String getMatchConditionValue() {
        return matchConditionValue;
    }

    public int getPriority() {
        return priority;
    }
}
