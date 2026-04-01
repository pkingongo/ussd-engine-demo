package com.example.ussd.handler;

import com.example.ussd.model.UssdResult;
import com.example.ussd.model.UssdSession;
import com.example.ussd.service.MenuService;
import org.springframework.stereotype.Component;

@Component
public class DisplayBalanceHandler implements StateHandler {

    private final MenuService menuService;

    public DisplayBalanceHandler(MenuService menuService) {
        this.menuService = menuService;
    }

    @Override
    public String getState() {
        return "DISPLAY_BALANCE";
    }

    @Override
    public UssdResult handle(UssdSession session, String input) {
        String message = menuService.getMenu(
                "DISPLAY_BALANCE",
                session.getLanguage(),
                session.getContext()
        );
        return new UssdResult("END", message, false);
    }
}
