package com.example.mockstock.portfolios;

import com.example.mockstock.users.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/user")
public class PortfoliosController {
    PortfoliosService portfoliosService;

    public PortfoliosController(PortfoliosService portfoliosService) {
        this.portfoliosService = portfoliosService;
    }

    @GetMapping("/portfolio")
    public List<Portfolios> getPortfolio(HttpServletRequest request) {
        Long id = (Long) request.getAttribute("userId");
        System.out.println("User ID: " + id);

        return portfoliosService.getPortfolios(id);
    }

    @PostMapping("/portfolio")
    public Portfolios addPortfolio(HttpServletRequest request, @RequestBody Portfolios portfolio) {
        Long id = (Long) request.getAttribute("userId");
        User user = portfoliosService.getUser(id);
        portfolio.setUser(user);
        return portfoliosService.addPortfolio(portfolio);
    }

    @DeleteMapping("/portfolio/{stockSymbol}")
    public ResponseEntity removePortfolio(HttpServletRequest request, @PathVariable String stockSymbol) {
        try {
            Long id = (Long) request.getAttribute("userId");
            portfoliosService.deletePortfolio(id, stockSymbol);
        } catch (PortfolioNotFound e) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.accepted().build();
    }
}
