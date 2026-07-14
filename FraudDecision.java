package com.abia.fraudauth.fraud;

public record FraudDecision(
    int score,
    boolean flagged,
    String reason
) {}
