package com.example.springbootweb3.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name="price")
@Getter
@Setter
public class Price {

    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "price_sequence"
    )
    @SequenceGenerator(
            name = "price_sequence",
            sequenceName = "price_sequence",
            allocationSize = 1
    )
    private Long id;
    private String symbol;
    private String source;
    private BigDecimal bidPrice;
    private BigDecimal askPrice;
}
