package dev.bituum.tinkoffmservice.util;

import com.google.gson.Gson;
import dev.bituum.tinkoffmservice.dto.CandleDto;
import dev.bituum.tinkoffmservice.module.Candle;

public class Extractor {
    public static CandleDto extract(String json){
//        String userJson = "[{'name': 'Alex','id': 1}, "
//                + "{'name': 'Brian','id':2}, "
//                + "{'name': 'Charles','id': 3}]";
//
        //TODO Сделать метод, который убирает фигурные скобку у json'а, чтобы он стал массивом, а не ебучим объектом с массивом!
        //util makePrettyJson
//        Gson gson = new Gson();
//        Candle[] candleDto = gson.fromJson(json, Candle[].class);
//        for(CandleDto dto : candleDto){
//            dto.from();
//        }
        return null;
    }

}
