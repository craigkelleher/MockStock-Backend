package com.example.mockstock.transactions;

import com.example.mockstock.users.User;
import com.example.mockstock.users.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionsService {
    TransactionsRepository transactionsRepository;
    UserRepository userRepository;
    public TransactionsService (TransactionsRepository transactionsRepository, UserRepository userRepository) {
        this.transactionsRepository = transactionsRepository;
        this.userRepository = userRepository;
    }

    public List<Transactions> getTransactions(Long id) {
        return transactionsRepository.findByUserId(id);
    }

    public Transactions postTransactions(Transactions transactions) {
        return transactionsRepository.save(transactions);
    }

    public User getUser(Long id) {
        return userRepository.findById(id).orElse(null);
    }
}
