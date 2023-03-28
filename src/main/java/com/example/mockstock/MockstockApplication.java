package com.example.mockstock;

import com.example.mockstock.stocks.StocksController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MockstockApplication {

	public static void main(String[] args) {
		StocksController stockQuote = new StocksController(System.getProperty("API_KEY"));
		SpringApplication.run(MockstockApplication.class, args);
	}

}
