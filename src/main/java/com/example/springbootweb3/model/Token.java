package com.example.springbootweb3.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Data
@Entity
@Table(name="token")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Token {

    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "token_sequence"
    )
    @SequenceGenerator(
            name = "token_sequence",
            sequenceName = "token_sequence",
            allocationSize = 1
    )
    private Long id;
    private Long walletId;
    private String symbol;
    private BigDecimal balance;

    public Token(Long walletId, String symbol, BigDecimal balance) {
        this.walletId = walletId;
        this.symbol = symbol;
        this.balance = balance;
    }
}
