package dev.bituum.tinkoffmservice.model;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Candle {
    @SerializedName("open")
    private Quotation open;
    @SerializedName("high")
    private Quotation high;
    @SerializedName("low")
    private Quotation low;
    @SerializedName("close")
    private Quotation close;
    @SerializedName("volume")
    private int volume;
    @SerializedName("time")
    private String time;
    @SerializedName("isComplete")
    private boolean isComplete;



}
