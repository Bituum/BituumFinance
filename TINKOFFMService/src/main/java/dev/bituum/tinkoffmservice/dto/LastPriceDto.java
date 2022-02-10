package dev.bituum.tinkoffmservice.dto;

import com.google.gson.annotations.SerializedName;
import dev.bituum.tinkoffmservice.model.LastPrice;

import java.util.ArrayList;
import java.util.List;

public class LastPriceDto {
    @SerializedName("lastPrices")
    private List<LastPrice> lastPrices = new ArrayList<>();

    public List<LastPrice> from(){
        return lastPrices;
    }
}

