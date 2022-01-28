package dev.bituum.tinkoffmservice.service.impl;

import com.fasterxml.jackson.databind.util.JSONPObject;
import dev.bituum.tinkoffmservice.enums.CandleInterval;
import dev.bituum.tinkoffmservice.model.Candle;
import dev.bituum.tinkoffmservice.service.MarketDataService;
import dev.bituum.tinkoffmservice.service.TickerService;
import dev.bituum.tinkoffmservice.util.Extractor;
import dev.bituum.tinkoffmservice.util.PostRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.http.HttpResponse;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
@PropertySource("classpath:/static/apiConfig.properties")
public class MarketDataServiceImpl implements MarketDataService {
    @Value("${post.token}")
    private String token;
    @Value("${post.getCandle}")
    private String getCandles;
    @Value("post.getOrders")
    private String getOrders;
    @Value("post.getOrderState")
    private String getOrderState;
    @Value("post.postOrder")
    private String postOrder;

    private String figi;
    private Date getYesterday() {
        final Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -1);
        return cal.getTime();
    }

    private final SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
    @Autowired
    private TickerService tickerService;

    @Override
    public String getCandles(String ticker) throws IOException, InterruptedException {
        Date now = new Date();
        Date yesterday = getYesterday();
        Map<String, String> map = new HashMap<>();
        String time = formatter.format(now);
        String yesterdayTime = formatter.format(yesterday);
        try{
            figi = tickerService.findFigi(ticker);
        }catch (IllegalArgumentException e){
            e.printStackTrace();
        }

        //".359Z" its a time zone
        map.put("figi", figi);
        map.put("from", yesterdayTime + ".359Z");
        map.put("to",time + ".359Z");
        //1 minute interval
        map.put("interval", String.valueOf(1));
        HttpResponse<String> response = PostRequest.sendPost(map, token, getCandles);
        return response.body();
    }

    @Override
    public String getLastPrices(String... figi) {
        return null;
    }

    @Override
    public String getOrderBook(String figi, int depth) {
        return null;
    }

    @Override
    public String getTradingStatus(String figi) {
        return null;
    }
}
