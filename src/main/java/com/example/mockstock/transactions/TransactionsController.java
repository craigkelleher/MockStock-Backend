package com.example.mockstock.transactions;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class TransactionsController {
    TransactionsService transactionsService;
    public TransactionsController (TransactionsService transactionsService) {
        this.transactionsService = transactionsService;
    }

    @GetMapping("{id}/transactions")
    public Transactions getTransactions(@PathVariable Long id) {
        return transactionsService.getTransactions(id);
    };

    @PostMapping("{id}/transactions")
    public Transactions postTransactions(@RequestBody Transactions transactions) {
        return transactionsService.postTransactions(transactions);
    }
}