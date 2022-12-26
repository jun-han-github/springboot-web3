package com.example.springbootweb3.service;

import com.example.springbootweb3.model.Price;
import com.example.springbootweb3.repository.PriceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

@Service
public class PriceService {
    @Autowired
    private PriceRepository repository;
    @Autowired
    private BinanceService binanceService;
    @Autowired
    private HoubiService houbiService;

    // Scheduled job for every 10 seconds
    @Scheduled(fixedRate = 10000)
    public void updatePriceInDB() {
        List<String> watchList = Arrays.asList("ETHUSDT", "BTCUSDT");
        for (String tokenSymbol : watchList) {
            Price price = repository.findBySymbol(tokenSymbol);
            Price bidPrice = bestPrice(tokenSymbol, "bid");
            Price askPrice = bestPrice(tokenSymbol, "ask");

            if (bidPrice == null || askPrice == null) {
                throw new NullPointerException();
            }

            if (price == null) {
                Price newPrice = new Price();
                newPrice.setSymbol(tokenSymbol);
                newPrice.setBidPrice(bidPrice.getBidPrice());
                newPrice.setAskPrice((askPrice.getAskPrice()));
                repository.save(newPrice);
            } else {
                price.setBidPrice(bidPrice.getBidPrice());
                price.setAskPrice(bidPrice.getAskPrice());
                repository.saveAndFlush(price);
            }
        }
    }

    /*
     * when we buy, we want the lower asking price
     *
     * When we sell, we want the higher bidding price
     *
     * */
    public Price bestPrice(String tokenSymbol, String action) {
        try {
            Price binance = binanceService.getPrice(tokenSymbol.toUpperCase()).block();
            Price houbi = houbiService.getPrice(tokenSymbol.toLowerCase()).block();


            // It's either ask or bid
            if (action == "ask") {
                BigDecimal binanceAskPrice = binance.getAskPrice();
                BigDecimal houbiAskPrice = houbi.getAskPrice();

                if (binanceAskPrice != null && houbiAskPrice != null) {
                    if (binanceAskPrice.compareTo(houbiAskPrice) < 0) {
                        return binance;
                    } else {
                        return houbi;
                    }
                } else {
                    throw new NullPointerException();
                }
            }

            if (action == "bid") {
                BigDecimal binanceBidPrice = binance.getBidPrice();
                BigDecimal houbiBidPrice = houbi.getBidPrice();

                if (binanceBidPrice != null && houbiBidPrice != null) {
                    if (binanceBidPrice.compareTo(houbiBidPrice) > 0) {
                        return binance;
                    } else {
                        return houbi;
                    }
                } else {
                    throw new NullPointerException();
                }
            }

            return null;

        } catch (Exception e) {
            throw new RuntimeException();
        }
    }
}
