package com.example.springbootweb3.service;

import com.example.springbootweb3.model.Price;
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
    public Mono<Price> getPrice(String symbol) {
        WebClient client = WebClient.create(BINANCE_BASE_URL);

        return client.get().uri(BOOKTICKER_API + "?symbol=" + symbol.toUpperCase()).retrieve().bodyToMono(String.class).map(response -> {
            try {
                // Parse the response into a JsonNode object
                ObjectMapper mapper = new ObjectMapper();
                JsonNode root = mapper.readTree(response);

                if (root == null) {
                    throw new IllegalArgumentException("Symbol not found");
                }

                // fill up Price entity with symbol, source (eg. Houbi or Binance), bidPrice and askPrice
                String tokenSymbol = root.get("symbol").asText();
                double bidPrice = root.get("bidPrice").asDouble();
                double askPrice = root.get("askPrice").asDouble();

                // Create a new Price object with the extracted values
                Price price = new Price();
                price.setSymbol(tokenSymbol);
                price.setSource("Binance");
                price.setBidPrice(bidPrice);
                price.setAskPrice(askPrice);

                // Return the Price object
                return price;
            } catch (JsonProcessingException e) {

                throw new RuntimeException();
            }
        });
    }

    public Double getBidPrice(String tokenSymbol) {
        return getPrice(tokenSymbol).block().getBidPrice();
    }

    public Double getAskPrice(String tokenSymbol) {
        return getPrice(tokenSymbol).block().getAskPrice();
    }
}
