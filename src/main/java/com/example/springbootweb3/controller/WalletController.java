package com.example.springbootweb3.controller;

import com.example.springbootweb3.model.Wallet;
import com.example.springbootweb3.repository.WalletRepository;
import com.example.springbootweb3.service.PriceService;
import com.example.springbootweb3.service.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.Optional;

@RestController
@RequestMapping(path = "/api/v1/wallet")
public class WalletController {

    @Autowired
    private WalletRepository repository;

    @Autowired
    private WalletService service;

    @Autowired
    private PriceService priceService;

    @GetMapping("/{id}")
    public ResponseEntity<Wallet> getWalletById(@PathVariable Long id) {
        Optional<Wallet> wallet = repository.findById(id);

        if (wallet.isPresent()) {
            return new ResponseEntity<>(wallet.get(), HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/tokens/{walletId}")
    public BigDecimal latestWalletBalance(@PathVariable Long walletId) {
        return service.updateWalletBalance(walletId);
    }
}
