package dev.bituum.tinkoffmservice.service;

import com.fasterxml.jackson.databind.util.JSONPObject;
import dev.bituum.tinkoffmservice.dto.CleanCandleDto;
import dev.bituum.tinkoffmservice.enums.CandleInterval;

import java.io.IOException;
import java.util.Date;
import java.util.List;

public interface MarketDataService {
    List<CleanCandleDto> getCandles(String ticker) throws IOException, InterruptedException;
    String getLastPrices(String ticker);
    String getOrderBook(String figi, int depth);
    String getTradingStatus(String figi);
}
