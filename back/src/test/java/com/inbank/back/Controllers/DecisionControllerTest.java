package com.inbank.back.Controllers;

import com.inbank.back.DecisionResponse;
import com.inbank.back.Services.Decision;
import com.inbank.back.Controllers.DecisionController.DecisionRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import com.inbank.back.Exceptions.ResourceNotFoundException;

import static org.junit.jupiter.api.Assertions.*;

class DecisionControllerTest {
    private DecisionController controller;
    private Decision decision;

    @BeforeEach
    void setUp() {
        decision = new Decision();
        controller = new DecisionController(decision);
    }

    @Test
    void testScoreInvalidUser() {
        DecisionRequest request = new DecisionRequest("unknown", 5000, 12);
        assertThrows(ResourceNotFoundException.class, () -> controller.score(request));
    }

    @Test
    void testScoreApproved() {
        DecisionRequest request = new DecisionRequest("49002010998", 5000, 12);
        DecisionResponse response = controller.score(request);
        assertTrue(response.approved());
        assertEquals(10000, response.amount());
        assertEquals(12, response.period());
        assertEquals("Approved", response.message());
    }

    @Test
    void testScoreDebt() {
        DecisionRequest request = new DecisionRequest("49002010965", 2000, 12);
        DecisionResponse response = controller.score(request);
        assertFalse(response.approved());
        assertEquals(0, response.amount());
        assertEquals(12, response.period());
        assertEquals("Debt found", response.message());
    }

    @Test
    void testHandleBadRequest() {
        IllegalArgumentException ex = new IllegalArgumentException("Test error");
        ResponseEntity<String> response = controller.handleBadRequest(ex);
        assertEquals(400, response.getStatusCode().value());
        assertTrue(response.getBody().contains("Test error"));
    }
}
