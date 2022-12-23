package com.example.springbootweb3.controller;

import com.example.springbootweb3.model.Wallet;
import com.example.springbootweb3.repository.WalletRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping(path = "/api/v1/wallet")
public class WalletController {

    @Autowired
    private WalletRepository repository;

    @GetMapping("/{id}")
    public ResponseEntity<Wallet> getWalletById(@PathVariable Long id) {
        Optional<Wallet> wallet = repository.findById(id);

        if (wallet.isPresent()) {
            return new ResponseEntity<>(wallet.get(), HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
