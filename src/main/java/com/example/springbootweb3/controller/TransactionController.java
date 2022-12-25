package com.example.springbootweb3.controller;

import com.example.springbootweb3.model.Token;
import com.example.springbootweb3.model.Transaction;
import com.example.springbootweb3.repository.TransactionRepository;
import com.example.springbootweb3.service.PriceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/transactions")
public class TransactionController {

    @Autowired
    private TransactionRepository repository;
    @Autowired
    private PriceService priceService;

    @GetMapping("/{address}")
    public ResponseEntity<List<Transaction>> getAllTransactions(@PathVariable String address) {
        List<Transaction> txn = repository.findByAddress(address);

        if (txn.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(txn, HttpStatus.OK);
    }

    @PostMapping("/buy")
    public ResponseEntity<Double> buyToken(@RequestBody Token token) {
        String tokenSymbol = token.getSymbol().toLowerCase();
        // check current token balance

        // best ask price appears here
        Double bestPrice = priceService.bestAskPrice(tokenSymbol);

        // save new balance and new purchase token value

        return new ResponseEntity<>(bestPrice, HttpStatus.OK);
    }
    @PostMapping("/sell")
    public ResponseEntity<Double> sellToken(@RequestBody Token token) {
        String tokenSymbol = token.getSymbol().toLowerCase();

        // check current token balance

        // best bid price appears here
        Double bestPrice = priceService.bestBidPrice(tokenSymbol);

        // save new balance and new sold token value


        return new ResponseEntity<>(bestPrice, HttpStatus.OK);
    }
}
