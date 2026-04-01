package com.example.ussd.handler;

import com.example.ussd.model.UssdResult;
import com.example.ussd.model.UssdSession;
import com.example.ussd.service.AuthenticationService;
import com.example.ussd.service.MenuService;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class EnterPinHandler implements StateHandler {

    private final MenuService menuService;
    private final AuthenticationService authenticationService;

    public EnterPinHandler(MenuService menuService, AuthenticationService authenticationService) {
        this.menuService = menuService;
        this.authenticationService = authenticationService;
    }

    @Override
    public String getState() {
        return "ENTER_PIN";
    }

    @Override
    public UssdResult handle(UssdSession session, String input) {
        if (input == null || input.isBlank()) {
            return new UssdResult(
                    "ENTER_PIN",
                    menuService.getMenu("ENTER_PIN", session.getLanguage(), Map.of()),
                    true
            );
        }

        if (!authenticationService.validatePin(session.getMsisdn(), input)) {
            return new UssdResult(
                    "ENTER_PIN",
                    menuService.getMenu("INVALID_PIN", session.getLanguage(), Map.of()),
                    true
            );
        }

        return new UssdResult("MAIN_MENU", "", true);
    }
}
