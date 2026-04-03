package com.example.ussd.repo;

import com.example.ussd.model.UssdMenu;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class InMemoryMenuRepository implements MenuRepository {

    private final List<UssdMenu> menus = List.of(
            new UssdMenu(1, "ENTER_PIN", "en", "Welcome to I&M Bank\\nEnter your PIN:", false, true),
            new UssdMenu(2, "INVALID_PIN", "en", "Invalid PIN\\nTry again:", false, true),
            new UssdMenu(3, "MAIN_MENU", "en", "Select Option:\\n1. Balance Enquiry\\n2. M-PESA\\n3. Loans", false, true),
            new UssdMenu(4, "LIST_ACCOUNT", "en", "Select Account:\\n${options}", true, true),
            new UssdMenu(5, "DISPLAY_BALANCE", "en", "Account: ${accountName}\\nBalance: KES ${balance}", true, true),
            new UssdMenu(6, "ENTER_AMOUNT", "en", "M-PESA from ${accountName} to ${beneficiaryName}\\nEnter amount:", true, true),
            new UssdMenu(7, "SEND_MPESA", "en", "Sent ${amount} to ${beneficiaryName}", true, true),
            new UssdMenu(8, "LOAN_OPTIONS", "en", "Loans for ${accountName}\\n1. Loan Balance\\n2. Mini Statement", true, true),
            new UssdMenu(9, "INVALID_SELECTION", "en", "Invalid selection\\n${menu}", true, true),
            new UssdMenu(10, "LIST_BENEFICIARY", "en", "Select Beneficiary:\\n${options}", true, true),
            new UssdMenu(11, "ENTER_REFERENCE", "en", "Enter reference:", true, true),
            new UssdMenu(12, "ENTER_LOAN_REF", "en", "Enter loan reference:", true, true)
    );

    @Override
    public Optional<UssdMenu> findActiveByStateKeyAndLanguage(String stateKey, String language) {
        return menus.stream()
                .filter(UssdMenu::isActive)
                .filter(menu -> menu.getStateKey().equals(stateKey))
                .filter(menu -> menu.getLanguage().equals(language))
                .findFirst();
    }
}
