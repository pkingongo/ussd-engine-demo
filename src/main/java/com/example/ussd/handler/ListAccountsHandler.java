package com.example.ussd.handler;

import com.example.ussd.model.Account;
import com.example.ussd.model.UssdResult;
import com.example.ussd.model.UssdSession;
import com.example.ussd.service.AccountService;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class ListAccountsHandler implements StateHandler {

    private final AccountService accountService;

    public ListAccountsHandler(AccountService accountService) {
        this.accountService = accountService;
    }

    @Override
    public String getState() {
        return "LIST_ACCOUNT";
    }

    @Override
    public UssdResult handle(UssdSession session, String input) {
        List<Account> accounts = accountService.getAccountsForCustomer(session.getMsisdn());
        if (accounts.isEmpty()) {
            return new UssdResult("END", "No accounts found for your profile.", false);
        }

        List<Map<String, String>> fullData = accounts.stream()
                .map(acc -> Map.of("id", acc.id(), "label", acc.name()))
                .collect(Collectors.toList());

        session.setDataType("ACCOUNT");
        session.setFullData(fullData);
        session.setTotalItems(fullData.size());
        session.setPage(1);
        session.getContext().put("dataType", "ACCOUNT");
        session.getContext().put("selectionContext", "ACCOUNT_" + session.getContext().get("nextAction"));

        return new UssdResult("LIST", "", true);
    }
}
