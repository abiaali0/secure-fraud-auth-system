package com.abia.fraudauth.transaction;

import com.abia.fraudauth.fraud.FraudDecision;
import com.abia.fraudauth.fraud.FraudDetectionService;
import com.abia.fraudauth.user.User;
import com.abia.fraudauth.user.UserRepository;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class TransactionService {
    private final TransactionRepository transactions;
    private final UserRepository users;
    private final FraudDetectionService fraud;

    public TransactionService(
        TransactionRepository transactions,
        UserRepository users,
        FraudDetectionService fraud
    ) {
        this.transactions = transactions;
        this.users = users;
        this.fraud = fraud;
    }

    public TransactionDtos.TransactionResponse create(
        String email,
        TransactionDtos.CreateRequest request
    ) {
        User user = users.findByEmail(email)
            .orElseThrow(() -> new IllegalArgumentException("Authenticated user does not exist"));

        List<Transaction> history = transactions.findByUserIdOrderByCreatedAtDesc(user.getId());
        String usualCountry = history.isEmpty() ? request.country() : history.get(0).getCountry();
        long recentCount = transactions.countByUserIdAndCreatedAtAfter(
            user.getId(),
            Instant.now().minus(10, ChronoUnit.MINUTES)
        );

        FraudDecision decision = fraud.evaluate(
            request.amount(),
            request.country().toUpperCase(),
            usualCountry,
            recentCount
        );

        Transaction transaction = new Transaction();
        transaction.setUser(user);
        transaction.setAmount(request.amount());
        transaction.setCountry(request.country().toUpperCase());
        transaction.setFlagged(decision.flagged());
        transaction.setRiskScore(decision.score());
        transaction.setRiskReason(decision.reason());

        return TransactionDtos.TransactionResponse.from(transactions.save(transaction));
    }

    public List<TransactionDtos.TransactionResponse> list(String email) {
        User user = users.findByEmail(email)
            .orElseThrow(() -> new IllegalArgumentException("Authenticated user does not exist"));
        return transactions.findByUserIdOrderByCreatedAtDesc(user.getId())
            .stream()
            .map(TransactionDtos.TransactionResponse::from)
            .toList();
    }
}
