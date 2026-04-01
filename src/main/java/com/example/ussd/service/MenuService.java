package com.example.ussd.service;

import com.example.ussd.model.UssdMenu;
import com.example.ussd.repo.MenuRepository;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class MenuService {

    private final MenuRepository menuRepository;

    public MenuService(MenuRepository menuRepository) {
        this.menuRepository = menuRepository;
    }

    public String getMenu(String stateKey, String language, Map<String, Object> context) {
        UssdMenu menu = menuRepository.findActiveByStateKeyAndLanguage(stateKey, language)
                .orElseThrow(() -> new IllegalStateException("No menu found for state " + stateKey));

        String text = menu.getTextTemplate();
        if (context != null) {
            for (Map.Entry<String, Object> entry : context.entrySet()) {
                text = text.replace("${" + entry.getKey() + "}", String.valueOf(entry.getValue()));
            }
        }
        return text;
    }
}
