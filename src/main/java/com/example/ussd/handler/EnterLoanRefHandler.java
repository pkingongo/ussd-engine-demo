package com.example.ussd.handler;

import com.example.ussd.model.UssdResult;
import com.example.ussd.model.UssdSession;
import com.example.ussd.service.MenuService;
import org.springframework.stereotype.Component;

@Component
public class EnterLoanRefHandler implements StateHandler {

    private final MenuService menuService;

    public EnterLoanRefHandler(MenuService menuService) {
        this.menuService = menuService;
    }

    @Override
    public String getState() {
        return "ENTER_LOAN_REF";
    }

    @Override
    public UssdResult handle(UssdSession session, String input) {
        if (input == null || input.trim().isEmpty()) {
            String menu = menuService.getMenu("ENTER_LOAN_REF", session.getLanguage(), session.getContext());
            return new UssdResult("ENTER_LOAN_REF", menu, true);
        }

        // Simple validation: non-empty
        String loanRef = input.trim();
        if (loanRef.isEmpty()) {
            String invalidMenu = "Invalid loan reference. Please enter a valid loan reference:\n";
            String menu = menuService.getMenu("ENTER_LOAN_REF", session.getLanguage(), session.getContext());
            return new UssdResult("ENTER_LOAN_REF", invalidMenu + menu.replace("Enter loan reference:", ""), true);
        }

        session.getContext().put("loanRef", loanRef);
        // For loans, perhaps go to LOAN_OPTIONS or a confirmation
        return new UssdResult("LOAN_OPTIONS", "", true);
    }
}
