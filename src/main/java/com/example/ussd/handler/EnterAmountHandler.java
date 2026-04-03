package com.example.ussd.handler;

import com.example.ussd.model.UssdResult;
import com.example.ussd.model.UssdSession;
import com.example.ussd.service.MenuService;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class EnterAmountHandler implements StateHandler {

    private final MenuService menuService;

    public EnterAmountHandler(MenuService menuService) {
        this.menuService = menuService;
    }

    @Override
    public String getState() {
        return "ENTER_AMOUNT";
    }

    @Override
    public UssdResult handle(UssdSession session, String input) {
        if (input == null || input.trim().isEmpty()) {
            String menu = menuService.getMenu("ENTER_AMOUNT", session.getLanguage(), session.getContext());
            return new UssdResult("ENTER_AMOUNT", menu, true);
        }

        // Validate amount (simple check: positive number)
        try {
            double amount = Double.parseDouble(input.trim());
            if (amount <= 0) {
                throw new NumberFormatException();
            }
            session.getContext().put("amount", input.trim());
            String confirmation = menuService.getMenu("SEND_MPESA", session.getLanguage(), session.getContext());
            return new UssdResult("END", confirmation, false);
        } catch (NumberFormatException e) {
            String invalidMenu = "Invalid amount. Please enter a valid amount:\n";
            String menu = menuService.getMenu("ENTER_AMOUNT", session.getLanguage(), session.getContext());
            return new UssdResult("ENTER_AMOUNT", invalidMenu + menu.replace("M-PESA from ${accountName} to ${beneficiaryName}\nEnter amount:", ""), true);
        }
    }
}
