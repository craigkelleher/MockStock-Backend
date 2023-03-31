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
		StocksController stocksController = context.getBean(StocksController.class);
		Stocks quote = stocksController.getQuote("AAPL");
		System.out.println(quote);

//		ConfigurableApplicationContext configurableApplicationContext = SpringApplication.run(MockstockApplication.class, args);
//		UserRepository userRepository = configurableApplicationContext.getBean(UserRepository.class);
//		PortfoliosRepository portfoliosRepository = configurableApplicationContext.getBean(PortfoliosRepository.class);
//		TransactionsRepository transactionsRepository = configurableApplicationContext.getBean(TransactionsRepository.class);
//
//
//		User user = new User("Robert", "rvtaylor94@gmail.com", "something", 100.75);
//		Transactions transaction1 = new Transactions("AAPL", "buy", 40.00, 5, user);
//		Transactions transaction2 = new Transactions("MSFT", "buy", 35.00, 7, user);
//		Portfolios portfolio1 = new Portfolios("AAPL", 5, user);
//		Portfolios portfolio2 = new Portfolios("MSFT", 7, user);
//
//		List<Transactions> transactions = Arrays.asList(transaction1, transaction2);
//		List<Portfolios> portfolios = Arrays.asList(portfolio1, portfolio2);
//		user.setPortfolios(portfolios);
//		userRepository.save(user);
//		portfoliosRepository.save(portfolio1);
//		portfoliosRepository.save(portfolio2);
//		transactionsRepository.save(transaction1);
//		transactionsRepository.save(transaction2);
//
//		userRepository.deleteAll();
//		portfoliosRepository.deleteAll();
//		transactionsRepository.deleteAll();
	}
}