package com.example.ussd.handler;

import com.example.ussd.model.Account;
import com.example.ussd.model.UssdResult;
import com.example.ussd.model.UssdSession;
import com.example.ussd.service.AccountService;
import com.example.ussd.service.MenuService;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@SuppressWarnings("unchecked")
public class SelectAccountHandler implements StateHandler {

    private final MenuService menuService;
    private final AccountService accountService;

    public SelectAccountHandler(MenuService menuService, AccountService accountService) {
        this.menuService = menuService;
        this.accountService = accountService;
    }

    @Override
    public String getState() {
        return "SELECT_ACCOUNT";
    }

    @Override
    public UssdResult handle(UssdSession session, String input) {
        Object rawMapping = session.getContext().get("accountOptions");
        if (!(rawMapping instanceof Map<?, ?>)) {
            return new UssdResult("END", "Session expired. Please dial again.", false);
        }

        Map<String, String> optionMap = (Map<String, String>) rawMapping;

        if (input == null || !optionMap.containsKey(input)) {
            String menu = buildInvalidSelectionMenu(session, optionMap);
            return new UssdResult("SELECT_ACCOUNT", menu, true);
        }

        String selectedAccountId = optionMap.get(input);
        Account account = accountService.getAccount(selectedAccountId);

        session.getContext().put("selectedAccountId", selectedAccountId);
        session.getContext().put("accountName", account.name());

        return new UssdResult("SELECT_ACCOUNT", "", true);
    }

    private String buildInvalidSelectionMenu(UssdSession session, Map<String, String> optionMap) {
        LinkedHashMap<String, String> ordered = new LinkedHashMap<>(optionMap);
        String rebuiltMenu = ordered.entrySet().stream()
                .map(entry -> entry.getKey() + ". " + accountService.getAccount(entry.getValue()).name())
                .collect(Collectors.joining("\n"));

        String selectionMenu = menuService.getMenu(
                "SELECT_ACCOUNT",
                session.getLanguage(),
                Map.of("options", rebuiltMenu)
        );

        return menuService.getMenu(
                "INVALID_SELECTION",
                session.getLanguage(),
                Map.of("menu", selectionMenu)
        );
    }
}
