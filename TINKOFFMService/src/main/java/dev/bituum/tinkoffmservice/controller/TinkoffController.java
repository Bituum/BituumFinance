package dev.bituum.tinkoffmservice.controller;

import dev.bituum.tinkoffmservice.service.MarketDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class TinkoffController {

    @Autowired
    private MarketDataService marketDataService;

    @GetMapping("/getCandles")
    public String getCandles() throws IOException, InterruptedException {
        return marketDataService.getCandles("BANE");
    }
}
