package com.example.ussd.engine;

import com.example.ussd.handler.StateHandler;
import com.example.ussd.model.*;
import com.example.ussd.service.SessionService;
import com.example.ussd.service.TransitionService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class UssdEngine {

    /**
     * Map of state → handler
     * Example:
     *  ENTER_PIN → EnterPinHandler
     *  MAIN_MENU → MainMenuHandler
     *
     * IMPORTANT:
     * This uses a custom bean (stateHandlerMap) where keys = handler.getState()
     */
    private final Map<String, StateHandler> handlers;

    /**
     * Responsible for resolving next state from DB (or in-memory)
     * Uses:
     *   currentState + input + context → nextState
     */
    private final TransitionService transitionService;

    /**
     * Handles session storage (Redis or in-memory)
     */
    private final SessionService sessionService;

    private final ObjectMapper objectMapper;

    public UssdEngine(
            @Qualifier("stateHandlerMap") Map<String, StateHandler> handlers,
            TransitionService transitionService,
            SessionService sessionService
    ) {
        this.handlers = handlers;
        this.transitionService = transitionService;
        this.sessionService = sessionService;
        this.objectMapper = new ObjectMapper();
        this.objectMapper.registerModule(new JavaTimeModule());
    }

    /**
     * Entry point for every USSD request
     *
     * Flow:
     * 1. Load session
     * 2. Execute current state handler
     * 3. If message → return to user
     * 4. Else resolve transition and continue loop
     */
    public UssdResponse process(UssdRequest request) {

        // 1. Load existing session OR create new one (default state = ENTER_PIN)
        UssdSession session =
                sessionService.getOrCreate(request.sessionId(), request.msisdn());

        // Input from user (can be "", "1", "1234", etc.)
        String input = request.input();

        /**
         * CORE LOOP:
         * Keeps executing states internally until we have something to show user
         */
        while (true) {

            // Debug logs (useful during development)
            System.out.println("Available handlers: " + handlers.keySet());
            System.out.println("Current state: " + session.getCurrentState());

            // Print session as JSON before processing state
            try {
                System.out.println("Session JSON: " + objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(session));
            } catch (Exception e) {
                System.out.println("Error serializing session: " + e.getMessage());
            }

            // 2. Get handler for current state
            StateHandler handler = handlers.get(session.getCurrentState());

            // Fail-safe: if no handler found → terminate session
            if (handler == null) {
                sessionService.clear(session.getSessionId());
                return new UssdResponse(
                        "No handler configured for state " + session.getCurrentState(),
                        false,
                        "END"
                );
            }

            /**
             * 3. Execute handler logic
             *
             * Handler responsibility:
             * - validate input
             * - fetch data (accounts, balance, etc.)
             * - update session context
             *
             * It DOES NOT decide next state (DB does that)
             */
            UssdResult result = handler.handle(session, input);

            /**
             * 4. If handler produced a message → respond to user
             *
             * This is a DISPLAY STATE
             */
            if (result.message() != null && !result.message().isBlank()) {

                /**
                 * Determine which state to persist:
                 * - If handler explicitly provided nextState → use it
                 * - Otherwise remain in current state
                 */
                String stateToPersist =
                        (result.nextState() == null || result.nextState().isBlank())
                                ? session.getCurrentState()
                                : result.nextState();

                session.setCurrentState(stateToPersist);

                /**
                 * If session should end OR state is END then terminate
                 */
                if (!result.continueSession() || "END".equals(stateToPersist)) {
                    sessionService.clear(session.getSessionId());
                    return new UssdResponse(result.message(), false, "END");
                }

                // Save session for next request
                sessionService.save(session);

                return new UssdResponse(
                        result.message(),
                        true,
                        session.getCurrentState()
                );
            }

            /**
             * 5. No message → PROCESSING STATE
             *
             * We now resolve next state using DB-driven transitions
             */
            UssdTransition transition = transitionService.resolve(
                    session.getCurrentState(),
                    input,
                    session.getContext()
            );

            /**
             * 6. Apply context updates (e.g. nextAction = BALANCE)
             */
            if (transition.getContextKeyToSet() != null) {
                session.getContext().put(
                        transition.getContextKeyToSet(),
                        transition.getContextValueToSet()
                );
            }

            /**
             * 7. Move to next state
             */
            session.setCurrentState(transition.getNextState());

            /**
             * 8. If next state is END → terminate session
             */
            if ("END".equals(transition.getNextState())) {
                sessionService.clear(session.getSessionId());
                return new UssdResponse("Session ended.", false, "END");
            }

            /**
             * 9. Clear input before next loop iteration
             *
             * IMPORTANT:
             * Prevents reusing previous user input
             * Ensures next state runs cleanly
             */
            input = null;
        }
    }
}