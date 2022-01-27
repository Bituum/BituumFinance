package dev.bituum.tinkoffmservice.service;

import com.fasterxml.jackson.databind.util.JSONPObject;
import dev.bituum.tinkoffmservice.enums.CandleInterval;

import java.io.IOException;
import java.util.Date;

public interface MarketDataService {
    void getCandles() throws IOException, InterruptedException;
    JSONPObject getLastPrices(String...figi);
    JSONPObject getOrderBook(String figi, int depth);
    JSONPObject getTradingStatus(String figi);
}
