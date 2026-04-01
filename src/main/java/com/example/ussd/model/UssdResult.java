package com.example.ussd.model;

public record UssdResult(
        String nextState,
        String message,
        boolean continueSession
) {
}
