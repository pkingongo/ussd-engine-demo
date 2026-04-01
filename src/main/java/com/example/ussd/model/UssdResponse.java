package com.example.ussd.model;

public record UssdResponse(
        String message,
        boolean continueSession,
        String currentState
) {
}
