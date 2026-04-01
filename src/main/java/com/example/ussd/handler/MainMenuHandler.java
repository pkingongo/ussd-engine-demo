package com.example.ussd.handler;

import com.example.ussd.model.UssdResult;
import com.example.ussd.model.UssdSession;
import com.example.ussd.service.MenuService;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class MainMenuHandler implements StateHandler {

    private final MenuService menuService;

    public MainMenuHandler(MenuService menuService) {
        this.menuService = menuService;
    }

    @Override
    public String getState() {
        return "MAIN_MENU";
    }

    @Override
    public UssdResult handle(UssdSession session, String input) {
        if (input == null || input.isBlank()) {
            return new UssdResult(
                    "MAIN_MENU",
                    menuService.getMenu("MAIN_MENU", session.getLanguage(), Map.of()),
                    true
            );
        }
        return new UssdResult("MAIN_MENU", "", true);
    }
}
