package com.example.mockstock;

import com.example.mockstock.stocks.Stocks;
import com.example.mockstock.stocks.StocksController;
import com.example.mockstock.transactions.Transactions;
import com.example.mockstock.users.User;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class MockstockApplication {

	public static void main(String[] args) throws Exception {
		ApplicationContext context = SpringApplication.run(MockstockApplication.class, args);
	}
}