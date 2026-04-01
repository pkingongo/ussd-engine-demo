package com.example.ussd.handler;

import com.example.ussd.model.UssdResult;
import com.example.ussd.model.UssdSession;

public interface StateHandler {
    String getState();
    UssdResult handle(UssdSession session, String input);
}
