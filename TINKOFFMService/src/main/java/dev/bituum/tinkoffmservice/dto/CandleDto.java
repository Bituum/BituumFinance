package dev.bituum.tinkoffmservice.dto;

import com.google.gson.annotations.SerializedName;
import dev.bituum.tinkoffmservice.model.Candle;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CandleDto {
    @SerializedName("candles")
    private List<Candle> candles = new ArrayList<>();

    public List<Candle> from(){
        return candles;
    }
}
