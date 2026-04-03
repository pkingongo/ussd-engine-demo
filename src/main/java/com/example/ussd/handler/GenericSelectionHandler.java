package com.example.ussd.handler;

import com.example.ussd.model.UssdResult;
import com.example.ussd.model.UssdSession;
import com.example.ussd.service.MenuService;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class GenericSelectionHandler implements StateHandler {

    private final MenuService menuService;

    public GenericSelectionHandler(MenuService menuService) {
        this.menuService = menuService;
    }

    @Override
    public String getState() {
        return "SELECT";
    }

    @Override
    public UssdResult handle(UssdSession session, String input) {
        if (input == null || input.trim().isEmpty()) {
            return buildInvalidSelection(session);
        }

        input = input.trim();

        if ("00".equals(input)) {
            return new UssdResult("MAIN_MENU", "", true);
        }

        if ("98".equals(input)) {
            int startIndex = (session.getPage() - 1) * session.getPageSize();
            if ((startIndex + session.getPageSize()) < session.getTotalItems()) {
                session.setPage(session.getPage() + 1);
                return new UssdResult("LIST", "", true);
            } else {
                return buildInvalidSelection(session);
            }
        }

        if ("97".equals(input)) {
            if (session.getPage() > 1) {
                session.setPage(session.getPage() - 1);
                return new UssdResult("LIST", "", true);
            } else {
                return buildInvalidSelection(session);
            }
        }

        try {
            int selectedIndex = Integer.parseInt(input) - 1;
            if (selectedIndex >= 0 && selectedIndex < session.getTotalItems()) {
                Map<String, String> selectedItem = session.getFullData().get(selectedIndex);
                return handleSelection(session, selectedItem);
            } else {
                return buildInvalidSelection(session);
            }
        } catch (NumberFormatException e) {
            return buildInvalidSelection(session);
        }
    }

    private UssdResult handleSelection(UssdSession session, Map<String, String> selectedItem) {
        String dataType = session.getDataType();
        if ("ACCOUNT".equals(dataType)) {
            session.getContext().put("selectedAccountId", selectedItem.get("id"));
            session.getContext().put("accountName", selectedItem.get("label"));
        } else if ("BENEFICIARY".equals(dataType)) {
            session.getContext().put("selectedBeneficiaryId", selectedItem.get("id"));
            session.getContext().put("beneficiaryName", selectedItem.get("label"));
        }
        // Let transitions handle the next state based on dataType and nextAction
        return new UssdResult("", "", true);
    }

    private UssdResult buildInvalidSelection(UssdSession session) {
        // For simplicity, redisplay the list
        return new UssdResult("LIST", "", true);
    }
}
