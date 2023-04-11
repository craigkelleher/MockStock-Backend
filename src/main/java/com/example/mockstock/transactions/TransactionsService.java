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

import java.util.Date;
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

    public List<Transactions> getTransactionsByStockSymbol(Long id, String stockSymbol) {
        return transactionsRepository.findByUserIdAndStockSymbol(id, stockSymbol);
    }

    public Transactions postTransactions(Transactions transaction, Long id) throws Exception {

        User user = userRepository.findById(id).orElse(null);
        if (user == null) throw new UserNotFound();

        Double userBalance = user.getBalance();
        Stocks stock = stockService.getQuote(transaction.getStockSymbol());

        transaction.setStockCost(stock.getPrice());
        transaction.setDate(new Date());

        Double totalPrice = stock.getPrice() * transaction.getQuantity();

        Double profitLoss = getProfitLoss(transaction, user, stock);

        if (transaction.getTransactionType().equals("buy")) {
            handleBuy(transaction, user, totalPrice, profitLoss);
        } else {
            handleSell(transaction, user, userBalance, totalPrice, profitLoss);
        }
        return transactionsRepository.save(transaction);
    }

    private Double getProfitLoss(Transactions transaction, User user, Stocks stock) {
        List<Transactions> allTransactions = getTransactionsByStockSymbol(user.getId(), transaction.getStockSymbol());

        allTransactions.add(transaction);

        double totalCost = 0.0;
        int totalShares = 0;
        for (Transactions t : allTransactions) {
            if (t.getTransactionType().equals("buy")) {
                totalCost += t.getStockCost() * t.getQuantity();
                totalShares += t.getQuantity();
            } else {
                totalCost -= t.getStockCost() * t.getQuantity();
                totalShares -= t.getQuantity();
            }
        }
        if (totalShares == 0) {
            return 0.00;
        }
        double averagePrice = totalCost / totalShares;
        return (stock.getPrice() - averagePrice) * transaction.getQuantity();
    }



    private void handleBuy(Transactions transaction, User user, Double totalPrice, Double profitLoss) throws Exception {
        if (totalPrice > user.getBalance()) {
            throw new InsufficientBalanceException();
        }
        user.setBalance(user.getBalance() - totalPrice);
        userService.updateUser(user.getId(), user.getBalance());

       if (portfoliosService.getPortfolioByStockSymbol(user.getId(), transaction.getStockSymbol()) == null) {

           Portfolios portfolio = new Portfolios(transaction.getStockSymbol(),
                   stockService.getCompanyName(transaction.getStockSymbol()),
                   transaction.getQuantity(),
                   profitLoss,
                   user);
           portfoliosService.addPortfolio(portfolio);
       } else {
           portfoliosService.updatePortfolio(user.getId(), transaction.getStockSymbol(), transaction.getQuantity(), transaction.getTransactionType(), profitLoss);
       }
    }

    private void handleSell(Transactions transactions, User user, Double userBalance, Double totalPrice, Double profitLoss) {
        Portfolios userPortfolio = portfoliosService.getPortfolioByStockSymbol(user.getId(), transactions.getStockSymbol());
        if (userPortfolio == null || userPortfolio.getQuantity() < transactions.getQuantity()) {
            throw new InsufficentStocksException();
        }
        user.setBalance(userBalance + totalPrice);
        userService.updateUser(user.getId(), user.getBalance());

        portfoliosService.updatePortfolio(user.getId(), transactions.getStockSymbol(), transactions.getQuantity(), transactions.getTransactionType(), profitLoss);
    }

    public User getUser(Long id) {
        return userRepository.findById(id).orElse(null);
    }
}
