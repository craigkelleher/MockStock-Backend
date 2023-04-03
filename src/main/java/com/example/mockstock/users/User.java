package com.example.mockstock.users;

import com.example.mockstock.portfolios.Portfolios;
import com.example.mockstock.transactions.Transactions;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.OnDelete;

import javax.persistence.*;
import java.util.List;

@Entity
@Table (name = "users")
@JsonInclude (JsonInclude.Include.NON_NULL)
public class User {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;
    @Column (name = "user_name")
    private String name;
    @Column (name = "email")
    private String email;
    @Column (name = "user_password", columnDefinition = "VARCHAR(255) BINARY")
    private String password;
    private Double balance;
    @OneToMany(
            mappedBy = "user",
            cascade = CascadeType.REMOVE,
            orphanRemoval = true
    )
    private List<Portfolios> portfolios;
    @OneToMany(
            mappedBy = "user",
            cascade = CascadeType.REMOVE,
            orphanRemoval = true
    )
    private List<Transactions> transactions;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public void setPortfolios(List<Portfolios> portfolios) {
        this.portfolios = portfolios;
    }


    public void setTransactions(List<Transactions> transactions) {
        this.transactions = transactions;
    }

    public User(String name, String email, String password, Double balance) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.balance = balance;
    }

    public User() {

    }
}
