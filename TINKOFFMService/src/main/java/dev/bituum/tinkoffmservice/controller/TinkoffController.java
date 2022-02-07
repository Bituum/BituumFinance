package dev.bituum.tinkoffmservice.controller;

import dev.bituum.tinkoffmservice.dto.CleanCandleDto;
import dev.bituum.tinkoffmservice.service.MarketDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
public class TinkoffController {

    @Autowired
    private MarketDataService marketDataService;

    @GetMapping("/getCandles/{ticker}")
    public List<CleanCandleDto> getCandles(@PathVariable("ticker") String ticker) throws IOException, InterruptedException {
        System.out.println(marketDataService.getCandles(ticker));
        return marketDataService.getCandles(ticker);
    }

    @GetMapping("/get_last_price/{ticker}")
    public String getLastPrice(@PathVariable("ticker") String ticker) {
        return marketDataService.getLastPrices(ticker);
    }

    @GetMapping("/get_order_book/{ticker}/with_depth/{depth}")
    public String getOrderBook(@PathVariable("ticker") String ticker, @PathVariable("depth") int depth) {
        return marketDataService.getOrderBook(ticker, depth);
    }

    @GetMapping("/get_trading_status_of/{ticker}")
    public String getTradingStatus(@PathVariable("ticker") String ticker) throws IOException, InterruptedException {
        return marketDataService.getTradingStatus(ticker);
    }
}
