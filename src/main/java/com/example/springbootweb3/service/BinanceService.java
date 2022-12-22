package com.example.springbootweb3.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class BinanceService {

    public ResponseEntity<Mono<String>> getAllBookTickers() {
        WebClient client = WebClient.create("https://api.binance.com");

        Mono<String> result = client.get().uri("/api/v3/ticker/bookTicker").retrieve().bodyToMono(String.class);
        return ResponseEntity.ok(result);
    }
}
