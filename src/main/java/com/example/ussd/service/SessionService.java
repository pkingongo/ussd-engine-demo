package com.example.ussd.service;

import com.example.ussd.model.UssdSession;

public interface SessionService {
    UssdSession getOrCreate(String sessionId, String msisdn);
    void save(UssdSession session);
    void clear(String sessionId);
}
