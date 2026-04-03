package com.example.ussd.handler;

import com.example.ussd.model.UssdResult;
import com.example.ussd.model.UssdSession;
import com.example.ussd.service.MenuService;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Component
public class GenericListHandler implements StateHandler {

    private final MenuService menuService;

    public GenericListHandler(MenuService menuService) {
        this.menuService = menuService;
    }

    @Override
    public String getState() {
        return "LIST";
    }

    @Override
    public UssdResult handle(UssdSession session, String input) {
        if (session.getFullData() == null || session.getFullData().isEmpty()) {
            return new UssdResult("END", "No items found.", false);
        }

        int startIndex = (session.getPage() - 1) * session.getPageSize();
        int endIndex = Math.min(startIndex + session.getPageSize(), session.getTotalItems());
        List<Map<String, String>> pagedData = session.getFullData().subList(startIndex, endIndex);

        String options = buildOptions(pagedData, startIndex, session);

        String menuKey = "LIST_" + session.getDataType();
        String menu = menuService.getMenu(menuKey, session.getLanguage(), Map.of("options", options));

        return new UssdResult("SELECT", menu, true);
    }

    private String buildOptions(List<Map<String, String>> pagedData, int startIndex, UssdSession session) {
        StringBuilder sb = new StringBuilder();
        sb.append(IntStream.range(0, pagedData.size())
                .mapToObj(i -> (startIndex + i + 1) + ". " + pagedData.get(i).get("label"))
                .collect(Collectors.joining("\n")));
        if ((startIndex + session.getPageSize()) < session.getTotalItems()) {
            sb.append("\n98. Next");
        }
        if (session.getPage() > 1) {
            sb.append("\n97. Previous");
        }
        sb.append("\n00. Main Menu");
        return sb.toString();
    }
}
