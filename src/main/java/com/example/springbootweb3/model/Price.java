package com.example.springbootweb3.model;

import jakarta.persistence.*;
import lombok.*;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name="price")
@Getter
@Setter
@ToString
public class Price {

    @Id
    @GeneratedValue(
            strategy = GenerationType.AUTO
    )
    private Long id;
    private String symbol;
    private String source;
    private double bidPrice;
    private double askPrice;

    public Price(String symbol, String source, double bidPrice, double bidQty, double askPrice, double askQty) {
        this.symbol = symbol;
        this.source = source;
        this.bidPrice = bidPrice;
        this.askPrice = askPrice;
    }
}