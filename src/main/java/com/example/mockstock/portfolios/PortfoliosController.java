package com.example.mockstock.portfolios;

import com.example.mockstock.users.User;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/user")
public class PortfoliosController {
    PortfoliosService portfoliosService;

    public PortfoliosController(PortfoliosService portfoliosService) {
        this.portfoliosService = portfoliosService;
    }

    @GetMapping("/{id}/portfolio")
    public List<Portfolios> getPortfolio(@PathVariable Long id) { return portfoliosService.getPortfolios(id); }

    @PostMapping("/{id}/portfolio")
    public Portfolios addPortfolio(@PathVariable Long id, @RequestBody Portfolios portfolio) {
        User user = portfoliosService.getUser(id);
        portfolio.setUser(user);
        return portfoliosService.addPortfolio(portfolio);
    }


}
