package dev.bituum.tinkoffmservice.dto;

import dev.bituum.tinkoffmservice.module.Candle;
import lombok.AllArgsConstructor;
import lombok.ToString;

import java.util.Arrays;
import java.util.List;

@AllArgsConstructor
public class CandleDto {
    private Candle candleList[];

    public List<Candle> from(){
        return Arrays.asList(candleList);
    }
}
