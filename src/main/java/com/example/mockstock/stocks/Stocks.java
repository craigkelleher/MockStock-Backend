package com.example.mockstock.stocks;

public class Stocks {
    private String symbol;
    private String companyName;
    private double price;
    private double percentChange;

    public Stocks(String symbol, String companyName, double price, double percentChange) {
        this.symbol = symbol;
        this.companyName = companyName;
        this.price = price;
        this.percentChange = percentChange;
    }

    public String getSymbol() {
        return symbol;
    }

    public String getCompanyName() {
        return companyName;
    }

    public double getPrice() {
        return price;
    }

    public double getPercentChange() {
        return percentChange;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setPercentChange(double percentChange) {
        this.percentChange = percentChange;
    }

    @Override
    public String toString() {
        return String.format("%s: current price=%.2f, today's change=%.2f%%",
                companyName, price, percentChange);
    }
}