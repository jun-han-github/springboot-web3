package com.example.springbootweb3.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name="transaction")
@Getter
@Setter
public class Transaction {

    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "txn_sequence"
    )
    @SequenceGenerator(
            name = "txn_sequence",
            sequenceName = "txn_sequence",
            allocationSize = 1
    )
    private Long id;
    private String address;
    private String symbol;
    private String type; // Buy or Sell
    private BigDecimal amount;
    private BigDecimal price;
    private String status; // success, failed
    private LocalDateTime timestamp;
}
