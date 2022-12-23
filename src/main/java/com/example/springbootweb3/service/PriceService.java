package com.example.springbootweb3.service;

import com.example.springbootweb3.model.Price;
import com.example.springbootweb3.repository.PriceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class PriceService {
    @Autowired
    private PriceRepository repository;

    // Scheduled job for every 10 seconds
    @Scheduled(fixedRate = 10000)
    public void updatePriceInDB() {

        // this needs to be real-time data
        Price price = new Price("BTCUSDT", "Houbi", 16825.77000000, 0.00312000, 16825.88000000, 0.00010000);
        repository.save(price);
    }
}
