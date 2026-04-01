package com.example.ussd.repo;

import com.example.ussd.model.UssdTransition;

import java.util.List;

public interface TransitionRepository {
    List<UssdTransition> findByCurrentStateOrderByPriorityAsc(String currentState);
}
