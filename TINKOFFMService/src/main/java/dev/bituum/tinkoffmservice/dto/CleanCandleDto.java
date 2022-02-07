package dev.bituum.tinkoffmservice.dto;

import dev.bituum.tinkoffmservice.model.Candle;
import dev.bituum.tinkoffmservice.model.Quotation;
import lombok.Data;
import org.springframework.stereotype.Repository;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Data
public class CleanCandleDto {
    private double open;
    private double close;
    private double high;
    private double low;
    private String time;

    public CleanCandleDto(Candle candle) {
        this.open = convert(candle.getOpen());
        this.close = convert(candle.getClose());
        this.high = convert(candle.getHigh());
        this.low = convert(candle.getLow());
        this.time = candle.getTime();
    }

    private Double convert(Quotation quotation){
        String floatPart = quotation.getNano() == null ? "0" : quotation.getNano();
        String price = quotation.getUnits() + "." + floatPart;
        return Double.parseDouble(price);
    }
}
