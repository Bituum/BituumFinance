package dev.bituum.tinkoffmservice.util;

import com.google.gson.Gson;
import dev.bituum.tinkoffmservice.dto.CandleDto;
import dev.bituum.tinkoffmservice.model.Candle;

import java.util.List;

public class Extractor {
    public static List<Candle> extract(String json){
        Gson gson = new Gson();
        System.out.println(json);
        return gson.fromJson(json, CandleDto.class).from();
    }

}
