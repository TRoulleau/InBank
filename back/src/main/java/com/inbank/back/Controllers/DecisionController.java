package com.inbank.back.Controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import com.inbank.back.DecisionResponse;
import com.inbank.back.Services.Decision;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class DecisionController {

    private final Decision decision;

    public DecisionController(Decision decision) {
        this.decision = decision;
    }

    public record DecisionRequest(String personalCode, int loanAmount, int loanPeriod) {}

    @PostMapping("/score")
    public DecisionResponse score(@RequestBody DecisionRequest request) {
        DecisionResponse result = this.decision.scoreCalculation(request.personalCode(), request.loanAmount(), request.loanPeriod());
        return result;
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleBadRequest(IllegalArgumentException e) {
        return ResponseEntity.badRequest().body("Validation Error: " + e.getMessage());
    }
}

