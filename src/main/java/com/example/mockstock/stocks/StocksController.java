package com.example.mockstock.stocks;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.json.JSONObject;
import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;

@RestController
public class StocksController {

    private final String apiKey;

    public StocksController(@Value("${api.key}") String apiKey){
        this.apiKey = apiKey;
    }

    // Can make routes if we want more than quotes: financials, metrics

    public String getQuote(@PathVariable String symbol) throws Exception {
        String quoteUrl = "https://finnhub.io/api/v1/quote?symbol=" + symbol + "&token=" + apiKey;
        String profileUrl = "https://finnhub.io/api/v1/stock/profile2?symbol=" + symbol + "&token=" + apiKey;

        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            // Make the quote API call
            HttpGet quoteHttpGet = new HttpGet(quoteUrl);
            CloseableHttpResponse quoteResponse = httpClient.execute(quoteHttpGet);
            BufferedReader quoteReader = new BufferedReader(new InputStreamReader(
                    quoteResponse.getEntity().getContent()));
            StringBuilder quoteResult = new StringBuilder();
            String quoteLine;
            while ((quoteLine = quoteReader.readLine()) != null) {
                quoteResult.append(quoteLine);
            }

            // Make the profile API call
            HttpGet profileHttpGet = new HttpGet(profileUrl);
            CloseableHttpResponse profileResponse = httpClient.execute(profileHttpGet);
            BufferedReader profileReader = new BufferedReader(new InputStreamReader(
                    profileResponse.getEntity().getContent()));
            StringBuilder profileResult = new StringBuilder();
            String profileLine;
            while ((profileLine = profileReader.readLine()) != null) {
                profileResult.append(profileLine);
            }

            // Parse the JSON responses
            JSONObject quoteJson = new JSONObject(quoteResult.toString());
            JSONObject profileJson = new JSONObject(profileResult.toString());

            // Extract the relevant data
            String name = getCompanyName(symbol);
            double price = quoteJson.getDouble("c");
            double percentChange = quoteJson.getDouble("dp");

            // Build the output string
            return String.format("%s: current price=%.2f, today's change=%.2f%%", name, price, percentChange);
        }
    }

    // can make a GET request to /quotes?symbols=AAPL,MSFT
    @GetMapping("/quotes")
    public String getMultipleQuotes(@RequestParam List<String> symbols) throws Exception {
        String symbolsString = String.join(",", symbols);
        String url = "https://finnhub.io/api/v1/quote?symbol=" + symbolsString + "&token=" + apiKey;
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

        JSONArray jsonArray = new JSONArray(result.toString());
        StringBuilder outputBuilder = new StringBuilder();
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            String symbol = jsonObject.getString("symbol");
            double price = jsonObject.getDouble("c");
            double percentChange = jsonObject.getDouble("dp");
            String name = getCompanyName(symbol);

            String outputString = String.format("%s: current price=%.2f, today's change=%.2f%%", name, price, percentChange);
            outputBuilder.append(outputString);
            if (i != jsonArray.length() - 1) {
                outputBuilder.append(System.lineSeparator());
            }
        }
        return outputBuilder.toString();
    }

    @GetMapping("/companyName/{symbol}")
    public String getCompanyName(@PathVariable String symbol) throws Exception {
        String url = "https://finnhub.io/api/v1/stock/profile2?symbol=" + symbol + "&token=" + apiKey;
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpGet httpGet = new HttpGet(url);
            CloseableHttpResponse response = httpClient.execute(httpGet);
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    response.getEntity().getContent()));
            StringBuilder result = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                result.append(line);
            }
            JSONObject json = new JSONObject(result.toString());
            return json.getString("name");
        }
    }
}