package com.example.mockstock.stocks;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
public class StocksControllerTests {
    private final StockService stockService = Mockito.mock(StockService.class);
    private final StocksController stocksController = new StocksController(stockService);

    @Test
    public void testGetQuote() throws Exception {
        String symbol = "AAPL";
        Stocks expectedStocks = new Stocks(symbol, "Apple Inc.", 200.00, 0.20);

        when(stockService.getQuote(anyString())).thenReturn(expectedStocks);

        Stocks result = stocksController.getQuote(symbol);

        assertEquals(expectedStocks, result);
    }
    @Test
    public void testGetMultipleQuotes() throws Exception {
        List<String> symbols = Arrays.asList("AAPL", "GOOG");
        Stocks apple = new Stocks("AAPL", "Apple Inc.", 200.00, 0.20);
        Stocks google = new Stocks("GOOG", "Alphabet Inc.", 1000.00, 0.50);
        List<Stocks> expectedQuotes = Arrays.asList(apple, google);

        when(stockService.getMultipleQuotes(anyList())).thenReturn(expectedQuotes);

        List<Stocks> result = stocksController.getMultipleQuotes(symbols);

        assertEquals(expectedQuotes, result);
    }
    @Test
    public void testGetCompanyName() throws Exception {
        String symbol = "AAPL";
        String expectedCompanyName = "Apple Inc.";

        when(stockService.getCompanyName(anyString())).thenReturn(expectedCompanyName);

        String result = stocksController.getCompanyName(symbol);

        assertEquals(expectedCompanyName, result);
    }
}
