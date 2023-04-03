package com.example.mockstock;

import com.example.mockstock.portfolios.Portfolios;
import com.example.mockstock.portfolios.PortfoliosRepository;
import com.example.mockstock.stocks.Stocks;
import com.example.mockstock.stocks.StocksController;
import com.example.mockstock.transactions.Transactions;
import com.example.mockstock.transactions.TransactionsRepository;
import com.example.mockstock.users.User;
import com.example.mockstock.users.UserRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

@SpringBootApplication
public class MockstockApplication {

	public static void main(String[] args) throws Exception {
		ApplicationContext context = SpringApplication.run(MockstockApplication.class, args);
//		StocksController stocksController = context.getBean(StocksController.class);
//		Stocks quote = stocksController.getQuote("AAPL");
//		System.out.println(quote);
	}
}