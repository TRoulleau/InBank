package com.inbank.back.Services;

import com.inbank.back.DecisionResponse;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DecisionTest {

    private final Decision decision = new Decision();

    @Test
    void testGetModifier() {
        assertEquals(0, decision.getModifier("49002010965"));
        assertEquals(100, decision.getModifier("49002010976"));
        assertEquals(300, decision.getModifier("49002010987"));
        assertEquals(1000, decision.getModifier("49002010998"));
        assertEquals(-1, decision.getModifier("unknown"));
    }

    @Test
    void testScoreCalculation_Approved() {
        DecisionResponse response = decision.scoreCalculation("49002010998", 5000, 12);
        assertTrue(response.approved());
        assertEquals(10000, response.amount()); // 1000*12 = 12000, but max is 10000
        assertEquals(12, response.period());
        assertEquals("Approved", response.message());
    }

    @Test
    void testScoreCalculation_Debt() {
        DecisionResponse response = decision.scoreCalculation("49002010965", 2000, 12);
        assertFalse(response.approved());
        assertEquals(0, response.amount());
        assertEquals(12, response.period());
        assertEquals("Debt found", response.message());
    }

    @Test
    void testScoreCalculation_AlternativeFound() {
        DecisionResponse response = decision.scoreCalculation("49002010976", 5000, 12);
        assertFalse(response.approved());
        assertTrue(response.amount() >= 2000);
        assertTrue(response.period() >= 12);
        assertTrue(response.message().contains("Alternative"));
    }

    @Test
    void testScoreCalculation_NoAlternative() {
        // Here we need to mock because we need a very low modifier that is not equal to 0
        Decision spyDecision = spy(new Decision());
        doReturn(10).when(spyDecision).getModifier("LOW_SCORE");
        DecisionResponse response = spyDecision.scoreCalculation("LOW_SCORE", 5000, 12);
        assertFalse(response.approved());
        assertEquals(0, response.amount());
        assertEquals(12, response.period());
        assertEquals("No alternative found", response.message());
    }
}
