package com.abia.fraudauth.transaction;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {
    private final TransactionService transactions;

    public TransactionController(TransactionService transactions) {
        this.transactions = transactions;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TransactionDtos.TransactionResponse create(
        Authentication authentication,
        @Valid @RequestBody TransactionDtos.CreateRequest request
    ) {
        return transactions.create(authentication.getName(), request);
    }

    @GetMapping
    public List<TransactionDtos.TransactionResponse> list(Authentication authentication) {
        return transactions.list(authentication.getName());
    }
}
