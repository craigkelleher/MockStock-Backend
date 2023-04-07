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

    public List<Transactions> getTransactionsByStockSymbol(Long id, String stockSymbol) {
        return transactionsRepository.findByUserIdAndStockSymbol(id, stockSymbol);
    }

    public Transactions postTransactions(Transactions transactions, Long id) throws Exception {
        User user = userRepository.findById(id).orElse(null);
        if (user == null) throw new UserNotFound();

        Double userBalance = user.getBalance();
        Stocks stock = stockService.getQuote(transactions.getStockSymbol());
        // TODO: CHANGE THIS TO stock.getPrice()
        transactions.setStockPrice(200.00);
        // TODO: CHANGE THIS TO stock.getPrice()
        Double totalPrice = transactions.getStockPrice() * transactions.getQuantity();

        if (transactions.getTransactionType().equals("buy")) {
            handleBuy(transactions, user, totalPrice, stock);
        } else {
            handleSell(transactions, user, userBalance, totalPrice);
        }
        return transactionsRepository.save(transactions);
    }

    private void handleBuy(Transactions transactions, User user, Double totalPrice, Stocks stock) throws Exception {
        if (totalPrice > user.getBalance()) {
            throw new InsufficientBalanceException();
        }
        user.setBalance(user.getBalance() - totalPrice);
        userService.updateUser(user.getId(), user.getBalance());

       if (portfoliosService.getPortfolioByStockSymbol(user.getId(), transactions.getStockSymbol()) == null) {

           List<Transactions> allTransactions = getTransactionsByStockSymbol(user.getId(), transactions.getStockSymbol());
           Double sum = 0.00;
           for (Transactions transaction : allTransactions) {
               if (transaction.getTransactionType().equals("buy")) {
                   sum += transaction.getStockPrice();
               } else {
                   sum -= transaction.getStockPrice();
               }
           }
           /*
                profit/loss = (current price - purchase price) * shares
                portfolio profit/loss = sum(stock profit/loss for all stocks in portfolio)
                (current price of a stock - average purchase price for a stock * shares)
           */
           Double profitLoss = (stock.getPrice() - (sum / allTransactions.size())) * transactions.getQuantity();
           // TODO: CHANGE THIS TO stock.getPrice()
           Portfolios portfolio = new Portfolios(transactions.getStockSymbol(),
                   stockService.getCompanyName(transactions.getStockSymbol()),
                   transactions.getQuantity(),
                   200.00,
                   profitLoss,
                   user);
           portfoliosService.addPortfolio(portfolio);
       } else {
           portfoliosService.updatePortfolio(user.getId(), transactions.getStockSymbol(), transactions.getQuantity(), transactions.getTransactionType());
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
