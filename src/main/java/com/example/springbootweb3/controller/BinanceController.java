package com.example.springbootweb3.controller;

import com.example.springbootweb3.service.BinanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;


@RestController
@RequestMapping(path = "api/binance/book-ticker")
public class BinanceController {

    @Autowired
    private BinanceService service;

    @GetMapping
    public ResponseEntity<Mono<String>> getAllBookTickers() {
        return service.getAllBookTickers();
    }

    /*
    * Example for @PathVariable symbol = 'btcusdt'
    *
    * */
    @GetMapping("/{symbol}")
    public ResponseEntity<Mono<String>> getOneBookTicker(@PathVariable String symbol) {
        return service.getOneBookTicker(symbol);
    }
}
