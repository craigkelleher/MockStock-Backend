package com.example.mockstock.stocks;

import org.springframework.stereotype.Component;

@Component
public interface HttpClient {
    String get(String url) throws Exception;
}