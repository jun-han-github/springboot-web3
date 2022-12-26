package com.example.springbootweb3.service;

import com.example.springbootweb3.model.Price;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.util.stream.StreamSupport;

@Service
public class HoubiService {
    private final String HOUBI_BASE_URL = "https://api.huobi.pro";

    public ResponseEntity<Mono<String>> getAllBookTickers() {
        WebClient client = WebClient.create(HOUBI_BASE_URL);

        Mono<String> result = client.get().uri("/market/tickers").retrieve().bodyToMono(String.class);

        return ResponseEntity.ok(result);
    }

    public Mono<Price> getPrice(String symbol) {
        WebClient client = WebClient.create(HOUBI_BASE_URL);

        Mono<Price> result = client.get().uri("/market/tickers").retrieve().bodyToMono(String.class).map(response -> {
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
                        .filter(node -> node.get("symbol").asText().equals( symbol.toLowerCase() ))
                        .findFirst()
                        .orElse(null);

                if (filtered == null) {
                    throw new IllegalArgumentException("Symbol not found");
                }

                // Retrieve the bid value, response is [{},{}], so we check if it's array, and we loop the array
                // fill up Price entity with symbol, source (eg. Houbi or Binance), bidPrice and askPrice
                String tokenSymbol = filtered.get("symbol").asText();
                BigDecimal bidPrice = new BigDecimal( filtered.get("bid").asText() );
                BigDecimal askPrice = new BigDecimal( filtered.get("ask").asText() );

                // Create a new Price object with the extracted values
                Price price = new Price();
                price.setSymbol(tokenSymbol);
                price.setSource("Houbi");
                price.setBidPrice(bidPrice);
                price.setAskPrice(askPrice);

                return price;

            } catch (JsonProcessingException e) {

                throw new RuntimeException();
            }
        });
        return result;
    }
}
