package com.example.springbootweb3.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.stream.StreamSupport;

@Service
public class HoubiService {
    private final String HOUBI_BASE_URL = "https://api.huobi.pro";

    public ResponseEntity<Mono<String>> getAllBookTickers() {
        WebClient client = WebClient.create(HOUBI_BASE_URL);

        Mono<String> result = client.get().uri("/market/tickers").retrieve().bodyToMono(String.class);

        return ResponseEntity.ok(result);
    }

    public Mono<Double> getPrice(String symbol, String type) {
        WebClient client = WebClient.create(HOUBI_BASE_URL);

        return client.get().uri("/market/tickers").retrieve().bodyToMono(String.class).map(response -> {
            try {
                // Parse the response into a JsonNode object
                ObjectMapper mapper = new ObjectMapper();
                JsonNode root = mapper.readTree(response);

                // Retrieve the data array
                JsonNode data = root.get("data");
                if (data == null || !data.isArray()) {
                    throw new IllegalArgumentException("Invalid data format");
                }

                // Filter the data array to include only the element with the specified symbol
                JsonNode filtered = StreamSupport.stream(data.spliterator(), false)
                        .filter(node -> node.get("symbol").asText().equals(symbol))
                        .findFirst()
                        .orElse(null);

                if (filtered == null) {
                    throw new IllegalArgumentException("Symbol not found");
                }

                // Retrieve the bid value, response is [{},{}], so we check if it's array, and we loop the array
                return filtered.get(type).asDouble();

            } catch (JsonProcessingException e) {

                throw new RuntimeException();
            }
        });
    }

    public Mono<Double> getBidPrice(String tokenSymbol) {
      return getPrice(tokenSymbol, "bid");
    }

    public Mono<Double> getAskPrice(String tokenSymbol) {
      return getPrice(tokenSymbol, "ask");
    }
}
