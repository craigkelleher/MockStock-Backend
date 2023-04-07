package com.example.mockstock.portfolios;

import com.example.mockstock.transactions.Transactions;
import com.example.mockstock.users.User;

import javax.persistence.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Entity
public class Portfolios {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "stock_symbol")
    private String stockSymbol;
    private String name;
    private int quantity;
    private Double profitLoss;
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public Portfolios(String stockSymbol, String name, int quantity, Double profitLoss, User user) {
        this.stockSymbol = stockSymbol;
        this.name = name;
        this.quantity = quantity;
        this.profitLoss = profitLoss;
        this.user = user;
    }

    public Portfolios() {
    }

    public String getStockSymbol() {
        return stockSymbol;
    }

    public void setStockSymbol(String stockSymbol) {
        this.stockSymbol = stockSymbol;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Double getProfitLoss() {
        return new BigDecimal(profitLoss).setScale(2, RoundingMode.CEILING).doubleValue();
    }

    public void setProfitLoss(Double profitLoss) {
        this.profitLoss = profitLoss;
    }

    public Long getUser() {
        return user.getId();
    }

    public void setUser(User user) {
        this.user = user;
    }


}
