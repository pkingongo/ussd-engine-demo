package com.example.ussd.handler;

import com.example.ussd.model.Account;
import com.example.ussd.model.UssdResult;
import com.example.ussd.model.UssdSession;
import com.example.ussd.service.AccountService;
import org.springframework.stereotype.Component;

@Component
public class AccountBalanceHandler implements StateHandler {

    private final AccountService accountService;

    public AccountBalanceHandler(AccountService accountService) {
        this.accountService = accountService;
    }

    @Override
    public String getState() {
        return "ACCOUNT_BALANCE";
    }

    @Override
    public UssdResult handle(UssdSession session, String input) {
        String accountId = String.valueOf(session.getContext().get("selectedAccountId"));
        if (accountId == null || accountId.equals("null")) {
            return new UssdResult("END", "Session expired. Please dial again.", false);
        }

        Account account = accountService.getAccount(accountId);
        session.getContext().put("accountName", account.name());
        session.getContext().put("balance", account.balance());

        return new UssdResult("DISPLAY_BALANCE", "", true);
    }
}
