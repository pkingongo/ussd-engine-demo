package com.example.ussd.service;

import com.example.ussd.model.UssdTransition;
import com.example.ussd.repo.TransitionRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class TransitionService {

    private final TransitionRepository transitionRepository;

    public TransitionService(TransitionRepository transitionRepository) {
        this.transitionRepository = transitionRepository;
    }

    public UssdTransition resolve(String state, String input, Map<String, Object> context) {
        List<UssdTransition> rules = transitionRepository.findByCurrentStateOrderByPriorityAsc(state);
        for (UssdTransition rule : rules) {
            if (!matchesInput(rule.getInputPattern(), input)) {
                continue;
            }
            if (!matchesCondition(rule, context)) {
                continue;
            }
            return rule;
        }
        throw new IllegalStateException("No transition found for state=" + state + ", input=" + input);
    }

    private boolean matchesInput(String pattern, String input) {
        if (pattern == null || pattern.equals("*")) {
            return true;
        }
        return input != null && input.matches(pattern);
    }

    private boolean matchesCondition(UssdTransition transition, Map<String, Object> context) {
        if (transition.getMatchConditionKey() == null) {
            return true;
        }
        Object actualValue = context.get(transition.getMatchConditionKey());
        return transition.getMatchConditionValue().equals(String.valueOf(actualValue));
    }
}
