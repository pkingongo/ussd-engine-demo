package com.example.ussd.controller;

import com.example.ussd.engine.UssdEngine;
import com.example.ussd.model.UssdRequest;
import com.example.ussd.model.UssdResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/ussd")
public class UssdController {

    private final UssdEngine ussdEngine;

    public UssdController(UssdEngine ussdEngine) {
        this.ussdEngine = ussdEngine;
    }

    @PostMapping("/process")
    public ResponseEntity<String> process(@Valid @RequestBody UssdRequest request) {
        UssdResponse response = ussdEngine.process(request);
        String prefix = response.continueSession() ? "CON " : "END ";
        return ResponseEntity.ok(prefix + response.message());
    }
}
