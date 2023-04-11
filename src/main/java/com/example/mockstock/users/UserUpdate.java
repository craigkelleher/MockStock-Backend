package com.example.mockstock.users;

public class UserUpdate {
    private Double balance;

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public UserUpdate(Double balance) {
        this.balance = balance;
    }

    public UserUpdate() {
    }
}
