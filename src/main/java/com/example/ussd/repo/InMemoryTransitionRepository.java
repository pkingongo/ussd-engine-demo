package com.example.ussd.repo;

import com.example.ussd.model.UssdTransition;
import org.springframework.stereotype.Repository;

import java.util.Comparator;
import java.util.List;

@Repository
public class InMemoryTransitionRepository implements TransitionRepository {

    private final List<UssdTransition> transitions = List.of(
            new UssdTransition(1, "ENTER_PIN", ".*", "MAIN_MENU", null, null, null, null, 1),
            new UssdTransition(2, "MAIN_MENU", "1", "LIST_ACCOUNTS", "nextAction", "BALANCE", null, null, 1),
            new UssdTransition(3, "MAIN_MENU", "2", "LIST_ACCOUNTS", "nextAction", "MPESA", null, null, 2),
            new UssdTransition(4, "MAIN_MENU", "3", "LIST_ACCOUNTS", "nextAction", "LOAN", null, null, 3),
            new UssdTransition(5, "LIST_ACCOUNTS", "*", "SELECT_ACCOUNT", null, null, null, null, 1),
            new UssdTransition(6, "SELECT_ACCOUNT", "[1-9]+", "ACCOUNT_BALANCE", null, null, "nextAction", "BALANCE", 1),
            new UssdTransition(7, "SELECT_ACCOUNT", "[1-9]+", "MPESA_AMOUNT", null, null, "nextAction", "MPESA", 2),
            new UssdTransition(8, "SELECT_ACCOUNT", "[1-9]+", "LOAN_OPTIONS", null, null, "nextAction", "LOAN", 3),
            new UssdTransition(9, "ACCOUNT_BALANCE", "*", "DISPLAY_BALANCE", null, null, null, null, 1),
            new UssdTransition(10, "DISPLAY_BALANCE", "*", "END", null, null, null, null, 1)
    );

    @Override
    public List<UssdTransition> findByCurrentStateOrderByPriorityAsc(String currentState) {
        return transitions.stream()
                .filter(t -> t.getCurrentState().equals(currentState))
                .sorted(Comparator.comparingInt(UssdTransition::getPriority))
                .toList();
    }
}
