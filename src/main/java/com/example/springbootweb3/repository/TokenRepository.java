package com.example.springbootweb3.repository;

import com.example.springbootweb3.model.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TokenRepository extends JpaRepository<Token, Long> {
    Token findBySymbol(String symbol);
    List<Token> findAllByWalletId(Long walletId);
}
