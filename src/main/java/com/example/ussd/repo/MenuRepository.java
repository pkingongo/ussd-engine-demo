package com.example.ussd.repo;

import com.example.ussd.model.UssdMenu;

import java.util.Optional;

public interface MenuRepository {
    Optional<UssdMenu> findActiveByStateKeyAndLanguage(String stateKey, String language);
}
