package com.abia.fraudauth.transaction;

import org.springframework.data.jpa.repository.JpaRepository;
import java.time.Instant;
import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findByUserIdOrderByCreatedAtDesc(Long userId);
    long countByUserIdAndCreatedAtAfter(Long userId, Instant after);
}
