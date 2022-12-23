package com.example.springbootweb3.controller;

import com.example.springbootweb3.model.Transaction;
import com.example.springbootweb3.repository.TransactionRepository;
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

    @GetMapping("/{address}")
    public ResponseEntity<List<Transaction>> getAllTransactions(@PathVariable String address) {
        List<Transaction> txn = repository.findByAddress(address);

        if (txn.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(txn, HttpStatus.OK);
    }
}
