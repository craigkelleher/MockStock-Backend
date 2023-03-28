package com.example.mockstock.stocks;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;

@RestController
public class StocksController {
    private final String API_KEY;

    public StocksController(String API_KEY){
        this.API_KEY = API_KEY;
    }

    @GetMapping("/quotes/{symbol}")
    public String getQuote(@PathVariable String symbol) throws Exception {
        String url = "https://finnhub.io/api/v1/quote?symbol=" + symbol + "&token=" + API_KEY;
        CloseableHttpResponse httpResponse;
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpGet httpGet = new HttpGet(url);
            httpResponse = httpClient.execute(httpGet);
        }
        BufferedReader reader = new BufferedReader(new InputStreamReader(
                httpResponse.getEntity().getContent()));
        StringBuilder result = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            result.append(line);
        }
        return result.toString();
    }

    // can make a GET request to /quotes?symbols=AAPL,MSFT
    @GetMapping("/quotes")
    public String getMultipleQuotes(@RequestParam List<String> symbols) throws Exception {
        String symbolsString = String.join(",", symbols);
        String url = "https://finnhub.io/api/v1/quote?symbol=" + symbolsString + "&token=" + this.API_KEY;
        CloseableHttpResponse httpResponse;
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpGet httpGet = new HttpGet(url);
            httpResponse = httpClient.execute(httpGet);
        }
        BufferedReader reader = new BufferedReader(new InputStreamReader(
                httpResponse.getEntity().getContent()));
        StringBuilder result = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            result.append(line);
        }
        return result.toString();
    }
}