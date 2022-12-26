package com.example.springbootweb3.service;

import com.example.springbootweb3.model.Token;
import com.example.springbootweb3.repository.TokenRepository;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class TokenService {
    @Autowired
    private TokenRepository repository;

    public Token getToken(String symbol) {
        // if multiple user, need to find by Id too
        return repository.findBySymbol(symbol);
    }

    public List<Token> getTokenByWalletId(Long walletId) {
        return repository.findAllByWalletId(walletId);
    }

    public Token saveToken(Token token) {
        return repository.save(token);
    }

    public Token updateBoughtToken(JsonNode orderDetails) {
        String baseSymbol = orderDetails.get("base").asText();
        BigDecimal amount = orderDetails.get("amount").decimalValue();
        Token existingToken = repository.findBySymbol(baseSymbol);

        if (existingToken == null) {
            Long wallet_id = orderDetails.get("wallet_id").asLong();
            BigDecimal balance = orderDetails.get("amount").decimalValue();

            Token newToken = new Token(wallet_id, baseSymbol, balance);
            return repository.save(newToken);
        } else {
            BigDecimal currentBalance = existingToken.getBalance();
            BigDecimal updatedBalance = currentBalance.add( amount );
            existingToken.setBalance(updatedBalance);

            return repository.save(existingToken);
        }
    }

    public Token updateSoldToken(JsonNode orderDetails) {
        String baseSymbol = orderDetails.get("base").asText();
        BigDecimal amount = orderDetails.get("amount").decimalValue();
        Token existingToken = repository.findBySymbol(baseSymbol);

        if (existingToken == null) {
            Long wallet_id = orderDetails.get("wallet_id").asLong();
            BigDecimal balance = orderDetails.get("amount").decimalValue();

            Token newToken = new Token(wallet_id, baseSymbol, balance);
            return repository.save(newToken);
        } else {
            BigDecimal currentBalance = existingToken.getBalance();
            BigDecimal updatedBalance = currentBalance.subtract(amount);
            existingToken.setBalance(updatedBalance);
        }

        return repository.save(existingToken);
    }
}
