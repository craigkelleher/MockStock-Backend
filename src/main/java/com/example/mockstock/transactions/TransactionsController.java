package com.example.mockstock.transactions;


import com.example.mockstock.users.User;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(maxAge = 3600)
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
    @ResponseStatus(HttpStatus.CREATED)
    public Transactions postTransactions(@PathVariable Long id, @RequestBody Transactions transactions) throws Exception {
        User user = transactionsService.getUser(id);
        transactions.setUser(user);
        return transactionsService.postTransactions(transactions, id);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public void insufficientBalanceExceptionHandler(InsufficientBalanceException e) {}

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public void insufficientStocksExceptionHandler(InsufficentStocksException e) {}
}