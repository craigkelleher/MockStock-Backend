package com.example.mockstock;

import com.example.mockstock.stocks.Stocks;
import com.example.mockstock.stocks.StocksController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class MockstockApplication {
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	public static void main(String[] args) throws Exception {
		ApplicationContext context = SpringApplication.run(MockstockApplication.class, args);
		StocksController stocksController = context.getBean(StocksController.class);
		Stocks quote = stocksController.getQuote("AAPL");
		System.out.println(quote);
	}
}