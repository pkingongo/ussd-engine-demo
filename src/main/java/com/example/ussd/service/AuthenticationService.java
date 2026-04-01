package com.example.ussd.service;

import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

    public boolean validatePin(String msisdn, String pin) {
        return "1234".equals(pin);
    }
}
