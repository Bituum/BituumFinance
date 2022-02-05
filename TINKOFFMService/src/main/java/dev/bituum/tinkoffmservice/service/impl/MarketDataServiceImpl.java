package dev.bituum.tinkoffmservice.service.impl;

import com.fasterxml.jackson.databind.util.JSONPObject;
import dev.bituum.tinkoffmservice.dto.CleanCandleDto;
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
import java.util.stream.Collectors;

@Service
@PropertySource("classpath:/static/apiConfig.properties")
public class MarketDataServiceImpl implements MarketDataService {
    @Value("${post.token}")
    private String token;
    @Value("${post.getCandle}")
    private String getCandles;
    @Value("${post.getLastPrices}")
    private String getLastPrices;
    @Value("${post.getOrderBook}")
    private String getOrderBook;
    @Value("${post.GetTradingStatus}")
    private String GetTradingStatus;

    private String figi;
    private Date getThreeHoursAgo() {
        final Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -1);
        return cal.getTime();
    }

    private final SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
    @Autowired
    private TickerService tickerService;

    @Override
    public List<CleanCandleDto> getCandles(String ticker) throws IOException, InterruptedException {
        Date now = new Date();
        Date yesterday = getThreeHoursAgo();
        Map<String, String> map = new HashMap<>();
        String time = formatter.format(now);
        String severalHoursAgo = formatter.format(yesterday);
        try{
            figi = tickerService.findFigi(ticker);
        }catch (IllegalArgumentException e){
            e.printStackTrace();
        }

        //".359Z" its a time zone
        map.put("figi", figi);
        map.put("from", severalHoursAgo + ".359Z");
        map.put("to",time + ".359Z");
        //1 minute interval
        map.put("interval", String.valueOf(1));
        PostRequest<String> request = new PostRequest<>();
        HttpResponse<String> response = request.sendPost(map, token, getCandles);

        List<Candle> candleList = Extractor.extract(response.body());
        return candleList.stream()
                .map(candle -> new CleanCandleDto(candle))
                .collect(Collectors.toList());
    }

    @Override
    public String getLastPrices(String ticker) {
        try{
            String figi = tickerService.findFigi(ticker);
            Map<String, Object[]> body = new HashMap<>();
            PostRequest<Object[]> request = new PostRequest<>();
            List<String> list = new ArrayList<>();
            list.add(figi);
            body.put("figi", list.toArray());
            return request.sendPost(body, token, getLastPrices).body();
        }catch (IllegalArgumentException | IOException | InterruptedException exception){
            exception.printStackTrace();
        }
        return null;
    }

    @Override
    public String getOrderBook(String ticker, int depth) {
        try{
            String figi = tickerService.findFigi(ticker);
            Map<String, String> body = new HashMap<>();
            PostRequest<String> request = new PostRequest<>();
            body.put("figi", figi);
            body.put("depth", String.valueOf(depth));
            return request.sendPost(body, token, getOrderBook).body();
        } catch (IOException | InterruptedException | IllegalArgumentException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String getTradingStatus(String ticker) {
        try{
            String figi = tickerService.findFigi(ticker);
            Map<String, String> body = new HashMap<>();
            PostRequest<String> request = new PostRequest<>();
            body.put("figi", figi);

            return request.sendPost(body, token, GetTradingStatus).body();
        } catch (IOException | InterruptedException | IllegalArgumentException e) {
            e.printStackTrace();
        }
        return null;
    }
}
