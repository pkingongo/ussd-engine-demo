package com.example.ussd.model;

import jakarta.validation.constraints.NotBlank;

public record UssdRequest(
        @NotBlank String sessionId,
        @NotBlank String msisdn,
        String input
) {
}
