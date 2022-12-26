package com.example.springbootweb3.controller;

import com.example.springbootweb3.model.Price;
import com.example.springbootweb3.model.Token;
import com.example.springbootweb3.model.Transaction;
import com.example.springbootweb3.repository.TransactionRepository;
import com.example.springbootweb3.service.BinanceService;
import com.example.springbootweb3.service.HoubiService;
import com.example.springbootweb3.service.PriceService;
import com.example.springbootweb3.service.TokenService;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/transactions")
public class TransactionController {

    @Autowired
    private TransactionRepository repository;
    @Autowired
    private PriceService priceService;
    @Autowired
    private TokenService tokenService;
    @Autowired
    private BinanceService binanceService;
    @Autowired
    private HoubiService houbiService;

    @GetMapping("/{address}")
    public ResponseEntity<List<Transaction>> getAllTransactions(@PathVariable String address) {
        List<Transaction> txn = repository.findByAddress(address);

        if (txn.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(txn, HttpStatus.OK);
    }

    @PostMapping("/buy")
    public ResponseEntity<Transaction> buyToken(@RequestBody JsonNode orderDetails) {

        String tokenSymbol = orderDetails.get("symbol").asText();
        String quoteSymbol = orderDetails.get("quote").asText(); // USDT
        BigDecimal amount = orderDetails.get("amount").decimalValue();

        // best ask price appears here
        Price bestPrice = priceService.bestPrice(tokenSymbol, "ask");

        // calculate cost
        BigDecimal totalCost = bestPrice.getAskPrice().multiply(amount);

        // check current token balance
        Token currentToken = tokenService.getToken(quoteSymbol);
        BigDecimal currentTokenBalance = currentToken.getBalance();

        if (currentTokenBalance.compareTo(totalCost) < 0) {
            throw new IllegalArgumentException("Insufficient funds for purchase");
        }

        // save new balance
        currentToken.setBalance( currentTokenBalance.subtract(totalCost) );
        tokenService.saveToken(currentToken);

        // new purchase token / value
        tokenService.updateBoughtToken(orderDetails);

        // transaction record
        Transaction newTxn = new Transaction();
        newTxn.setAddress("0x123456");
        newTxn.setSymbol(tokenSymbol.toUpperCase());
        newTxn.setType("Buy");
        newTxn.setAmount(amount);
        newTxn.setPrice(bestPrice.getAskPrice());
        newTxn.setSource(bestPrice.getSource());
        newTxn.setTimestamp(LocalDateTime.now());
        repository.save(newTxn);

        return new ResponseEntity<>(newTxn, HttpStatus.OK);
    }
    @PostMapping("/sell")
    public ResponseEntity<Transaction> sellToken(@RequestBody JsonNode orderDetails) {

        String tokenSymbol = orderDetails.get("symbol").asText();
        String quoteSymbol = orderDetails.get("quote").asText();
        BigDecimal amount = orderDetails.get("amount").decimalValue();

        // best bid price appears here
        Price bestPrice = priceService.bestPrice(tokenSymbol, "bid");

        // calculate gain
        BigDecimal totalGain = bestPrice.getBidPrice().multiply(amount);

        // check current token balance
        Token currentToken = tokenService.getToken(quoteSymbol);
        BigDecimal currentTokenBalance = currentToken.getBalance();

        if (currentTokenBalance.compareTo(amount) < 0) {
            throw new IllegalArgumentException("Insufficient funds to sell");
        }

        // save new balance
        currentToken.setBalance( currentTokenBalance.add(totalGain) );
        tokenService.saveToken(currentToken);

        // new purchase token / value
        tokenService.updateSoldToken(orderDetails);

        // transaction record
        Transaction newTxn = new Transaction();
        newTxn.setAddress("0x123456");
        newTxn.setSymbol(tokenSymbol.toUpperCase());
        newTxn.setType("Sell");
        newTxn.setAmount(amount);
        newTxn.setPrice(bestPrice.getBidPrice());
        newTxn.setSource(bestPrice.getSource());
        newTxn.setTimestamp(LocalDateTime.now());
        repository.save(newTxn);

        return new ResponseEntity<>(newTxn, HttpStatus.OK);
    }
}
