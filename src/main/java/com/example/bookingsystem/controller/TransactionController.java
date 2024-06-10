package com.example.bookingsystem.controller;

import com.example.bookingsystem.dto.RejectedTransactionsResponse;
import com.example.bookingsystem.dto.RejectedTransactionsResponse.RejectedTransaction;
import com.example.bookingsystem.entity.Transaction;
import com.example.bookingsystem.service.TransactionService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

@RestController
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping(value = "/transactions", consumes = MediaType.APPLICATION_NDJSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<RejectedTransactionsResponse> handleTransactions(@RequestBody Flux<Transaction> transactions) {
        System.out.println("Received");

        List<RejectedTransaction> rejectedTransactions = new ArrayList<>();

        return transactions
                .doOnNext(transaction -> {
                    if (!transactionService.transact(transaction)) {
                        rejectedTransactions.add(new RejectedTransaction(
                                transaction.getFirstName(),
                                transaction.getLastName(),
                                transaction.getEmail(),
                                transaction.getTransactionTypeNumber()));
                    }
                })
                .then(Mono.just(new RejectedTransactionsResponse(rejectedTransactions)));
    }
}
