package dev.bituum.tinkoffmservice.model;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Quotation {
    @SerializedName("units")
    private int units;
    @SerializedName("nano")
    private int nano;
}
