package com.inbank.back;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
// import org.springframework.web.bind.annotation.CrossOrigin; // Will need to be uncommented in the future
import org.springframework.web.bind.annotation.RestController;

// @CrossOrigin(origins = "http://localhost:XXXX") // Port will need to be replaced in the future
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

