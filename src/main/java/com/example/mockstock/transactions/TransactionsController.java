package com.example.mockstock.transactions;


import com.example.mockstock.users.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class TransactionsController {
    TransactionsService transactionsService;
    public TransactionsController (TransactionsService transactionsService) {
        this.transactionsService = transactionsService;
    }

    @GetMapping("{id}/transactions")
    public List<Transactions> getTransactions(@PathVariable Long id) {
        return transactionsService.getTransactions(id);
    };

    @PostMapping("{id}/transactions")
    public Transactions postTransactions(@PathVariable Long id, @RequestBody Transactions transactions) {
        User user = transactionsService.getUser(id);
        transactions.setUser(user);
        System.out.println(user);
        return transactionsService.postTransactions(transactions);
    }
}