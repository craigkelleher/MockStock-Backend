package com.example.mockstock.stocks;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;


import static org.junit.jupiter.api.Assertions.assertEquals;

public class StockServiceTests {
    private StockService stockService;

    @Mock
    private HttpClient httpClient;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        stockService = new StockService("testApiKey", httpClient);
    }

    @Test
    void testGetQuote() throws Exception {
        String symbol = "AAPL";
        String quoteResult = "{\"c\":200.0,\"dp\":0.20}";
        String profileResult = "{\"name\":\"Apple Inc.\"}";
        when(httpClient.get("https://finnhub.io/api/v1/quote?symbol=" + symbol + "&token=testApiKey"))
                .thenReturn(quoteResult);
        when(httpClient.get("https://finnhub.io/api/v1/stock/profile2?symbol=" + symbol + "&token=testApiKey"))
                .thenReturn(profileResult);

        Stocks expectedStocks = new Stocks(symbol, "Apple Inc.", 200.0, 0.20);
        Stocks actualStocks = stockService.getQuote(symbol);

        assertEquals(expectedStocks.toString(), actualStocks.toString());
    }

    @Test
    void testGetMultipleQuotes() throws Exception {
        List<String> symbols = new ArrayList<>();
        symbols.add("AAPL");
        symbols.add("GOOGL");
        String quoteResult1 = "{\"c\":200.0,\"dp\":0.20}";
        String profileResult1 = "{\"name\":\"Apple Inc.\"}";
        String quoteResult2 = "{\"c\":1000.0,\"dp\":0.50}";
        String profileResult2 = "{\"name\":\"Alphabet Inc.\"}";
        when(httpClient.get("https://finnhub.io/api/v1/quote?symbol=AAPL&token=testApiKey"))
                .thenReturn(quoteResult1);
        when(httpClient.get("https://finnhub.io/api/v1/stock/profile2?symbol=AAPL&token=testApiKey"))
                .thenReturn(profileResult1);
        when(httpClient.get("https://finnhub.io/api/v1/quote?symbol=GOOGL&token=testApiKey"))
                .thenReturn(quoteResult2);
        when(httpClient.get("https://finnhub.io/api/v1/stock/profile2?symbol=GOOGL&token=testApiKey"))
                .thenReturn(profileResult2);

        List<Stocks> expectedStocks = new ArrayList<>();
        expectedStocks.add(new Stocks("AAPL", "Apple Inc.", 200.0, 0.20));
        expectedStocks.add(new Stocks("GOOGL", "Alphabet Inc.", 1000.0, 0.50));
        List<Stocks> actualStocks = stockService.getMultipleQuotes(symbols);

        assertEquals(expectedStocks.toString(), actualStocks.toString());
    }

    @Test
    void testGetCompanyName() throws Exception {
        String symbol = "AAPL";
        String profileResult = "{\"name\":\"Apple Inc.\"}";
        when(httpClient.get("https://finnhub.io/api/v1/stock/profile2?symbol=" + symbol + "&token=testApiKey"))
                .thenReturn(profileResult);

        String expectedCompanyName = "Apple Inc.";
        String actualCompanyName = stockService.getCompanyName(symbol);

        assertEquals(expectedCompanyName, actualCompanyName);
    }

}
