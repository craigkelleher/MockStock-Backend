package com.example.mockstock;

import com.example.mockstock.stocks.StocksController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class MockstockApplication {

	public static void main(String[] args) throws Exception {
		ApplicationContext context = SpringApplication.run(MockstockApplication.class, args);
		StocksController stocksController = context.getBean(StocksController.class);
		String quote = stocksController.getQuote("AAPL");
		System.out.println(quote);
	}
}