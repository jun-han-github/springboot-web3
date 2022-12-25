package com.example.springbootweb3.service;

import com.example.springbootweb3.model.Price;
import com.example.springbootweb3.repository.PriceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PriceService {
    @Autowired
    private PriceRepository repository;
    @Autowired
    private BinanceService binanceService;
    @Autowired
    private HoubiService houbiService;

    // Scheduled job for every 10 seconds
//    @Scheduled(fixedRate = 10000)
    public void updatePriceInDB() {
        // this needs to be real-time data
        Price price = new Price("BTCUSDT", "Houbi", 16825.77000000, 0.00312000, 16825.88000000, 0.00010000);
        repository.save(price);
    }

    /*
    * Math.max() is used because when we sell,
    * we want the higher bidding price
    *
    * */
    public Double bestBidPrice(String tokenSymbol) {
        try {
            Double binanceBidPrice = binanceService.getBidPrice(tokenSymbol);
            Double houbiBidPrice = houbiService.getBidPrice(tokenSymbol);


            return Math.max(binanceBidPrice, houbiBidPrice);

        } catch (Exception e) {
            throw new RuntimeException();
        }
    }


    /*
     * Math.min() is used because when we buy,
     * we want the lower asking price
     *
     * */
    public Double bestAskPrice(String tokenSymbol) {
        try {
            Double binanceAskPrice = binanceService.getAskPrice(tokenSymbol);
            Double houbiAskPrice = houbiService.getAskPrice(tokenSymbol);

            return Math.min(binanceAskPrice, houbiAskPrice);
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }
}
