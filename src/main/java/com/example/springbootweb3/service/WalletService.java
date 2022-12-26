package com.example.springbootweb3.service;

import com.example.springbootweb3.model.Price;
import com.example.springbootweb3.model.Token;
import com.example.springbootweb3.model.Wallet;
import com.example.springbootweb3.repository.WalletRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class WalletService {

    @Autowired
    private WalletRepository repository;
    @Autowired
    private TokenService tokenService;
    @Autowired
    private PriceService priceService;

    public BigDecimal updateWalletBalance(Long walletId) {
        Wallet wallet = repository.findById(walletId).orElse(null);
        List<Token> tokens = tokenService.getTokenByWalletId(walletId);
        BigDecimal walletBalance = BigDecimal.ZERO;

        for (Token token : tokens) {
            if (token.getSymbol().toLowerCase().equals("usdt")) {
                walletBalance = walletBalance.add( token.getBalance() );
                continue;
            }

            String tokenSymbol = token.getSymbol() + "USDT";
            Price price = priceService.bestPrice(tokenSymbol, "bid");
            BigDecimal balance = token.getBalance().multiply( price.getBidPrice() );
            walletBalance = walletBalance.add( balance );
        };

        wallet.setBalance(walletBalance);
        repository.save(wallet);
        return walletBalance;
    }

}
