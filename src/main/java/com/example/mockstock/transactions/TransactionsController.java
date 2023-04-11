package com.example.mockstock.transactions;


import com.example.mockstock.users.User;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/user")
public class TransactionsController {
    TransactionsService transactionsService;
    public TransactionsController (TransactionsService transactionsService) {
        this.transactionsService = transactionsService;
    }

    @GetMapping("/transactions")
    public List<Transactions> getTransactions(HttpServletRequest request) {
        Long id = (Long) request.getAttribute("userId");

        return transactionsService.getTransactions(id);
    };

    @PostMapping("/transactions")
    @ResponseStatus(HttpStatus.CREATED)
    public Transactions postTransactions(HttpServletRequest request, @RequestBody Transactions transactions) throws Exception {
        Long id = (Long) request.getAttribute("userId");
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