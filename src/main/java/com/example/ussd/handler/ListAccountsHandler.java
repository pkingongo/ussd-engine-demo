package com.example.ussd.handler;

import com.example.ussd.model.Account;
import com.example.ussd.model.UssdResult;
import com.example.ussd.model.UssdSession;
import com.example.ussd.service.AccountService;
import com.example.ussd.service.MenuService;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class ListAccountsHandler implements StateHandler {

    private final AccountService accountService;
    private final MenuService menuService;

    public ListAccountsHandler(AccountService accountService, MenuService menuService) {
        this.accountService = accountService;
        this.menuService = menuService;
    }

    @Override
    public String getState() {
        return "LIST_ACCOUNTS";
    }

    @Override
    public UssdResult handle(UssdSession session, String input) {
        List<Account> accounts = accountService.getAccountsForCustomer(session.getMsisdn());
        if (accounts.isEmpty()) {
            return new UssdResult("END", "No accounts found for your profile.", false);
        }

        Map<String, String> optionToAccountId = new LinkedHashMap<>();
        String options = buildOptions(accounts, optionToAccountId);

        session.getContext().put("accountOptions", optionToAccountId);

        String menu = menuService.getMenu(
                "SELECT_ACCOUNT",
                session.getLanguage(),
                Map.of("options", options)
        );

        return new UssdResult("SELECT_ACCOUNT", menu, true);
    }

    private String buildOptions(List<Account> accounts, Map<String, String> optionToAccountId) {
        for (int i = 0; i < accounts.size(); i++) {
            optionToAccountId.put(String.valueOf(i + 1), accounts.get(i).id());
        }

        return accounts.stream()
                .map(account -> optionToAccountId.entrySet().stream()
                        .filter(e -> e.getValue().equals(account.id()))
                        .map(Map.Entry::getKey)
                        .findFirst()
                        .orElseThrow() + ". " + account.name())
                .collect(Collectors.joining("\n"));
    }
}
