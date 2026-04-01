package com.example.ussd.config;

import com.example.ussd.handler.StateHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Configuration
public class HandlerConfig {

    @Bean
    public Map<String, StateHandler> stateHandlerMap(List<StateHandler> handlers) {
        return handlers.stream()
                .collect(Collectors.toMap(
                        StateHandler::getState,
                        handler -> handler
                ));
    }
}
