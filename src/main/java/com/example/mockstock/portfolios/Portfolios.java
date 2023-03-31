package com.example.mockstock.portfolios;

import com.example.mockstock.users.User;

import javax.persistence.*;

@Entity
public class Portfolios {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "stock_symbol")
    private String stockSymbol;
    private int quantity;
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public Portfolios(String stockSymbol, int quantity, User user) {
        this.stockSymbol = stockSymbol;
        this.quantity = quantity;
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

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Long getUser() {
        return user.getId();
    }

    public void setUser(User user) {
        this.user = user;
    }

}
