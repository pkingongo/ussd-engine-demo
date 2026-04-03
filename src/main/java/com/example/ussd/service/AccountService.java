package com.example.ussd.service;

import com.example.ussd.model.Account;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountService {

    public List<Account> getAccountsForCustomer(String msisdn) {
        return List.of(
                new Account("ACC001", "Savings", "12,450.00"),
                new Account("ACC002", "Salary", "98,120.35"),
                new Account("ACC003", "Current", "1,250,000.00"),
                new Account("ACC004", "Fixed Deposit", "500,000.00"),
                new Account("ACC005", "Business", "250,000.00"),
                new Account("ACC006", "Joint", "75,000.00"),
                new Account("ACC007", "Loan", "200,000.00")
        );
    }

    public Account getAccount(String accountId) {
        return getAccountsForCustomer("ignored").stream()
                .filter(account -> account.id().equals(accountId))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Unknown account " + accountId));
    }
}
