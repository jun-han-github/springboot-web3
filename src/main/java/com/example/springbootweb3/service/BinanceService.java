package com.example.springbootweb3.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class BinanceService {

    private final String BINANCE_BASE_URL ="https://api.binance.com";
    private final String BOOKTICKER_API = "/api/v3/ticker/bookTicker";
    public ResponseEntity<Mono<String>> getAllBookTickers() {

        WebClient client = WebClient.create(BINANCE_BASE_URL);
        Mono<String> result = client.get().uri(BOOKTICKER_API).retrieve().bodyToMono(String.class);

        return ResponseEntity.ok(result);
    }

    public ResponseEntity<Mono<String>> getOneBookTicker(String symbol) {
        WebClient client = WebClient.create(BINANCE_BASE_URL);
        Mono<String> result = client.get().uri(BOOKTICKER_API + "?symbol=" + symbol.toUpperCase()).retrieve().bodyToMono(String.class);
        return ResponseEntity.ok(result);
    }
    public Mono<Double> getPrice(String symbol, String type) {
        WebClient client = WebClient.create(BINANCE_BASE_URL);

        return client.get().uri(BOOKTICKER_API + "?symbol=" + symbol.toUpperCase()).retrieve().bodyToMono(String.class).map(response -> {
            try {
                // Parse the response into a JsonNode object
                ObjectMapper mapper = new ObjectMapper();
                JsonNode root = mapper.readTree(response);

                if (root == null) {
                    throw new IllegalArgumentException("Symbol not found");
                }

                // Retrieve the bid value, response is {}, so can just .get()
                return root.get(type).asDouble();

            } catch (JsonProcessingException e) {

                throw new RuntimeException();
            }
        });
    }

    public Mono<Double> getBidPrice(String tokenSymbol) {
        return getPrice(tokenSymbol, "bidPrice");
    }

    public Mono<Double> getAskPrice(String tokenSymbol) {
        return getPrice(tokenSymbol, "askPrice");
    }
}
