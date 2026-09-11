package com.abia.fraudauth.fraud;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class FraudDetectionService {
    private static final BigDecimal HIGH_VALUE_THRESHOLD = new BigDecimal("5000");

    public FraudDecision evaluate(
        BigDecimal amount,
        String country,
        String usualCountry,
        long recentTransactionCount
    ) {
        int score = 0;
        StringBuilder reasons = new StringBuilder();

        if (amount.compareTo(HIGH_VALUE_THRESHOLD) > 0) {
            score += 45;
            reasons.append("High-value transaction; ");
        }

        if (usualCountry != null && !usualCountry.equalsIgnoreCase(country)) {
            score += 30;
            reasons.append("New transaction country; ");
        }

        if (recentTransactionCount >= 5) {
            score += 35;
            reasons.append("High transaction velocity; ");
        }

        boolean flagged = score >= 50;

        return new FraudDecision(
            score,
            flagged,
            reasons.isEmpty() ? "No elevated risk signals" : reasons.toString().trim()
        );
    }
}
