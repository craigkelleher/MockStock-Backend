package com.example.mockstock.transactions;

import org.springframework.stereotype.Service;

@Service
public class TransactionsService {
    TransactionsRepository transactionsRepository;
    public TransactionsService (TransactionsRepository transactionsRepository) {
        this.transactionsRepository = transactionsRepository;
    }

    public Transactions getTransactions(Long id) {
        return transactionsRepository.getTransactions(id);
    }

    public Transactions postTransactions(Transactions transactions) {
        return transactionsRepository.save(transactions);
    }
}
