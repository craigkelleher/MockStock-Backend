package com.example.mockstock.transactions;

import com.example.mockstock.users.User;

import javax.persistence.*;
import java.math.BigDecimal;
import java.math.RoundingMode;

@Entity
public class Transactions {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "stock_symbol")
    private String stockSymbol;
    @Column(name = "transaction_type")
    private String transactionType;
    @Column(name = "stock_price")
    private Double stockPrice;
    private int quantity;
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Double getStockPrice() { return stockPrice; }

    public void setStockPrice(Double stockPrice) {
        this.stockPrice = stockPrice;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public String getStockSymbol() {
        return stockSymbol;
    }

    public void setStockSymbol(String stockSymbol) {
        this.stockSymbol = stockSymbol;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Long getUser() {
        return user.getId();
    }

    public Transactions(String stockSymbol, String transactionType, int quantity, User user) {
        this.stockSymbol = stockSymbol;
        this.transactionType = transactionType;
        this.quantity = quantity;
        this.user = user;
    }

    public Transactions() {
    }
}
