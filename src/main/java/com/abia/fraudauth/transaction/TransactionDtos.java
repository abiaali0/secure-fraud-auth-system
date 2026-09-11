package com.abia.fraudauth.transaction;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.time.Instant;

public final class TransactionDtos {
    private TransactionDtos() {}

    public record CreateRequest(
        @NotNull @DecimalMin("0.01") BigDecimal amount,
        @NotBlank @Size(min = 2, max = 2) String country
    ) {}

    public record TransactionResponse(
        Long id,
        BigDecimal amount,
        String country,
        Instant createdAt,
        boolean flagged,
        int riskScore,
        String riskReason
    ) {
        public static TransactionResponse from(Transaction transaction) {
            return new TransactionResponse(
                transaction.getId(),
                transaction.getAmount(),
                transaction.getCountry(),
                transaction.getCreatedAt(),
                transaction.isFlagged(),
                transaction.getRiskScore(),
                transaction.getRiskReason()
            );
        }
    }
}
