package com.example.springbootweb3.controller;

import com.example.springbootweb3.model.Token;
import com.example.springbootweb3.repository.TokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/tokens")
public class TokenController {

    @Autowired
    private TokenRepository repository;

    @GetMapping
    public ResponseEntity<List<Token>> getAllTokens() {
        try {

            List<Token> tokens = new ArrayList<>();
            repository.findAll().forEach(tokens::add);

            if (tokens.isEmpty()) {
                return new ResponseEntity<>(tokens, HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
