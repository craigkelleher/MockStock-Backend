package com.example.mockstock;

import com.example.mockstock.stocks.StocksController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MockstockApplication {

	public static void main(String[] args) throws Exception {
		SpringApplication.run(MockstockApplication.class, args);
		StocksController stocksController = new StocksController(System.getProperty("API_KEY"));
		System.out.println("Apple quote: " + stocksController.getQuote("AAPL"));
	}
}