package com.abia.fraudauth;

import com.abia.fraudauth.fraud.FraudDecision;
import com.abia.fraudauth.fraud.FraudDetectionService;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class FraudDetectionServiceTest {
    private final FraudDetectionService service = new FraudDetectionService();

    @Test
    void flagsHighValueForeignTransaction() {
        FraudDecision decision = service.evaluate(
            new BigDecimal("7500"),
            "US",
            "CA",
            1
        );

        assertTrue(decision.flagged());
        assertTrue(decision.score() >= 50);
    }

    @Test
    void allowsLowRiskTransaction() {
        FraudDecision decision = service.evaluate(
            new BigDecimal("100"),
            "CA",
            "CA",
            1
        );

        assertFalse(decision.flagged());
        assertEquals(0, decision.score());
    }
}
