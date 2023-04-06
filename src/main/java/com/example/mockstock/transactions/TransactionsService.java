package com.example.mockstock.transactions;

import com.example.mockstock.portfolios.Portfolios;
import com.example.mockstock.portfolios.PortfoliosService;
import com.example.mockstock.stocks.StockService;
import com.example.mockstock.stocks.Stocks;
import com.example.mockstock.users.User;
import com.example.mockstock.users.UserNotFound;
import com.example.mockstock.users.UserRepository;
import com.example.mockstock.users.UserService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionsService {
    TransactionsRepository transactionsRepository;
    UserRepository userRepository;
    StockService stockService;
    UserService userService;
    PortfoliosService portfoliosService;

    public TransactionsService (TransactionsRepository transactionsRepository,
                                UserRepository userRepository,
                                StockService stockService,
                                UserService userService,
                                PortfoliosService portfoliosService) {
        this.transactionsRepository = transactionsRepository;
        this.userRepository = userRepository;
        this.stockService = stockService;
        this.userService = userService;
        this.portfoliosService = portfoliosService;
    }

    public List<Transactions> getTransactions(Long id) {
        return transactionsRepository.findByUserId(id);
    }

    public Transactions postTransactions(Transactions transactions, Long id) throws Exception {
        User user = userRepository.findById(id).orElse(null);
        if (user == null) throw new UserNotFound();

        Double userBalance = user.getBalance();
        Stocks stock = stockService.getQuote(transactions.getStockSymbol());
        transactions.setStockPrice(stock.getPrice());
        Double totalPrice = stock.getPrice() * transactions.getQuantity();

        if (transactions.getTransactionType().equals("buy")) {
            handleBuy(transactions, user, userBalance, totalPrice);
        } else {
            handleSell(transactions, user, userBalance, totalPrice);
        }
        return transactionsRepository.save(transactions);
    }

    private void handleBuy(Transactions transactions, User user, Double userBalance, Double totalPrice) throws Exception {
        if (totalPrice <= userBalance) {
            user.setBalance(userBalance - totalPrice);
            userService.updateUser(user.getId(), user.getBalance());

           if (portfoliosService.getPortfolioByStockSymbol(user.getId(), transactions.getStockSymbol()) == null) {
               Portfolios portfolio = new Portfolios(transactions.getStockSymbol(),
                       stockService.getCompanyName(transactions.getStockSymbol()),
                       transactions.getQuantity(), user);
               portfoliosService.addPortfolio(portfolio);
           } else {
               portfoliosService.updatePortfolio(user.getId(), transactions.getStockSymbol(), transactions.getQuantity(), transactions.getTransactionType());
           }
        } else {
            throw new InsufficientBalanceException();
        }
    }

    private void handleSell(Transactions transactions, User user, Double userBalance, Double totalPrice) {
        Portfolios userPortfolio = portfoliosService.getPortfolioByStockSymbol(user.getId(), transactions.getStockSymbol());
        if (userPortfolio == null || userPortfolio.getQuantity() < transactions.getQuantity()) {
            throw new InsufficentStocksException();
        }
        user.setBalance(userBalance + totalPrice);
        userService.updateUser(user.getId(), user.getBalance());

        portfoliosService.updatePortfolio(user.getId(), transactions.getStockSymbol(), transactions.getQuantity(), transactions.getTransactionType());
    }


    public User getUser(Long id) {
        return userRepository.findById(id).orElse(null);
    }
}
