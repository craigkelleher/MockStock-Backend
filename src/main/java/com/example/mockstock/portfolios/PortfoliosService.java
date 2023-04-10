package com.example.mockstock.portfolios;

import com.example.mockstock.users.User;
import com.example.mockstock.users.UserRepository;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Service
public class PortfoliosService {
    PortfoliosRepository portfoliosRepository;
    UserRepository userRepository;
    public PortfoliosService(PortfoliosRepository portfoliosRepository, UserRepository userRepository) {
        this.portfoliosRepository = portfoliosRepository;
        this.userRepository = userRepository;
    }
    public List<Portfolios> getPortfolios(Long id) {
        return portfoliosRepository.findByUserId(id); }

    public Portfolios getPortfolioByStockSymbol(Long id, String stockSymbol) {
        return portfoliosRepository.findByUserIdAndStockSymbol(id, stockSymbol).orElse(null);
    }

    public Portfolios addPortfolio(Portfolios portfolio) {
        return portfoliosRepository.save(portfolio); }

    public User getUser(Long id) {
        return userRepository.findById(id).orElse(null); }

    public void updatePortfolio(Long id, String stockSymbol, int quantity, String transactionType, Double profitLoss) {
        Portfolios portfolio = getPortfolioByStockSymbol(id, stockSymbol);
        if (transactionType.equals("buy")) {
            portfolio.setQuantity(portfolio.getQuantity() + quantity);

        } else {
            portfolio.setQuantity(portfolio.getQuantity() - quantity);

        }
        portfolio.setProfitLoss(profitLoss);
        portfoliosRepository.save(portfolio);
    }

    public void deletePortfolio(Long id, String stockSymbol) {
        Optional<Portfolios> portfolio = portfoliosRepository.findByUserIdAndStockSymbol(id, stockSymbol);

        if (portfolio.isPresent()) {
            portfoliosRepository.delete(portfolio.get());
        } else throw new PortfolioNotFound();


    }
}
