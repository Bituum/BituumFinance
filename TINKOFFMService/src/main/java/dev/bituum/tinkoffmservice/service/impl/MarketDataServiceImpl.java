package dev.bituum.tinkoffmservice.service.impl;

import com.fasterxml.jackson.databind.util.JSONPObject;
import dev.bituum.tinkoffmservice.model.Candle;
import dev.bituum.tinkoffmservice.service.MarketDataService;
import dev.bituum.tinkoffmservice.util.Extractor;
import dev.bituum.tinkoffmservice.util.PostRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.http.HttpResponse;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    private SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'hh-mm-sssZ");

    @Override
    public void getCandles() throws IOException, InterruptedException {
        Date now = new Date();
        Map<String, String> map = new HashMap<>();
        map.put("figi", "BBG004S68758");
        map.put("from", "2022-01-27T05:15:06.814Z");
        map.put("to","2022-01-27T19:07:06.814Z");
        map.put("interval","1");
        HttpResponse<String> response = PostRequest.sendPost(map, token, getCandles);
        //System.out.println(response.body());
        List<Candle> candleList = Extractor.extract(response.body());
        System.out.println(candleList);
        System.out.println(Extractor.extract(response.body()));
        System.out.println(Extractor.extract(response.body()));
        System.out.println(Extractor.extract(response.body()));
    }

    @Override
    public JSONPObject getLastPrices(String... figi) {
        return null;
    }

    @Override
    public JSONPObject getOrderBook(String figi, int depth) {
        return null;
    }

    @Override
    public JSONPObject getTradingStatus(String figi) {
        return null;
    }
}
