package com.example.ussd.service;

import com.example.ussd.model.UssdSession;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Service
public class InMemorySessionService implements SessionService {

    private static final Duration TTL = Duration.ofMinutes(5);
    private final ConcurrentMap<String, UssdSession> sessions = new ConcurrentHashMap<>();

    @Override
    public UssdSession getOrCreate(String sessionId, String msisdn) {
        purgeExpired();
        UssdSession existing = sessions.get(sessionId);
        if (existing != null) {
            existing.setUpdatedAt(Instant.now());
            return existing;
        }

        UssdSession created = new UssdSession(sessionId, msisdn, "ENTER_PIN");
        save(created);
        return created;
    }

    @Override
    public void save(UssdSession session) {
        session.setUpdatedAt(Instant.now());
        sessions.put(session.getSessionId(), session);
    }

    @Override
    public void clear(String sessionId) {
        sessions.remove(sessionId);
    }

    private void purgeExpired() {
        Instant now = Instant.now();
        sessions.values().removeIf(session -> session.getUpdatedAt().plus(TTL).isBefore(now));
    }
}
