package com.example.springbootweb3.controller;

import com.example.springbootweb3.service.BinanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class BinanceController {

    @Autowired
    private BinanceService service;

    @GetMapping("api/binance/book-ticker")
    public ResponseEntity<Mono<String>> getAllBookTickers() {
        return service.getAllBookTickers();
    }
}
