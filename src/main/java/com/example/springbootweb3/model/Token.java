package com.example.springbootweb3.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Data
@Entity
@Table(name="token")
@NoArgsConstructor
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
    private Long wallet_id;
    private String symbol;
    private BigDecimal balance;

    public Token(Long wallet_id, String symbol, BigDecimal balance) {
        this.wallet_id = wallet_id;
        this.symbol = symbol;
        this.balance = balance;
    }
}
