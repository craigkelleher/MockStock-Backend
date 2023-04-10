package com.example.mockstock.stocks;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
public class StocksController {
    private final StockService stockService;

    public StocksController(StockService stockService) {
        this.stockService = stockService;
    }

    @GetMapping("/quotes/{symbol}")
    public Stocks getQuote(@PathVariable String symbol) throws Exception {
        return stockService.getQuote(symbol);
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/quotes")
    public List<Stocks> getMultipleQuotes(@RequestParam List<String> symbols) throws Exception {
        return stockService.getMultipleQuotes(symbols);
    }

    @GetMapping("/companyName/{symbol}")
    public String getCompanyName(@PathVariable String symbol) throws Exception {
        return stockService.getCompanyName(symbol);
    }
}