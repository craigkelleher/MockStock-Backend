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
//		StocksController stocksController = context.getBean(StocksController.class);
//		Stocks quote = stocksController.getQuote("AAPL");
//		System.out.println(quote);

		User user = new User("someone", "something", "password", 1000.00);
		Transactions transaction = new Transactions("AAPL", "buy", 3, user);


	}
}