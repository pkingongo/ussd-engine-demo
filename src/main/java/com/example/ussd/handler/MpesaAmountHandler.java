package com.example.ussd.handler;

import com.example.ussd.model.UssdResult;
import com.example.ussd.model.UssdSession;
import com.example.ussd.service.MenuService;
import org.springframework.stereotype.Component;

@Component
public class MpesaAmountHandler implements StateHandler {

    private final MenuService menuService;

    public MpesaAmountHandler(MenuService menuService) {
        this.menuService = menuService;
    }

    @Override
    public String getState() {
        return "MPESA_AMOUNT";
    }

    @Override
    public UssdResult handle(UssdSession session, String input) {
        String message = menuService.getMenu("MPESA_AMOUNT", session.getLanguage(), session.getContext());
        return new UssdResult("END", message, false);
    }
}
