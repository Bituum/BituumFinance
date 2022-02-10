package dev.bituum.tinkoffmservice.util;

import com.google.gson.Gson;
import dev.bituum.tinkoffmservice.dto.CandleDto;
import dev.bituum.tinkoffmservice.dto.LastPriceDto;
import dev.bituum.tinkoffmservice.model.Candle;
import dev.bituum.tinkoffmservice.model.LastPrice;

import java.util.List;

public class LastPriceExtractor {
    public static List<LastPrice> extract(String json){
        Gson gson = new Gson();
        return gson.fromJson(json, LastPriceDto.class).from();
    }
}
