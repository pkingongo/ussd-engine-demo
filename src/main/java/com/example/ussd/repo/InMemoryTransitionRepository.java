package com.example.ussd.repo;

import com.example.ussd.model.UssdTransition;
import org.springframework.stereotype.Repository;

import java.util.Comparator;
import java.util.List;

@Repository
public class InMemoryTransitionRepository implements TransitionRepository {

    private final List<UssdTransition> transitions = List.of(
            new UssdTransition(1, "ENTER_PIN", ".*", "MAIN_MENU", null, null, null, null, 1),
            new UssdTransition(2, "MAIN_MENU", "1", "LIST_ACCOUNT", "nextAction", "BALANCE", null, null, 1),
            new UssdTransition(3, "MAIN_MENU", "2", "LIST_ACCOUNT", "nextAction", "MPESA", null, null, 1),
            new UssdTransition(4, "MAIN_MENU", "3", "LIST_ACCOUNT", "nextAction", "LOAN", null, null, 1),
            new UssdTransition(5, "LIST_ACCOUNT", "*", "LIST", null, null, null, null, 1),
            new UssdTransition(6, "LIST", "*", "SELECT", null, null, null, null, 1),
            new UssdTransition(7, "SELECT", "00", "MAIN_MENU", null, null, null, null, 1),
            new UssdTransition(8, "SELECT", "98", "LIST", null, null, null, null, 1),
            new UssdTransition(9, "SELECT", "97", "LIST", null, null, null, null, 1),
            new UssdTransition(10, "SELECT", "[1-9]+", "ACCOUNT_BAL", null, null, "selectionContext", "ACCOUNT_BALANCE", 1),
            new UssdTransition(11, "SELECT", "[1-9]+", "LIST_BENEFICIARIES", null, null, "selectionContext", "ACCOUNT_MPESA", 1),
            new UssdTransition(12, "SELECT", "[1-9]+", "LIST_BENEFICIARIES", null, null, "selectionContext", "ACCOUNT_LOAN", 1),
            new UssdTransition(13, "SELECT", "[1-9]+", "ENTER_AMOUNT", null, null, "selectionContext", "BENEFICIARY_MPESA", 1),
            new UssdTransition(14, "SELECT", "[1-9]+", "ENTER_LOAN_REF", null, null, "selectionContext", "BENEFICIARY_LOAN", 1),
            new UssdTransition(15, "LIST_BENEFICIARIES", "*", "LIST", null, null, null, null, 1),
            new UssdTransition(16, "ACCOUNT_BAL", "*", "DISPLAY_BALANCE", null, null, null, null, 1),
            new UssdTransition(17, "DISPLAY_BALANCE", "*", "END", null, null, null, null, 1),
            new UssdTransition(18, "ENTER_AMOUNT", ".*", "SEND_MPESA", null, null, null, null, 1)
    );

    @Override
    public List<UssdTransition> findByCurrentStateOrderByPriorityAsc(String currentState) {
        return transitions.stream()
                .filter(t -> t.getCurrentState().equals(currentState))
                .sorted(Comparator.comparingInt(UssdTransition::getPriority))
                .toList();
    }
}
