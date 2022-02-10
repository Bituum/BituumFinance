package dev.bituum.tinkoffmservice.model;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LastPrice {
    @SerializedName("figi")
    private String figi;
    @SerializedName("price")
    private Quotation price;
    @SerializedName("time")
    private String time;
}
