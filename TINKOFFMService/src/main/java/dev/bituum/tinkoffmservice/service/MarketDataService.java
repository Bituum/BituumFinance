package dev.bituum.tinkoffmservice.service;

import com.fasterxml.jackson.databind.util.JSONPObject;
import dev.bituum.tinkoffmservice.enums.CandleInterval;

import java.io.IOException;
import java.util.Date;

public interface MarketDataService {
    String getCandles(String ticker) throws IOException, InterruptedException;
    String getLastPrices(String ticker);
    String getOrderBook(String figi, int depth);
    String getTradingStatus(String figi);
}
