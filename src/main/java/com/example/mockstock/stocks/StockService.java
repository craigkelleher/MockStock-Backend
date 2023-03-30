package com.example.mockstock.stocks;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class StockService {
    private final String apiKey;
    private final HttpClient httpClient;

    public StockService(@Value("${api.key}")String apiKey, HttpClient httpClient) {
        this.apiKey = apiKey;
        this.httpClient = httpClient;
    }

    public Stocks getQuote(String symbol) throws Exception {
        String quoteUrl = "https://finnhub.io/api/v1/quote?symbol=" + symbol + "&token=" + apiKey;
        String profileUrl = "https://finnhub.io/api/v1/stock/profile2?symbol=" + symbol + "&token=" + apiKey;

        String quoteResult = httpClient.get(quoteUrl);
        String profileResult = httpClient.get(profileUrl);

        JSONObject quoteJson = new JSONObject(quoteResult);
        JSONObject profileJson = new JSONObject(profileResult);

        String name = profileJson.getString("name");
        double price = quoteJson.getDouble("c");
        double percentChange = quoteJson.getDouble("dp");

        return new Stocks(symbol, name, price, percentChange);
    }

    public List<Stocks> getMultipleQuotes(List<String> symbols) throws Exception {
        List<Stocks> quotes = new ArrayList<>();
        for (String symbol : symbols) {
            Stocks quote = getQuote(symbol);
            quotes.add(quote);
        }
        return quotes;
    }

    public String getCompanyName(String symbol) throws Exception {
        String url = "https://finnhub.io/api/v1/stock/profile2?symbol=" + symbol + "&token=" + apiKey;
        String result = httpClient.get(url);
        JSONObject json = new JSONObject(result);
        return json.getString("name");
    }
}